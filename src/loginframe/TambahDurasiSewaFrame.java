import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TambahDurasiSewaFrame extends JFrame {

    private JTextField namaField;
    private JTextField modelMobilField;
    private JTextField tanggalMulaiField;
    private JTextField durasiField;

    public TambahDurasiSewaFrame() {
        setTitle("Tambah Durasi Sewa - Rental Mobil");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel modelMobilLabel = new JLabel("Model Mobil:");
        JLabel tanggalMulaiLabel = new JLabel("Tanggal Mulai (yyyy-mm-dd):");
        JLabel durasiLabel = new JLabel("Durasi (hari):");
        
        namaField = new JTextField(20);
        modelMobilField = new JTextField(20);
        tanggalMulaiField = new JTextField(20);
        durasiField = new JTextField(20);
        
        JButton submitButton = new JButton("Submit");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(namaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(namaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(modelMobilLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(modelMobilField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(tanggalMulaiLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tanggalMulaiField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(durasiLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(durasiField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        setVisible(true);
    }

    private void handleSubmit() {
        String nama = namaField.getText();
        String modelMobil = modelMobilField.getText();
        String tanggalMulai = tanggalMulaiField.getText();
        int durasi;

        try {
            durasi = Integer.parseInt(durasiField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Durasi harus berupa angka", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tambahDurasiSewa(nama, modelMobil, tanggalMulai, durasi)) {
            JOptionPane.showMessageDialog(this, "Durasi sewa berhasil ditambahkan");
            namaField.setText("");
            modelMobilField.setText("");
            tanggalMulaiField.setText("");
            durasiField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan durasi sewa", "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean tambahDurasiSewa(String nama, String modelMobil, String tanggalMulai, int durasi) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;

        try {
            String url = "jdbc:mysql://localhost:3306/rental_mobil";
            String dbUsername = "root";
            String dbPassword = "";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO sewa (nama, model_mobil, tanggal_mulai, durasi) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setString(2, modelMobil);
            statement.setString(3, tanggalMulai);
            statement.setInt(4, durasi);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kesalahan database: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TambahDurasiSewaFrame();
            }
        });
    }
}
