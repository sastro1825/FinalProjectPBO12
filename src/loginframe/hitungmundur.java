import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalGUI extends JFrame {
    private JLabel nameLabel, carLabel, returnLabel;
    private JTextField nameField;
    private JComboBox<String> carComboBox;
    private JButton rentButton, returnButton;
    private JTextArea countdownArea;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public RentalGUI() {
        setTitle("Rental Car");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);
        carLabel = new JLabel("Select Car:");
        carComboBox = new JComboBox<>();
        returnLabel = new JLabel("Return Date:");
        rentButton = new JButton("Rent Car");
        returnButton = new JButton("Return Car");
        countdownArea = new JTextArea(10, 30);

        add(nameLabel);
        add(nameField);
        add(carLabel);
        add(carComboBox);
        add(returnLabel);
        add(rentButton);
        add(returnButton);
        add(countdownArea);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_mobil", "username", "password");
            stmt = conn.createStatement();
            refreshCarList(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentCar();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnCar();
            }
        });

       Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCountdown();
            }
        });
        timer.start();
    }

    private void refreshCarList() {
        carComboBox.removeAllItems(); 
        try {
            rs = stmt.executeQuery("SELECT namaMobil FROM list_mobil WHERE ketersediaan='Tersedia'");
            while (rs.next()) {
                carComboBox.addItem(rs.getString("namaMobil"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void rentCar() {
        String name = nameField.getText();
        String car = carComboBox.getSelectedItem().toString();
        Date returnDate = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String returnDateString = sdf.format(returnDate);
        try {
            stmt.executeUpdate("UPDATE list_mobil SET ketersediaan='Tidak Tersedia' WHERE namaMobil='" + car + "'");
            stmt.executeUpdate("INSERT INTO sewa (namaPenyewa, tipeMobil, tglSewa, tglKembali) VALUES ('" + name + "', '" + car + "', CURDATE(), '" + returnDateString + "')");
            refreshCarList();
            countdownArea.setText("Car rented successfully! Return by: " + returnDateString);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void returnCar() {
        String car = carComboBox.getSelectedItem().toString();
        try {
            stmt.executeUpdate("UPDATE list_mobil SET ketersediaan='Tersedia' WHERE namaMobil='" + car + "'");
            stmt.executeUpdate("DELETE FROM sewa WHERE tipeMobil='" + car + "' AND tglKembali IS NULL");
            refreshCarList(); 
            countdownArea.setText("Car returned successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCountdown() {
        try {
            rs = stmt.executeQuery("SELECT TIMESTAMPDIFF(SECOND, CURDATE(), tglKembali) AS countdown FROM sewa WHERE tglKembali > CURDATE()");
            if (rs.next()) {
                int seconds = rs.getInt("countdown");
                int days = seconds / (24 * 60 * 60);
                seconds %= (24 * 60 * 60);
                int hours = seconds / (60 * 60);
                seconds %= (60 * 60);
                int minutes = seconds / 60;
                seconds %= 60;
                countdownArea.setText("Time remaining: " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
            } else {
                countdownArea.setText("No active rentals.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RentalGUI().setVisible(true);
            }
        });
    }
}
