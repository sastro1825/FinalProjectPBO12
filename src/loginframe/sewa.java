package loginframe;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import db.db_con;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.Vector;


/**
 *
 * @author Wahid
 */
public class sewa extends javax.swing.JFrame {
    Connection conn;
    ResultSet rs;
    String nama, nik, alamat, noHP, tipeMobil, tahun, totalHarga, supir, tglSewa, tglAkhir;
    String ketersediaan;
    String nopolbox;
    
    db_con koneksi;
    
    /**
     * Creates new form sewa
     */
    public sewa() {
        initComponents();
        
        koneksi = new db_con(new db.parameter().HOST_DB, new db.parameter().USERNAME_DB, new db.parameter().PASSWORD_DB);
        conn = koneksi.connectDatabase();

        addItemtoComboBoxMobil();
        
        jLabelKetersediaan.setText("");
        jLabelTotalHarga.setText("");
        
        
        
    }
    
    private void addItemtoComboBoxMobil() {

        try {
            String[] namaKolom = {"namaMobil"};
            String namaTabel = "list_mobil";
            rs = koneksi.databaseSelect(namaKolom, namaTabel);
            
            while (rs.next()) {
                String item = rs.getString("namaMobil");
                jComboBoxMobil.addItem(item);
            }
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    private void nopol(String namaMobil) {
        try {
            String[] namaKolom = {"nopol"};
            String namaTabel = "list_mobil";
            String condition = "namaMobil = '" +namaMobil + "'";
            rs = koneksi.databaseSelect(namaKolom, namaTabel, condition);
            
            while(rs.next()) {
                String item = rs.getString("nopol");
                jComboBoxNopol.addItem(item);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    /*private void insertData() {
        try {

            if (!lblNama.getText().isEmpty()) {

                if (!jikakeluar()) {

                    JOptionPane.showMessageDialog(this, "maaf mobil ini sedang tidak tersedia");
                } else {
                    String kolom[] = {"peminjam", "nopol", "tgl_pinjaman", "tgl_kembali", "harga", "lama", "total"};
                    java.util.Date tgl = (java.util.Date) this.jDateChooser1.getDate();
                    java.util.Date tgl1 = (java.util.Date) this.jDateChooser2.getDate();

                    String isi[] = {lblNama.getText(), boxnopol.getSelectedItem().toString(), new java.sql.Date(tgl.getTime()).toString(), new java.sql.Date(tgl1.getTime()).toString(), lbl_harga.getText(), txt_lama.getText(), txt_total.getText()};
                    System.out.println(con.queryInsert("tb_transaksi", kolom, isi));

                    JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan");
                    cekstatus();
                    add_peminjam();
                }


            } else {
                JOptionPane.showMessageDialog(this, "Data isian ada yang kosong");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error input data");
            System.out.println("salah");
        }
    }*/
    
    /*private void insertDatabase(String values) {
        String namaTabel = "sewa";
        String[] namaKolom = {"namaPenyewa", "nik", "alamat", "noHP", "tipeMobil", "tahunMobilSewa", "nopol", "tglSewa", "tglKembali", "supir", "totalHarga"};
        String[] tableValues = values.split(",");
        koneksi.databaseInsert(namaTabel, namaKolom, tableValues);
    
        if (result.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
        }
    }*/
    
    /*private boolean insertDatabase(String values) {
    String namaTabel = "sewa";
    String[] namaKolom = {"namaPenyewa", "nik", "alamat", "noHP", "tipeMobil", "tahunMobilSewa", "nopol", "tglSewa", "tglKembali", "supir", "totalHarga"};
    String[] tableValues = values.split(",");
    
    try {
        koneksi.databaseInsert(namaTabel, namaKolom, tableValues);
        return true;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
    }*/
    
    /*private void insertDatabase(String values) {
    String namaTabel = "sewa";
    String[] namaKolom = {"namaPenyewa", "nik", "alamat", "noHP", "tipeMobil", "tahunMobilSewa", "nopol", "tglSewa", "tglKembali", "supir", "totalHarga"};
    String[] tableValues = values.split(",");
    String result = koneksi.databaseInsert(namaTabel, namaKolom, tableValues);

    if (result.equals("SUCCESS")) {
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
    } else {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
    }
}*/
    private void insertIntoSewa(String nama, String nik, String alamat, String noHP, String tipeMobil,
                             String tahun, String nopolbox, String tglSewa, String tglAkhir,
                             String supir, String totalHarga) {
        String sql = "INSERT INTO sewa (namaPenyewa, nik, alamat, noHP, tipeMobil, tahunMobilSewa, " +
                 "nopol, tglSewa, tglKembali, supir, totalHarga) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String namaTabel = "list_mobil";
        String[] namaKolom = {"ketersediaan"};
        String[] tableValues = {"Tidak Tersedia"};
        String condition = "nopol = '" + nopolbox + "'";

        try (Connection connection = koneksi.connectDatabase();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, nik);
            preparedStatement.setString(3, alamat);
            preparedStatement.setString(4, noHP);
            preparedStatement.setString(5, tipeMobil);
            preparedStatement.setString(6, tahun);
            preparedStatement.setString(7, nopolbox);
            preparedStatement.setString(8, tglSewa);
            preparedStatement.setString(9, tglAkhir);
            preparedStatement.setString(10, supir);
            preparedStatement.setString(11, totalHarga);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(nopolbox);
                koneksi.mySQLUpdate(namaTabel, namaKolom, tableValues, condition);
                JOptionPane.showMessageDialog(this, "Penyewaan Berhasil");
                System.out.println("Data berhasil disimpan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }
    }


    
    
    public String cekStatus(String nopol) {
        String[] namaKolom = {"ketersediaan"};
        String namaTabel = "list_mobil";
        String condition = "nopol = '" + nopol + "'";
        String ketersediaan = null;
        
        try {
            rs = koneksi.databaseSelect(namaKolom, namaTabel, condition);
            
            while(rs.next()) {
                String status = rs.getString("ketersediaan");
                //System.out.println("2" +status);
                
                if(status.equals("Tersedia")) {
                    ketersediaan = status;
                    break;
                }
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        //System.out.println(ketersediaan);
        return ketersediaan;
    }
    
    public void totalHarga(int Driver) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String strDate1 = df.format(jDateChooserMulai.getDate());
            String strDate2 = df.format(jDateChooserAkhir.getDate());
            Date Tanggal1 = df.parse(strDate1);
            Date Tanggal2 = df.parse(strDate2);
            long Hari1 = Tanggal1.getTime();
            long Hari2 = Tanggal2.getTime();
            long diff = Hari2 - Hari1;
            long Lama = diff / (24 * 60 * 60 * 1000);
            String Hasil = (Long.toString(Lama));
            //System.out.println(Hasil);

            int harga_sewa = Integer.parseInt(jLabelHargaMobil.getText());
            int lama_sewa = Integer.parseInt(Hasil);
            int harga_driver = Driver * lama_sewa;
            int Total = (harga_sewa * lama_sewa) + harga_driver;
            String totalHarga = Integer.toString(Total);
            jLabelTotalHarga.setText(totalHarga);



        } catch (Exception a) {
            JOptionPane.showMessageDialog(this, "Masukan Tanggal Peminjaman dan Tanggal Pengembalian");
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jMenu1 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabelMobil1 = new javax.swing.JLabel();
        jLabelMobil3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldNama = new javax.swing.JTextField();
        jLabelNama = new javax.swing.JLabel();
        jLabelNik = new javax.swing.JLabel();
        jTextFieldNik = new javax.swing.JTextField();
        jButtonSewa = new javax.swing.JButton();
        jLabelAlamat = new javax.swing.JLabel();
        jTextFieldAlamat = new javax.swing.JTextField();
        jLabelHp = new javax.swing.JLabel();
        jTextFieldHp = new javax.swing.JTextField();
        jComboBoxMobil = new javax.swing.JComboBox<>();
        jLabelMobil = new javax.swing.JLabel();
        jDateChooserMulai = new com.toedter.calendar.JDateChooser();
        jDateChooserAkhir = new com.toedter.calendar.JDateChooser();
        jLabelDurasi = new javax.swing.JLabel();
        jLabelSampai = new javax.swing.JLabel();
        jLabelNopol = new javax.swing.JLabel();
        jLabelTahun = new javax.swing.JLabel();
        jComboBoxNopol = new javax.swing.JComboBox<>();
        jLabelHarga = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelTahunMobil = new javax.swing.JLabel();
        jPanelHargaMobil = new javax.swing.JPanel();
        jLabelHargaMobil = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jLabelKetersediaan = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabelTotalHarga = new javax.swing.JLabel();
        jComboBoxDriver = new javax.swing.JComboBox<>();
        jLabelDriver = new javax.swing.JLabel();

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Nama Penyewa");

        jMenu1.setText("jMenu1");

        jLabelMobil1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMobil1.setText("Tipe Mobil");

        jLabelMobil3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMobil3.setText("STATUS:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FORM PENYEWAAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 24))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1174, 659));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("picture");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(249, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(282, 282, 282))
        );

        jTextFieldNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNamaActionPerformed(evt);
            }
        });

        jLabelNama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNama.setText("Nama Penyewa");

        jLabelNik.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNik.setText("NIK");

        jTextFieldNik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNikActionPerformed(evt);
            }
        });

        jButtonSewa.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButtonSewa.setText("Sewa");
        jButtonSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSewaActionPerformed(evt);
            }
        });

        jLabelAlamat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelAlamat.setText("Alamat");

        jTextFieldAlamat.setToolTipText("");
        jTextFieldAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAlamatActionPerformed(evt);
            }
        });

        jLabelHp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelHp.setText("No. HP");

        jTextFieldHp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHpActionPerformed(evt);
            }
        });

        jComboBoxMobil.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxMobil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Mobil" }));
        jComboBoxMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMobilActionPerformed(evt);
            }
        });

        jLabelMobil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelMobil.setText("Tipe Mobil");

        jLabelDurasi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDurasi.setText("Durasi Penyewaan");

        jLabelSampai.setText("s/d");

        jLabelNopol.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelNopol.setText("NOPOL");

        jLabelTahun.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTahun.setText("TAHUN");

        jComboBoxNopol.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxNopol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No. Polisi" }));
        jComboBoxNopol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNopolActionPerformed(evt);
            }
        });

        jLabelHarga.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelHarga.setText("HARGA/HARI (Rp.)");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("TextField.shadow")));

        jLabelTahunMobil.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelTahunMobil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTahunMobil, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTahunMobil, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanelHargaMobil.setBackground(new java.awt.Color(255, 255, 255));
        jPanelHargaMobil.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("TextField.shadow")));

        jLabelHargaMobil.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelHargaMobil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelHargaMobilLayout = new javax.swing.GroupLayout(jPanelHargaMobil);
        jPanelHargaMobil.setLayout(jPanelHargaMobilLayout);
        jPanelHargaMobilLayout.setHorizontalGroup(
            jPanelHargaMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelHargaMobil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelHargaMobilLayout.setVerticalGroup(
            jPanelHargaMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelHargaMobil, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabelStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelStatus.setText("STATUS:");

        jLabelKetersediaan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelKetersediaan.setForeground(new java.awt.Color(255, 0, 51));
        jLabelKetersediaan.setText("stts");

        jLabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotal.setText("TOTAL:");

        jLabelTotalHarga.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotalHarga.setText("total");

        jComboBoxDriver.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jComboBoxDriver.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        jComboBoxDriver.setSelectedIndex(-1);
        jComboBoxDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDriverActionPerformed(evt);
            }
        });

        jLabelDriver.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelDriver.setText("Driver:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelMobil)
                                    .addComponent(jComboBoxMobil, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelNopol))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelTahun)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabelHarga)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanelHargaMobil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap())))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNama)
                                    .addComponent(jTextFieldNik)
                                    .addComponent(jTextFieldAlamat)
                                    .addComponent(jTextFieldHp))
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelKetersediaan)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jDateChooserMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelSampai)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jDateChooserAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelDriver))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelNama)
                                            .addComponent(jLabelNik)
                                            .addComponent(jLabelAlamat)
                                            .addComponent(jLabelHp)
                                            .addComponent(jLabelDurasi)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabelTotal)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabelTotalHarga)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelNik)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNik, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelAlamat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelHp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldHp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMobil)
                    .addComponent(jLabelNopol)
                    .addComponent(jLabelTahun)
                    .addComponent(jLabelHarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxMobil, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBoxNopol, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelHargaMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabelDurasi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelSampai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooserMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooserAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus)
                    .addComponent(jLabelKetersediaan))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTotal)
                    .addComponent(jLabelTotalHarga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButtonSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamaActionPerformed

    private void jTextFieldNikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNikActionPerformed

    private void jButtonSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSewaActionPerformed
        // TODO add your handling code here:
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        nama = this.jTextFieldNama.getText();
        nik = this.jTextFieldNik.getText();
        alamat = this.jTextFieldAlamat.getText();
        noHP = this.jTextFieldHp.getText();
        tipeMobil = this.jComboBoxMobil.getSelectedItem().toString();
        tahun = this.jLabelTahunMobil.getText();
        totalHarga = this.jLabelTotalHarga.getText();
        supir = this.jComboBoxDriver.getSelectedItem().toString();
        nopolbox = this.jComboBoxNopol.getSelectedItem().toString();
        String tglSewa = "";
        String tglAkhir = "";

        try {
            tglSewa = df.format(this.jDateChooserMulai.getDate());
            tglAkhir = df.format(this.jDateChooserAkhir.getDate());
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Pilih tanggal sewa dan tanggal kembali dengan benar.");
            return;
        }

        System.out.println(tglSewa);

        String ketersediaan = cekStatus(nopolbox);

        if (ketersediaan != null) {
            System.out.println("Sukses");
            insertIntoSewa(nama, nik, alamat, noHP, tipeMobil, tahun, nopolbox, tglSewa, tglAkhir, supir, totalHarga);
        } else {
            JOptionPane.showMessageDialog(this, "Maaf, mobil yang Anda pilih sedang tidak tersedia.");
        }
        /*if (ketersediaan != null) {
        System.out.println("Sukses");
        boolean isSuccess = insertDatabase(nama + "," + nik + "," + alamat + "," + noHP + "," + tipeMobil + "," + tahun + "," + nopolbox + "," + tglSewa + "," + tglAkhir + "," + supir + "," + totalHarga);
        
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Maaf, mobil yang Anda pilih sedang tidak tersedia.");
    }*/
        
    }//GEN-LAST:event_jButtonSewaActionPerformed

    private void jTextFieldAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAlamatActionPerformed

    private void jTextFieldHpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHpActionPerformed

    private void jComboBoxMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMobilActionPerformed
        // TODO add your handling code here:
        String condition = jComboBoxMobil.getSelectedItem().toString();
            while(jComboBoxNopol.getItemCount() > 1) {
                jComboBoxNopol.removeItemAt(1);
            }
            
        nopol(condition);
    }//GEN-LAST:event_jComboBoxMobilActionPerformed

    private void jComboBoxNopolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNopolActionPerformed
        // TODO add your handling code here:
        jLabelTahunMobil.setText("");
        jLabelHargaMobil.setText("");
        jLabelKetersediaan.setText("");
        String st = (String) jComboBoxNopol.getSelectedItem();
        String[] namaKolom = {"tahunMobil", "harga", "ketersediaan"};
        String namaTabel = "list_mobil";
        ResultSet rst = koneksi.databaseSelect(namaKolom, namaTabel, "nopol = '" + st + "'");
        //System.out.println(rst);
        try {
            while (rst.next()) {
                this.jLabelKetersediaan.setText(rst.getString("ketersediaan"));
                this.jLabelTahunMobil.setText(rst.getString("tahunMobil"));
                this.jLabelHargaMobil.setText(rst.getString("harga"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(sewa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //cekStatus();
    }//GEN-LAST:event_jComboBoxNopolActionPerformed

    private void jComboBoxDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDriverActionPerformed
        // TODO add your handling code here:
        String driver = jComboBoxDriver.getSelectedItem().toString();
        int harga = 0;
        if(driver == "Ya") {
            harga = 100_000;
        }
        totalHarga(harga);
    }//GEN-LAST:event_jComboBoxDriverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonSewa;
    private javax.swing.JComboBox<String> jComboBoxDriver;
    private javax.swing.JComboBox<String> jComboBoxMobil;
    private javax.swing.JComboBox<String> jComboBoxNopol;
    private com.toedter.calendar.JDateChooser jDateChooserAkhir;
    private com.toedter.calendar.JDateChooser jDateChooserMulai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelAlamat;
    private javax.swing.JLabel jLabelDriver;
    private javax.swing.JLabel jLabelDurasi;
    private javax.swing.JLabel jLabelHarga;
    private javax.swing.JLabel jLabelHargaMobil;
    private javax.swing.JLabel jLabelHp;
    private javax.swing.JLabel jLabelKetersediaan;
    private javax.swing.JLabel jLabelMobil;
    private javax.swing.JLabel jLabelMobil1;
    private javax.swing.JLabel jLabelMobil3;
    private javax.swing.JLabel jLabelNama;
    private javax.swing.JLabel jLabelNik;
    private javax.swing.JLabel jLabelNopol;
    private javax.swing.JLabel jLabelSampai;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTahun;
    private javax.swing.JLabel jLabelTahunMobil;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalHarga;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelHargaMobil;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTextField jTextFieldAlamat;
    private javax.swing.JTextField jTextFieldHp;
    private javax.swing.JTextField jTextFieldNama;
    private javax.swing.JTextField jTextFieldNik;
    // End of variables declaration//GEN-END:variables
}
