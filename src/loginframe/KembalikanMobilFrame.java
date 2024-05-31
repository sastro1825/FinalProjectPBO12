package loginframe;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.CallableStatement;

public class KembalikanMobilFrame extends JFrame {

    private JTextField namaField;
    private JTextField modelMobilField;
    private JDateChooser tanggalKembaliChooser;

    public KembalikanMobilFrame() {
        setTitle("Kembalikan Mobil - Rental Mobil");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel modelMobilLabel = new JLabel("Model Mobil:");
        JLabel tanggalKembaliLabel = new JLabel("Tanggal Kembali:");

        namaField = new JTextField(20);
        modelMobilField = new JTextField(20);
        tanggalKembaliChooser = new JDateChooser();
        tanggalKembaliChooser.setDateFormatString("yyyy-MM-dd");

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
        add(tanggalKembaliChooser, gbc);

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalKembali = sdf.format(tanggalKembaliChooser.getDate());

        if (kembalikanMobil(nama, modelMobil, tanggalKembali)) {
            JOptionPane.showMessageDialog(this, "Mobil berhasil dikembalikan");
            namaField.setText("");
            modelMobilField.setText("");
            tanggalKembaliChooser.setDate(null); 
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengembalikan mobil", "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

private boolean kembalikanMobil(String nama, String modelMobil, String tanggalKembali) {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_mobil", "root", "");
         CallableStatement statement = connection.prepareCall("CALL KembalikanMobil(?,?,?)")) {

        statement.setString(1, nama);
        statement.setString(2, modelMobil);
        statement.setDate(3, java.sql.Date.valueOf(tanggalKembali));

        statement.execute();

        return true;
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
