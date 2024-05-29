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
    private JTextField nikField;
    private JTextField alamatField;
    private JTextField noHPField;
    private JTextField tipeMobilField;
    private JTextField tahunMobilSewaField;
    private JTextField nopolField;
    private JTextField tglSewaField;
    private JTextField tglKembaliField;
    private JTextField supirField;
    private JTextField totalHargaField;

    public TambahDurasiSewaFrame() {
        setTitle("Tambah Durasi Sewa - Rental Mobil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel nikLabel = new JLabel("NIK:");
        JLabel alamatLabel = new JLabel("Alamat:");
        JLabel noHPLabel = new JLabel("No HP:");
        JLabel tipeMobilLabel = new JLabel("Tipe Mobil:");
        JLabel tahunMobilSewaLabel = new JLabel("Tahun Mobil Sewa:");
        JLabel nopolLabel = new JLabel("Nopol:");
        JLabel tglSewaLabel = new JLabel("Tanggal Sewa (yyyy-mm-dd):");
        JLabel tglKembaliLabel = new JLabel("Tanggal Kembali (yyyy-mm-dd):");
        JLabel supirLabel = new JLabel("Supir (Ya/Tidak):");
        JLabel totalHargaLabel = new JLabel("Total Harga:");

        namaField = new JTextField(20);
        nikField = new JTextField(20);
        alamatField = new JTextField(20);
        noHPField = new JTextField(20);
        tipeMobilField = new JTextField(20);
        tahunMobilSewaField = new JTextField(20);
        nopolField = new JTextField(20);
        tglSewaField = new JTextField(20);
        tglKembaliField = new JTextField(20);
        supirField = new JTextField(20);
        totalHargaField = new JTextField(20);

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
        add(nikLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nikField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(alamatLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(alamatField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(noHPLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(noHPField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(tipeMobilLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(tipeMobilField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(tahunMobilSewaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(tahunMobilSewaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(nopolLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(nopolField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(tglSewaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        add(tglSewaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(tglKembaliLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        add(tglKembaliField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(supirLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        add(supirField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(totalHargaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        add(totalHargaField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
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
        String nik = nikField.getText();
        String alamat = alamatField.getText();
        String noHP = noHPField.getText();
        String tipeMobil = tipeMobilField.getText();
        int tahunMobilSewa;
        String nopol = nopolField.getText();
        String tglSewa = tglSewaField.getText();
        String tglKembali = tglKembaliField.getText();
        String supir = supirField.getText();
        int totalHarga;

        try {
            tahunMobilSewa = Integer.parseInt(tahunMobilSewaField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun Mobil Sewa harus berupa angka", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            totalHarga = Integer.parseInt(totalHargaField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Total Harga harus berupa angka", "Kesalahan Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tambahDurasiSewa(nama, nik, alamat, noHP, tipeMobil, tahunMobilSewa, nopol, tglSewa, tglKembali, supir, totalHarga)) {
            JOptionPane.showMessageDialog(this, "Durasi sewa berhasil ditambahkan");
            namaField.setText("");
            nikField.setText("");
            alamatField.setText("");
            noHPField.setText("");
            tipeMobilField.setText("");
            tahunMobilSewaField.setText("");
            nopolField.setText("");
            tglSewaField.setText("");
            tglKembaliField.setText("");
            supirField.setText("");
            totalHargaField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan durasi sewa", "Kesalahan Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean tambahDurasiSewa(String nama, String nik, String alamat, String noHP, String tipeMobil, int tahunMobilSewa, String nopol, String tglSewa, String tglKembali, String supir, int totalHarga) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isSuccess = false;

        try {
            String url = "jdbc:mysql://localhost:3306/rental_mobil";
            String dbUsername = "root";
            String dbPassword = "";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "INSERT INTO sewa (namaPenyewa, nik, alamat, noHP, tipeMobil, tahunMobilSewa, nopol, tglSewa, tglKembali, supir, totalHarga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setString(2, nik);
            statement.setString(3, alamat);
            statement.setString(4, noHP);
            statement.setString(5, tipeMobil);
            statement.setInt(6, tahunMobilSewa);
            statement.setString(7, nopol);
            statement.setString(8, tglSewa);
            statement.setString(9, tglKembali);
            statement.setString(10, supir);
            statement.setInt(11, totalHarga);

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
