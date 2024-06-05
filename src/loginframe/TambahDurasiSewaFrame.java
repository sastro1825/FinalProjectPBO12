package loginframe;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import loginframe.menu;

public class TambahDurasiSewaFrame extends JFrame {

    private JTextField namaField;
    private JTextField nikField;
    private JTextField alamatField;
    private JTextField noHPField;
    private JTextField tipeMobilField;
    private JTextField tahunMobilSewaField;
    private JTextField nopolField;
    private JDateChooser tglSewaChooser;
    private JDateChooser tglKembaliChooser;
    private JTextField supirField;
    private JTextField totalHargaField;

    public TambahDurasiSewaFrame() {
        setTitle("Tambah Durasi Sewa - Rental Mobil");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set background color
        getContentPane().setBackground(Color.decode("#0B60B0"));

        // Create labels and set their foreground to white
        JLabel namaLabel = new JLabel("Nama:");
        namaLabel.setForeground(Color.WHITE);
        JLabel nikLabel = new JLabel("NIK:");
        nikLabel.setForeground(Color.WHITE);
        JLabel alamatLabel = new JLabel("Alamat:");
        alamatLabel.setForeground(Color.WHITE);
        JLabel noHPLabel = new JLabel("No HP:");
        noHPLabel.setForeground(Color.WHITE);
        JLabel tipeMobilLabel = new JLabel("Tipe Mobil:");
        tipeMobilLabel.setForeground(Color.WHITE);
        JLabel tahunMobilSewaLabel = new JLabel("Tahun Mobil Sewa:");
        tahunMobilSewaLabel.setForeground(Color.WHITE);
        JLabel nopolLabel = new JLabel("Nopol:");
        nopolLabel.setForeground(Color.WHITE);
        JLabel tglSewaLabel = new JLabel("Tanggal Sewa:");
        tglSewaLabel.setForeground(Color.WHITE);
        JLabel tglKembaliLabel = new JLabel("Tanggal Kembali:");
        tglKembaliLabel.setForeground(Color.WHITE);
        JLabel supirLabel = new JLabel("Supir (Ya/Tidak):");
        supirLabel.setForeground(Color.WHITE);
        JLabel totalHargaLabel = new JLabel("Total Harga:");
        totalHargaLabel.setForeground(Color.WHITE);

        namaField = new JTextField(20);
        nikField = new JTextField(20);
        alamatField = new JTextField(20);
        noHPField = new JTextField(20);
        tipeMobilField = new JTextField(20);
        tahunMobilSewaField = new JTextField(20);
        nopolField = new JTextField(20);
        tglSewaChooser = new JDateChooser();
        tglKembaliChooser = new JDateChooser();
        supirField = new JTextField(20);
        totalHargaField = new JTextField(20);

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Kembali");

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Add components to the frame
        addComponent(gbc, 0, 0, namaLabel);
        addComponent(gbc, 1, 0, namaField);
        addComponent(gbc, 0, 1, nikLabel);
        addComponent(gbc, 1, 1, nikField);
        addComponent(gbc, 0, 2, alamatLabel);
        addComponent(gbc, 1, 2, alamatField);
        addComponent(gbc, 0, 3, noHPLabel);
        addComponent(gbc, 1, 3, noHPField);
        addComponent(gbc, 0, 4, tipeMobilLabel);
        addComponent(gbc, 1, 4, tipeMobilField);
        addComponent(gbc, 0, 5, tahunMobilSewaLabel);
        addComponent(gbc, 1, 5, tahunMobilSewaField);
        addComponent(gbc, 0, 6, nopolLabel);
        addComponent(gbc, 1, 6, nopolField);
        addComponent(gbc, 0, 7, tglSewaLabel);
        addComponent(gbc, 1, 7, tglSewaChooser);
        addComponent(gbc, 0, 8, tglKembaliLabel);
        addComponent(gbc, 1, 8, tglKembaliChooser);
        addComponent(gbc, 0, 9, supirLabel);
        addComponent(gbc, 1, 9, supirField);
        addComponent(gbc, 0, 10, totalHargaLabel);
        addComponent(gbc, 1, 10, totalHargaField);
        addComponent(gbc, 0, 11, backButton);
        addComponent(gbc, 1, 11, submitButton);

        // Add action listeners to buttons
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new menu().setVisible(true);
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        setVisible(true);
    }

    private void addComponent(GridBagConstraints gbc, int x, int y, Component component) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }

    private void handleSubmit() {
        String nama = namaField.getText();
        String nik = nikField.getText();
        String alamat = alamatField.getText();
        String noHP = noHPField.getText();
        String tipeMobil = tipeMobilField.getText();
        int tahunMobilSewa;
        String nopol = nopolField.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tglSewa = sdf.format(tglSewaChooser.getDate());
        String tglKembali = sdf.format(tglKembaliChooser.getDate());
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
            tglSewaChooser.setDate(null);
            tglKembaliChooser.setDate(null);
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
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TambahDurasiSewaFrame().setVisible(true));
    }
}
