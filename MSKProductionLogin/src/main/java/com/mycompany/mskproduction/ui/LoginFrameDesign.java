package com.mycompany.mskproduction.ui;

import com.mycompany.mskproduction.config.DatabaseConfig;
import com.mycompany.mskproduction.dao.UserDAO;
import com.mycompany.mskproduction.model.Session;
import com.mycompany.mskproduction.model.User;
import javax.swing.JOptionPane;

/**
 * Login Frame - Editable dengan NetBeans GUI Builder
 */
public class LoginFrameDesign extends javax.swing.JFrame {

    private final UserDAO userDAO = new UserDAO(DatabaseConfig.getJdbcTemplate());

    public LoginFrameDesign() {
        initComponents();
        passwordField.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        brandLabel = new javax.swing.JLabel();
        spacer1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 6), new java.awt.Dimension(0, 6), new java.awt.Dimension(32767, 6));
        titleLabel = new javax.swing.JLabel();
        spacer2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 4), new java.awt.Dimension(0, 4), new java.awt.Dimension(32767, 4));
        subtitleLabel = new javax.swing.JLabel();
        spacer3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
        usernameLabel = new javax.swing.JLabel();
        spacer4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 6), new java.awt.Dimension(0, 6), new java.awt.Dimension(32767, 6));
        usernameField = new javax.swing.JTextField();
        spacer5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 12), new java.awt.Dimension(0, 12), new java.awt.Dimension(32767, 12));
        passwordLabel = new javax.swing.JLabel();
        spacer6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 6), new java.awt.Dimension(0, 6), new java.awt.Dimension(32767, 6));
        passwordField = new javax.swing.JPasswordField();
        spacer7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 12), new java.awt.Dimension(0, 12), new java.awt.Dimension(32767, 12));
        showPasswordCheckbox = new javax.swing.JCheckBox();
        spacer8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 16), new java.awt.Dimension(0, 16), new java.awt.Dimension(32767, 16));
        loginButton = new javax.swing.JButton();
        spacer9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 8), new java.awt.Dimension(0, 8), new java.awt.Dimension(32767, 8));
        cancelButton = new javax.swing.JButton();
        spacer10 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 12), new java.awt.Dimension(0, 12), new java.awt.Dimension(32767, 12));
        hintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Admin - MSK Production");
        setMinimumSize(new java.awt.Dimension(500, 480));
        setPreferredSize(new java.awt.Dimension(600, 550));
        setResizable(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(60, 80, 40, 80));
        mainPanel.setLayout(new javax.swing.BoxLayout(mainPanel, javax.swing.BoxLayout.PAGE_AXIS));

        brandLabel.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        brandLabel.setForeground(new java.awt.Color(180, 83, 9));
        brandLabel.setText("MSK PRODUCTION");
        mainPanel.add(brandLabel);
        mainPanel.add(spacer1);

        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(30, 41, 59));
        titleLabel.setText("Masuk sebagai Admin");
        mainPanel.add(titleLabel);
        mainPanel.add(spacer2);

        subtitleLabel.setForeground(new java.awt.Color(100, 116, 139));
        subtitleLabel.setText("Gunakan akun admin yang terdaftar di database.");
        mainPanel.add(subtitleLabel);
        mainPanel.add(spacer3);

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(30, 41, 59));
        usernameLabel.setText("Username");
        mainPanel.add(usernameLabel);
        mainPanel.add(spacer4);

        usernameField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        usernameField.setText("admin");
        usernameField.setMargin(new java.awt.Insets(7, 10, 7, 10));
        usernameField.setMaximumSize(new java.awt.Dimension(32767, 38));
        usernameField.setMinimumSize(new java.awt.Dimension(0, 38));
        usernameField.setPreferredSize(new java.awt.Dimension(0, 38));
        mainPanel.add(usernameField);
        mainPanel.add(spacer5);

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(30, 41, 59));
        passwordLabel.setText("Password");
        mainPanel.add(passwordLabel);
        mainPanel.add(spacer6);

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        passwordField.setMargin(new java.awt.Insets(7, 10, 7, 10));
        passwordField.setMaximumSize(new java.awt.Dimension(32767, 38));
        passwordField.setMinimumSize(new java.awt.Dimension(0, 38));
        passwordField.setPreferredSize(new java.awt.Dimension(0, 38));
        mainPanel.add(passwordField);
        mainPanel.add(spacer7);

        showPasswordCheckbox.setForeground(new java.awt.Color(100, 116, 139));
        showPasswordCheckbox.setText("Tampilkan password");
        showPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPasswordCheckboxActionPerformed(evt);
            }
        });
        mainPanel.add(showPasswordCheckbox);
        mainPanel.add(spacer8);

        loginButton.setBackground(new java.awt.Color(180, 83, 9));
        loginButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Masuk");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.setMaximumSize(new java.awt.Dimension(32767, 44));
        loginButton.setMinimumSize(new java.awt.Dimension(0, 44));
        loginButton.setPreferredSize(new java.awt.Dimension(0, 44));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        mainPanel.add(loginButton);
        mainPanel.add(spacer9);

        cancelButton.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(30, 41, 59));
        cancelButton.setText("Batal");
        cancelButton.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(226, 232, 240)), javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        cancelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelButton.setMaximumSize(new java.awt.Dimension(32767, 40));
        cancelButton.setMinimumSize(new java.awt.Dimension(0, 40));
        cancelButton.setPreferredSize(new java.awt.Dimension(0, 40));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        mainPanel.add(cancelButton);
        mainPanel.add(spacer10);

        hintPanel.setBackground(new java.awt.Color(255, 247, 237));
        hintPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 12, 10, 12));
        hintPanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        hintPanel.setLayout(new java.awt.BorderLayout());

        hintLabel.setForeground(new java.awt.Color(146, 64, 14));
        hintLabel.setText("Akun bawaan: admin  /  admin123");
        hintPanel.add(hintLabel, java.awt.BorderLayout.WEST);

        mainPanel.add(hintPanel);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void showPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPasswordCheckboxActionPerformed
        passwordField.setEchoChar(showPasswordCheckbox.isSelected() ? (char) 0 : '\u2022');
    }//GEN-LAST:event_showPasswordCheckboxActionPerformed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username dan password wajib diisi.", 
                "Terjadi Kesalahan", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user;
        try {
            user = userDAO.authenticate(username, password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Tidak dapat terhubung ke database.\n\n" + ex.getMessage()
                + "\n\nPeriksa server MySQL atau atur koneksi melalui "
                + "\"Pengaturan Database\" di halaman utama.",
                "Terjadi Kesalahan", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (user == null) {
            JOptionPane.showMessageDialog(this, 
                "Username atau password salah.", 
                "Terjadi Kesalahan", 
                JOptionPane.ERROR_MESSAGE);
            passwordField.selectAll();
            passwordField.requestFocusInWindow();
            return;
        }

        Session.setUser(user);
        new LoginSuccessFrameDesign().setVisible(true);
        dispose();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        new HomeFrameDesign().setVisible(true);
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LoginFrameDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrameDesign().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel hintPanel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JCheckBox showPasswordCheckbox;
    private javax.swing.Box.Filler spacer1;
    private javax.swing.Box.Filler spacer10;
    private javax.swing.Box.Filler spacer2;
    private javax.swing.Box.Filler spacer3;
    private javax.swing.Box.Filler spacer4;
    private javax.swing.Box.Filler spacer5;
    private javax.swing.Box.Filler spacer6;
    private javax.swing.Box.Filler spacer7;
    private javax.swing.Box.Filler spacer8;
    private javax.swing.Box.Filler spacer9;
    private javax.swing.JLabel subtitleLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
