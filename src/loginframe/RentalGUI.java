package loginframe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalGUI extends JFrame {
    private JLabel carLabel, returnLabel;
    private JComboBox<String> carComboBox;
    private JButton backButton;
    private JTextArea countdownArea;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public RentalGUI() {
        setTitle("Rental Car");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        carLabel = new JLabel("Nomor Polisi:");
        carComboBox = new JComboBox<>();
        returnLabel = new JLabel("Return Date:");
        backButton = new JButton("Back to Menu");
        countdownArea = new JTextArea(10, 30);

        add(carLabel);
        add(carComboBox);
        add(returnLabel);
        add(backButton);
        add(countdownArea);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_mobil", "username", "password");
            stmt = conn.createStatement();
            refreshCarList(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality to go back to menu
                dispose(); // Close current window
                new Menu().setVisible(true); // Show menu window
            }
        });

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCountdown(carComboBox.getSelectedItem().toString());
            }
        });
        timer.start();
    }

    private void refreshCarList() {
        carComboBox.removeAllItems(); 
        try {
            rs = stmt.executeQuery("SELECT nopol FROM list_mobil WHERE ketersediaan='Tidak Tersedia'");
            while (rs.next()) {
                carComboBox.addItem(rs.getString("nopol"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCountdown(String nopol) {
        try {
            rs = stmt.executeQuery("SELECT TIMESTAMPDIFF(SECOND, CURDATE(), tglKembali) AS countdown FROM sewa WHERE nopol = '" + nopol + "'");
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
