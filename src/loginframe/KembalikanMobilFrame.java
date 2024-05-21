import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KembalikanMobilFrame extends JFrame {

    private JTextField namaField;
    private JTextField modelMobilField;
    private JTextField tanggalKembaliField;

    public KembalikanMobilFrame() {
        setTitle("Kembalikan Mobil - Rental Mobil");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel modelMobilLabel = new JLabel("Model Mobil:");
        JLabel tanggalKembaliLabel = new JLabel("Tanggal Kembali (yyyy-mm-dd):");

        namaField = new JTextField(20);
        modelMobilField = new JTextField(20);
        tanggalKembaliField = new JTextField(20);

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
        add(tanggalKembaliLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tanggalKembaliField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
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
        String tanggalKembali = tanggalKembaliField.getText();

        if (kembalikanMobil(nama, modelMobil, tanggalKembali)) {
            JOptionPane.showMessageDialog(this, "Mobil berhasil dikembalikan");
            namaField.setText("");
            modelMobilField.setText("");
            tanggalKembaliField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengembalikan mobil", "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean kembalikanMobil(String nama, String modelMobil, String tanggalKembali) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_mobil", "root", "");
             PreparedStatement statement = connection.prepareStatement("UPDATE sewa SET tanggal_kembali = ? WHERE nama = ? AND model_mobil = ? AND tanggal_kembali IS NULL")) {

            statement.setString(1, tanggalKembali);
            statement.setString(2, nama);
            statement.setString(3, modelMobil);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kesalahan database: " + e.getMessage(), "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new KembalikanMobilFrame();
            }
        });
    }
}
