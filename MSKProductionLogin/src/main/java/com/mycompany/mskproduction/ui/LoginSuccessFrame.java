package com.mycompany.mskproduction.ui;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.model.Session;
import com.mycompany.mskproduction.model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Halaman penutup alur login. Menampilkan informasi akun yang berhasil masuk.
 */
public class LoginSuccessFrame extends JFrame {

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("id", "ID"));

    public LoginSuccessFrame() {
        setTitle("MSK Production - Login Berhasil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(780, 540);
        setMinimumSize(new Dimension(720, 500));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createBody(), BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Ui.SIDEBAR);
        header.setBorder(new EmptyBorder(26, 34, 24, 34));
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("MSK PRODUCTION");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 22));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(LEFT_ALIGNMENT);
        JLabel subtitle = new JLabel("Sistem Produksi Roti");
        subtitle.setFont(Ui.FONT);
        subtitle.setForeground(new Color(168, 162, 158));
        subtitle.setAlignmentX(LEFT_ALIGNMENT);

        header.add(brand);
        header.add(Box.createVerticalStrut(3));
        header.add(subtitle);
        return header;
    }

    private JPanel createBody() {
        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(30, 34, 24, 34));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel check = new JLabel("\u2713");
        check.setFont(new Font("Segoe UI", Font.BOLD, 42));
        check.setForeground(Ui.SUCCESS);
        check.setAlignmentX(LEFT_ALIGNMENT);
        content.add(check);
        content.add(Box.createVerticalStrut(2));

        JLabel badge = new JLabel("LOGIN BERHASIL");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Ui.SUCCESS);
        badge.setAlignmentX(LEFT_ALIGNMENT);
        content.add(badge);
        content.add(Box.createVerticalStrut(8));

        User user = Session.getUser();
        String nama = user == null ? "Administrator" : user.getNama();
        JLabel title = new JLabel("Selamat datang, " + nama);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Ui.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        content.add(title);
        content.add(Box.createVerticalStrut(6));

        JLabel description = new JLabel("Anda berhasil masuk ke aplikasi MSK Production.");
        description.setFont(Ui.FONT.deriveFont(14f));
        description.setForeground(Ui.MUTED);
        description.setAlignmentX(LEFT_ALIGNMENT);
        content.add(description);
        content.add(Box.createVerticalStrut(22));
        content.add(createAccountCard(user));
        content.add(Box.createVerticalStrut(16));

        JLabel note = new JLabel("Sesi login aktif. Gunakan tombol Keluar untuk kembali ke halaman awal.");
        note.setFont(Ui.FONT_SMALL);
        note.setForeground(Ui.MUTED);
        note.setAlignmentX(LEFT_ALIGNMENT);
        content.add(note);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);

        JButton logout = Ui.secondaryButton("Keluar");
        logout.addActionListener(event -> logout());
        JButton close = Ui.primaryButton("Tutup Aplikasi");
        close.addActionListener(event -> {
            dispose();
            System.exit(0);
        });
        actions.add(logout);
        actions.add(close);

        JLabel time = new JLabel("Masuk pada " + LocalDateTime.now().format(TIME_FORMAT));
        time.setFont(Ui.FONT_SMALL);
        time.setForeground(Ui.MUTED);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(time, BorderLayout.WEST);
        footer.add(actions, BorderLayout.EAST);

        body.add(content, BorderLayout.CENTER);
        body.add(footer, BorderLayout.SOUTH);
        return body;
    }

    private JPanel createAccountCard(User user) {
        JPanel card = new JPanel(new GridLayout(2, 2, 12, 12));
        card.setOpaque(false);
        card.setAlignmentX(LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 132));
        card.add(infoBox("Username", user == null ? "-" : user.getUsername()));
        card.add(infoBox("Nama", user == null ? "-" : user.getNama()));
        card.add(infoBox("Hak Akses", user == null ? "-" : roleLabel(user.getRole())));
        card.add(infoBox("Database", Database.getDescription()));
        return card;
    }

    private JPanel infoBox(String label, String value) {
        JPanel box = new JPanel();
        box.setBackground(new Color(248, 250, 252));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Ui.BORDER),
                new EmptyBorder(12, 14, 12, 14)));
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel labelText = new JLabel(label);
        labelText.setFont(Ui.FONT_SMALL);
        labelText.setForeground(Ui.MUTED);
        labelText.setAlignmentX(LEFT_ALIGNMENT);

        JLabel valueText = new JLabel(value);
        valueText.setFont(Ui.FONT_BOLD.deriveFont(14f));
        valueText.setForeground(Ui.TEXT);
        valueText.setAlignmentX(LEFT_ALIGNMENT);

        box.add(labelText);
        box.add(Box.createVerticalStrut(4));
        box.add(valueText);
        return box;
    }

    private String roleLabel(String role) {
        if (role == null || role.isBlank()) {
            return "Admin";
        }
        return "ADMIN".equalsIgnoreCase(role) ? "Administrator" : role;
    }

    private void logout() {
        Session.clear();
        dispose();
        new HomeFrame().setVisible(true);
    }
}
