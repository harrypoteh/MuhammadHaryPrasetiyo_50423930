package com.mycompany.mskproduction.ui;

import com.mycompany.mskproduction.dao.UserDAO;
import com.mycompany.mskproduction.model.Session;
import com.mycompany.mskproduction.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Dialog login admin sebelum masuk ke aplikasi.
 */
public class LoginDialog extends JDialog {

    private final UserDAO userDAO = new UserDAO();
    private final JTextField usernameField = Ui.textField(18);
    private final JPasswordField passwordField = new JPasswordField(18);

    public LoginDialog(HomeFrame owner) {
        super(owner, "Login Admin", true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(26, 30, 22, 30));

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
        body.add(header, BorderLayout.NORTH);

        usernameField.setText("admin");
        passwordField.setFont(Ui.FONT);
        passwordField.setMargin(new java.awt.Insets(7, 10, 7, 10));

        JPanel form = new JPanel(new GridLayout(4, 1, 0, 6));
        form.setOpaque(false);
        form.add(Ui.label("Username"));
        form.add(usernameField);
        form.add(Ui.label("Password"));
        form.add(passwordField);
        body.add(form, BorderLayout.CENTER);

        JCheckBox showPassword = new JCheckBox("Tampilkan password");
        showPassword.setOpaque(false);
        showPassword.setFont(Ui.FONT_SMALL);
        showPassword.setForeground(Ui.MUTED);
        showPassword.addActionListener(event ->
                passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '\u2022'));

        JButton login = Ui.primaryButton("Masuk");
        login.setPreferredSize(new Dimension(0, 44));
        login.addActionListener(event -> login());
        getRootPane().setDefaultButton(login);

        JButton cancel = Ui.secondaryButton("Batal");
        cancel.addActionListener(event -> dispose());

        JPanel actions = new JPanel(new BorderLayout(10, 0));
        actions.setOpaque(false);
        actions.add(login, BorderLayout.CENTER);
        actions.add(cancel, BorderLayout.EAST);

        JPanel footer = new JPanel(new BorderLayout(0, 12));
        footer.setOpaque(false);
        footer.add(showPassword, BorderLayout.NORTH);
        footer.add(actions, BorderLayout.CENTER);

        JPanel hint = new JPanel(new BorderLayout());
        hint.setBackground(Ui.PRIMARY_SOFT);
        hint.setBorder(new EmptyBorder(10, 12, 10, 12));
        JLabel hintText = new JLabel("Akun bawaan: admin  /  admin123");
        hintText.setFont(Ui.FONT_SMALL);
        hintText.setForeground(new Color(146, 64, 14));
        hint.add(hintText, BorderLayout.WEST);

        JPanel south = new JPanel(new BorderLayout(0, 12));
        south.setOpaque(false);
        south.add(footer, BorderLayout.NORTH);
        south.add(hint, BorderLayout.SOUTH);
        body.add(south, BorderLayout.SOUTH);

        setContentPane(body);
        setSize(430, 460);
        setLocationRelativeTo(owner);
        SwingUtilities.invokeLater(() -> passwordField.requestFocusInWindow());
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
            Session.setUser(user);
            dispose();
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof HomeFrame) {
                    window.dispose();
                    break;
                }
            }
            new LoginSuccessFrame().setVisible(true);
        } catch (SQLException ex) {
            Ui.showError(this, "Tidak dapat terhubung ke database.\n\n" + ex.getMessage()
                    + "\n\nPeriksa server MySQL atau atur koneksi melalui tombol "
                    + "\"Pengaturan Database\" di halaman utama.");
        }
    }
}
