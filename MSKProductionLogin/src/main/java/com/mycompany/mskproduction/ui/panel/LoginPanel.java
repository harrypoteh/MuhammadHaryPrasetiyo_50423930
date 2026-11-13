package com.mycompany.mskproduction.ui.panel;

import com.mycompany.mskproduction.config.DatabaseConfig;
import com.mycompany.mskproduction.dao.UserDAO;
import com.mycompany.mskproduction.model.User;
import com.mycompany.mskproduction.ui.Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Panel form login admin.
 */
public class LoginPanel extends JPanel {

    private final UserDAO userDAO = new UserDAO(DatabaseConfig.getJdbcTemplate());
    private final JTextField usernameField = Ui.textField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private final PanelNavigator navigator;

    public interface PanelNavigator {
        void onLoginSuccess(User user);
        void onLoginCancel();
    }

    public LoginPanel(PanelNavigator navigator) {
        this.navigator = navigator;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(60, 80, 40, 80));
        
        content.add(createHeaderPanel(), BorderLayout.NORTH);
        content.add(createFormPanel(), BorderLayout.CENTER);
        content.add(createFooterPanel(), BorderLayout.SOUTH);
        
        add(content, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        
        JLabel brand = new JLabel("MSK PRODUCTION");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 11));
        brand.setForeground(Ui.PRIMARY);
        brand.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel title = new JLabel("Masuk sebagai Admin");
        title.setFont(Ui.FONT.deriveFont(Font.BOLD, 23));
        title.setForeground(Ui.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Gunakan akun admin yang terdaftar di database.");
        subtitle.setFont(Ui.FONT_SMALL);
        subtitle.setForeground(Ui.MUTED);
        subtitle.setAlignmentX(LEFT_ALIGNMENT);
        
        header.add(brand);
        header.add(Box.createVerticalStrut(6));
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        
        return header;
    }

    private JPanel createFormPanel() {
        usernameField.setText("admin");
        passwordField.setFont(Ui.FONT);
        passwordField.setMargin(new java.awt.Insets(7, 10, 7, 10));

        JPanel form = new JPanel(new GridLayout(4, 1, 0, 6));
        form.setOpaque(false);
        form.add(Ui.label("Username"));
        form.add(usernameField);
        form.add(Ui.label("Password"));
        form.add(passwordField);
        
        return form;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        
        JCheckBox showPassword = new JCheckBox("Tampilkan password");
        showPassword.setOpaque(false);
        showPassword.setFont(Ui.FONT_SMALL);
        showPassword.setForeground(Ui.MUTED);
        showPassword.setAlignmentX(LEFT_ALIGNMENT);
        showPassword.addActionListener(event ->
                passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022'));
        footer.add(showPassword);
        footer.add(Box.createVerticalStrut(12));

        JButton login = Ui.primaryButton("Masuk");
        login.setPreferredSize(new Dimension(0, 44));
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        login.setAlignmentX(LEFT_ALIGNMENT);
        login.addActionListener(event -> login());
        footer.add(login);
        footer.add(Box.createVerticalStrut(8));

        JButton cancel = Ui.secondaryButton("Batal");
        cancel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cancel.setAlignmentX(LEFT_ALIGNMENT);
        cancel.addActionListener(event -> navigator.onLoginCancel());
        footer.add(cancel);
        footer.add(Box.createVerticalStrut(12));

        JPanel hint = new JPanel(new BorderLayout());
        hint.setBackground(Ui.PRIMARY_SOFT);
        hint.setBorder(new EmptyBorder(10, 12, 10, 12));
        hint.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        hint.setAlignmentX(LEFT_ALIGNMENT);
        
        JLabel hintText = new JLabel("Akun bawaan: admin  /  admin123");
        hintText.setFont(Ui.FONT_SMALL);
        hintText.setForeground(new Color(146, 64, 14));
        hint.add(hintText, BorderLayout.WEST);
        footer.add(hint);
        
        return footer;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            Ui.showError(this, "Username dan password wajib diisi.");
            return;
        }
        
        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                Ui.showError(this, "Username atau password salah.");
                passwordField.selectAll();
                passwordField.requestFocusInWindow();
                return;
            }
            navigator.onLoginSuccess(user);
        } catch (Exception ex) {
            Ui.showError(this, "Tidak dapat terhubung ke database.\n\n" + ex.getMessage()
                    + "\n\nPeriksa server MySQL atau atur koneksi melalui "
                    + "\"Pengaturan Database\" di halaman utama.");
        }
    }
    
    public void reset() {
        usernameField.setText("admin");
        passwordField.setText("");
        passwordField.requestFocusInWindow();
    }
}