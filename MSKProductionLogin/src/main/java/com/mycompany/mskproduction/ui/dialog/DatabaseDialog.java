package com.mycompany.mskproduction.ui.dialog;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.ui.Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Dialog pengaturan koneksi MySQL. Data disimpan di folder pengguna,
 * sehingga tidak perlu mengubah kode program saat password MySQL berbeda.
 */
public class DatabaseDialog extends javax.swing.JDialog {

    private final JTextField hostField = Ui.textField(18);
    private final JTextField portField = Ui.textField(18);
    private final JTextField databaseField = Ui.textField(18);
    private final JTextField userField = Ui.textField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private boolean saved;

    public DatabaseDialog(Window owner) {
        super(owner, "Pengaturan Database", ModalityType.APPLICATION_MODAL);
        setResizable(false);

        hostField.setText(Database.getHost());
        portField.setText(String.valueOf(Database.getPort()));
        databaseField.setText(Database.getDatabaseName());
        userField.setText(Database.getUser());
        passwordField.setText(Database.getPassword());
        passwordField.setFont(Ui.FONT);

        FormPanel form = new FormPanel();
        form.addRow("Host", hostField);
        form.addRow("Port", portField);
        form.addRow("Nama Database", databaseField);
        form.addRow("Username", userField);
        form.addRow("Password", passwordField);
        form.addHint("Contoh XAMPP/MariaDB: host localhost, port 3306, user root, password kosong.");
        form.addHint("Database dan tabel akan dibuat otomatis bila belum tersedia.");

        JButton test = Ui.secondaryButton("Tes Koneksi");
        test.addActionListener(event -> testConnection());
        JButton cancel = Ui.secondaryButton("Batal");
        cancel.addActionListener(event -> dispose());
        JButton save = Ui.primaryButton("Simpan");
        save.addActionListener(event -> save());
        getRootPane().setDefaultButton(save);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(6, 0, 0, 0));
        footer.add(test, BorderLayout.WEST);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);
        right.add(cancel);
        right.add(save);
        footer.add(right, BorderLayout.EAST);

        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(22, 26, 18, 26));
        body.add(DialogSupport.header("Koneksi Database MySQL",
                "Sesuaikan pengaturan dengan server MySQL yang digunakan."), BorderLayout.NORTH);
        body.add(form, BorderLayout.CENTER);
        body.add(footer, BorderLayout.SOUTH);
        setContentPane(body);
        pack();
        setLocationRelativeTo(owner);
    }

    private int port() throws NumberFormatException {
        return Integer.parseInt(portField.getText().trim());
    }

    private void testConnection() {
        try {
            Database.testConnection(hostField.getText().trim(), port(),
                    userField.getText().trim(), new String(passwordField.getPassword()));
            Ui.showInfo(this, "Koneksi berhasil. Server MySQL dapat dihubungi.");
        } catch (NumberFormatException ex) {
            Ui.showError(this, "Port harus berupa angka, contoh: 3306.");
        } catch (SQLException ex) {
            Ui.showError(this, "Koneksi gagal.\n\n" + ex.getMessage()
                    + "\n\nPastikan server MySQL sudah berjalan dan data login benar.");
        }
    }

    private void save() {
        String host = hostField.getText().trim();
        String database = databaseField.getText().trim();
        String user = userField.getText().trim();
        if (host.isEmpty() || database.isEmpty() || user.isEmpty()) {
            Ui.showError(this, "Host, nama database, dan username wajib diisi.");
            return;
        }
        try {
            Database.saveConfig(host, port(), database, user,
                    new String(passwordField.getPassword()));
            saved = true;
            Ui.showInfo(this, "Pengaturan berhasil disimpan.\nDatabase " + database
                    + " siap digunakan.");
            dispose();
        } catch (NumberFormatException ex) {
            Ui.showError(this, "Port harus berupa angka, contoh: 3306.");
        } catch (Exception ex) {
            Ui.showError(this, "Pengaturan tersimpan, tetapi database gagal disiapkan.\n\n"
                    + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
