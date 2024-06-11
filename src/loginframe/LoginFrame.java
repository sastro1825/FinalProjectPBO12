package loginframe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login - Rental Mobil");
        setSize(500, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(0x0B60B0));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel logoLabel = new JLabel();
        try {
            BufferedImage logoImage = ImageIO.read(getClass().getResource("/logopbo.png"));
            Image scaledLogoImage = logoImage.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledLogoImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        add(logoLabel, gbc);

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");
        usernameField = new JTextField(20); 
        passwordField = new JPasswordField(20); 
        JButton loginButton = new JButton("Login");

        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        usernameField.setForeground(Color.WHITE);
        passwordField.setForeground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);

        usernameField.setBackground(new Color(0x0B60B0));
        passwordField.setBackground(new Color(0x0B60B0));
        loginButton.setBackground(new Color(0xF0EDCF)); 
        loginButton.setOpaque(true); 

        gbc.gridwidth = 1; 
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 
        add(usernameField, gbc);

        gbc.gridwidth = 2; 
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        gbc.gridx = 1; 
        gbc.gridy = 2; 
        add(passwordField, gbc);


        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.weightx = 0.0; 
        add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (authenticate(username, new String(password))) {
            JOptionPane.showMessageDialog(this, "Login Sukses");
        } else {
            JOptionPane.showMessageDialog(this, "Salah username atau password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticate(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean isValidUser = false;

        try {
            String url = "jdbc:mysql://localhost:3306/rental_mobil";
            String dbUsername = "root";
            String dbPassword = "";

            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isValidUser = true;
                this.dispose();

                menu Menu = new menu();
                Menu.setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isValidUser;
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame();
            }
        });
    }
}
