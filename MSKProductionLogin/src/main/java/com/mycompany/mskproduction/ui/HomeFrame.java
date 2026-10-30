package com.mycompany.mskproduction.ui;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.ui.dialog.DatabaseDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

/**
 * Halaman awal aplikasi. Pengguna masuk sebagai admin untuk membuka dashboard.
 */
public class HomeFrame extends JFrame {

    private final JLabel statusDot = new JLabel("\u25CF");
    private final JLabel statusTitle = new JLabel("Memeriksa database...");
    private final JLabel statusDetail = new JLabel(Database.getDescription());
    private final JLabel statusHint = new JLabel(" ");

    public HomeFrame() {
        setTitle("MSK Production - Sistem Produksi Roti");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1020, 660);
        setMinimumSize(new Dimension(960, 620));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.add(createHero(), BorderLayout.WEST);
        root.add(createWelcome(), BorderLayout.CENTER);
        setContentPane(root);
        checkDatabase();
    }

    private JPanel createHero() {
        JPanel hero = new JPanel();
        hero.setBackground(Ui.SIDEBAR);
        hero.setPreferredSize(new Dimension(430, 0));
        hero.setBorder(new EmptyBorder(42, 42, 34, 38));
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("MSK");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(LEFT_ALIGNMENT);
        JLabel production = new JLabel("PRODUCTION");
        production.setFont(new Font("Segoe UI", Font.BOLD, 13));
        production.setForeground(new Color(251, 191, 36));
        production.setAlignmentX(LEFT_ALIGNMENT);
        JLabel tagline = new JLabel("Bakery Production System");
        tagline.setFont(Ui.FONT);
        tagline.setForeground(new Color(168, 162, 158));
        tagline.setAlignmentX(LEFT_ALIGNMENT);
        hero.add(logo);
        hero.add(production);
        hero.add(Box.createVerticalStrut(4));
        hero.add(tagline);
        hero.add(Box.createVerticalStrut(52));

        JLabel headline = new JLabel("<html>Produksi roti lebih rapi,<br>stok lebih terkontrol.</html>");
        headline.setFont(new Font("Segoe UI", Font.BOLD, 25));
        headline.setForeground(Color.WHITE);
        headline.setAlignmentX(LEFT_ALIGNMENT);
        hero.add(headline);
        hero.add(Box.createVerticalStrut(14));

        JLabel description = new JLabel("<html>Kelola bahan baku, resep, pesanan, dan hasil "
                + "produksi roti dalam satu aplikasi desktop terintegrasi.</html>");
        description.setFont(Ui.FONT);
        description.setForeground(new Color(214, 211, 209));
        description.setAlignmentX(LEFT_ALIGNMENT);
        hero.add(description);
        hero.add(Box.createVerticalStrut(34));

        String[] features = {
            "Manajemen bahan baku dan produk jadi",
            "Resep produksi dengan hitung kebutuhan otomatis",
            "Validasi stok sebelum produksi dijalankan",
            "Laporan pemakaian bahan dan hasil produksi"
        };
        for (String feature : features) {
            hero.add(featureRow(feature));
            hero.add(Box.createVerticalStrut(10));
        }

        hero.add(Box.createVerticalGlue());
        JLabel footer = new JLabel("<html>Laboratorium Informatika<br>Universitas Gunadarma &bull; 2026</html>");
        footer.setFont(Ui.FONT_SMALL);
        footer.setForeground(new Color(120, 113, 108));
        footer.setAlignmentX(LEFT_ALIGNMENT);
        hero.add(footer);
        return hero;
    }

    private JPanel featureRow(String text) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
        row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel bullet = new JLabel("\u25AA");
        bullet.setFont(new Font("Segoe UI", Font.BOLD, 15));
        bullet.setForeground(new Color(251, 191, 36));
        JLabel label = new JLabel(text);
        label.setFont(Ui.FONT);
        label.setForeground(new Color(231, 229, 228));
        row.add(bullet, BorderLayout.WEST);
        row.add(label, BorderLayout.CENTER);
        return row;
    }

    private JPanel createWelcome() {
        JPanel welcome = new JPanel();
        welcome.setBackground(Color.WHITE);
        welcome.setBorder(new EmptyBorder(48, 54, 34, 54));
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        welcome.add(Box.createVerticalGlue());

        JLabel badge = new JLabel("SISTEM PRODUKSI ROTI");
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Ui.PRIMARY);
        badge.setAlignmentX(LEFT_ALIGNMENT);
        welcome.add(badge);
        welcome.add(Box.createVerticalStrut(10));

        JLabel title = new JLabel("<html>Selamat Datang di<br>MSK Production</html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Ui.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        welcome.add(title);
        welcome.add(Box.createVerticalStrut(12));

        JLabel description = new JLabel("<html>Aplikasi pengelolaan produksi roti untuk mencatat bahan baku, "
                + "produk jadi, resep, pesanan, serta hasil produksi secara terstruktur.</html>");
        description.setFont(Ui.FONT.deriveFont(14f));
        description.setForeground(Ui.MUTED);
        description.setAlignmentX(LEFT_ALIGNMENT);
        welcome.add(description);
        welcome.add(Box.createVerticalStrut(26));

        JButton login = Ui.primaryButton("Masuk sebagai Admin");
        login.setFont(Ui.FONT_BOLD.deriveFont(14f));
        login.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        login.setPreferredSize(new Dimension(0, 46));
        login.setAlignmentX(LEFT_ALIGNMENT);
        login.addActionListener(event -> new LoginDialog(this).setVisible(true));
        welcome.add(login);
        welcome.add(Box.createVerticalStrut(10));

        JButton settings = Ui.secondaryButton("Pengaturan Database");
        settings.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        settings.setAlignmentX(LEFT_ALIGNMENT);
        settings.addActionListener(event -> {
            DatabaseDialog dialog = new DatabaseDialog(this);
            dialog.setVisible(true);
            if (dialog.isSaved()) {
                checkDatabase();
            }
        });
        welcome.add(settings);
        welcome.add(Box.createVerticalStrut(24));

        welcome.add(createStatusPanel());
        welcome.add(Box.createVerticalStrut(20));

        JLabel hint = new JLabel("Akun admin bawaan: admin / admin123");
        hint.setFont(Ui.FONT_SMALL);
        hint.setForeground(Ui.MUTED);
        hint.setAlignmentX(LEFT_ALIGNMENT);
        welcome.add(hint);

        welcome.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("MSK Production v1.0  \u2022  Java Desktop & MySQL");
        footer.setFont(Ui.FONT_SMALL);
        footer.setForeground(Ui.MUTED);
        footer.setAlignmentX(LEFT_ALIGNMENT);
        welcome.add(footer);
        return welcome;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(248, 250, 252));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createLineBorder(Ui.BORDER));

        statusDot.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusDot.setForeground(Ui.MUTED);
        statusDot.setBorder(new EmptyBorder(0, 14, 0, 0));
        panel.add(statusDot, BorderLayout.WEST);

        JPanel text = new JPanel(new GridLayout(3, 1, 0, 0));
        text.setOpaque(false);
        statusTitle.setFont(Ui.FONT_BOLD);
        statusTitle.setForeground(Ui.TEXT);
        statusDetail.setFont(Ui.FONT_SMALL);
        statusDetail.setForeground(Ui.MUTED);
        statusHint.setFont(Ui.FONT_SMALL);
        statusHint.setForeground(Ui.DANGER);
        text.add(statusTitle);
        text.add(statusDetail);
        text.add(statusHint);
        panel.add(text, BorderLayout.CENTER);
        return panel;
    }

    private void checkDatabase() {
        statusDot.setForeground(Ui.MUTED);
        statusTitle.setText("Memeriksa koneksi database...");
        statusTitle.setForeground(Ui.TEXT);
        statusDetail.setText(Database.getDescription());
        statusHint.setText(" ");
        new SwingWorker<Boolean, Void>() {
            private String error = "";

            @Override
            protected Boolean doInBackground() {
                try {
                    Database.testConnection(Database.getHost(), Database.getPort(),
                            Database.getUser(), Database.getPassword());
                    return true;
                } catch (Exception ex) {
                    error = ex.getMessage();
                    return false;
                }
            }

            @Override
            protected void done() {
                try {
                    if (get()) {
                        statusDot.setForeground(Ui.SUCCESS);
                        statusTitle.setText("Database terhubung");
                        statusTitle.setForeground(Ui.SUCCESS);
                        statusHint.setText(" ");
                    } else {
                        statusDot.setForeground(Ui.DANGER);
                        statusTitle.setText("Database belum terhubung");
                        statusTitle.setForeground(Ui.DANGER);
                        statusHint.setText("Periksa MySQL atau buka Pengaturan Database.");
                        if (error != null && !error.isBlank()) {
                            statusDetail.setText(Database.getDescription());
                            statusDetail.setToolTipText(error);
                        }
                    }
                } catch (Exception ex) {
                    statusTitle.setText("Database belum terhubung");
                    statusTitle.setForeground(Ui.DANGER);
                }
            }
        }.execute();
    }
}
