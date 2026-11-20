package com.mycompany.mskproduction.ui;

import com.mycompany.mskproduction.model.Session;
import com.mycompany.mskproduction.model.User;
import com.mycompany.mskproduction.ui.dialog.DatabaseDialog;
import com.mycompany.mskproduction.ui.panel.HomePanel;
import com.mycompany.mskproduction.ui.panel.LoginPanel;
import com.mycompany.mskproduction.ui.panel.LoginSuccessPanel;
import com.mycompany.mskproduction.ui.panel.ReportPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Jendela utama aplikasi.
 *
 * <p>Seluruh alur berjalan di dalam satu jendela memakai {@link CardLayout},
 * sehingga tidak ada jendela baru yang dibuka atau ditutup saat berpindah
 * halaman. Halaman yang tersedia: {@link HomePanel}, {@link LoginPanel},
 * {@link LoginSuccessPanel}, dan {@link ReportPanel}.</p>
 */
public class AppFrame extends JFrame implements
        HomePanel.PanelNavigator,
        LoginPanel.PanelNavigator,
        LoginSuccessPanel.PanelNavigator,
        ReportPanel.PanelNavigator {

    private static final String CARD_HOME = "HOME";
    private static final String CARD_LOGIN = "LOGIN";
    private static final String CARD_SUCCESS = "SUCCESS";
    private static final String CARD_REPORT = "REPORT";
    private static final String TITLE = "MSK Production - Sistem Produksi Roti";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    private final HomePanel homePanel = new HomePanel(this);
    private final LoginPanel loginPanel = new LoginPanel(this);
    private LoginSuccessPanel loginSuccessPanel;
    private ReportPanel reportPanel;

    public AppFrame() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 740);
        setMinimumSize(new Dimension(1180, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        contentPanel.add(homePanel, CARD_HOME);
        contentPanel.add(loginPanel, CARD_LOGIN);
        add(contentPanel, BorderLayout.CENTER);

        showHome();
    }

    public void showHome() {
        setTitle(TITLE);
        homePanel.checkDatabase();
        cardLayout.show(contentPanel, CARD_HOME);
    }

    public void showLogin() {
        setTitle("Login Admin - MSK Production");
        loginPanel.reset();
        cardLayout.show(contentPanel, CARD_LOGIN);
    }

    public void showLoginSuccess() {
        setTitle("Login Berhasil - MSK Production");

        // Panel dibuat ulang setiap login agar data akun yang ditampilkan
        // selalu berasal dari sesi terbaru, bukan dari sesi sebelumnya.
        if (loginSuccessPanel != null) {
            contentPanel.remove(loginSuccessPanel);
        }
        loginSuccessPanel = new LoginSuccessPanel(this);
        contentPanel.add(loginSuccessPanel, CARD_SUCCESS);
        contentPanel.revalidate();
        contentPanel.repaint();

        cardLayout.show(contentPanel, CARD_SUCCESS);
    }

    /**
     * Menampilkan halaman laporan. Panel dibuat ulang setiap kali dibuka agar
     * laporan selalu diisi dengan data terbaru dari database.
     */
    public void showReport() {
        setTitle("Laporan Akun - MSK Production");

        if (reportPanel != null) {
            contentPanel.remove(reportPanel);
        }
        reportPanel = new ReportPanel(this);
        contentPanel.add(reportPanel, CARD_REPORT);
        contentPanel.revalidate();
        contentPanel.repaint();

        cardLayout.show(contentPanel, CARD_REPORT);
    }

    // ---------------------------------------------------------------
    // HomePanel.PanelNavigator
    // ---------------------------------------------------------------

    @Override
    public void showLoginPanel() {
        showLogin();
    }

    @Override
    public void showDatabaseDialog() {
        new DatabaseDialog(this).setVisible(true);
    }

    // ---------------------------------------------------------------
    // LoginPanel.PanelNavigator
    // ---------------------------------------------------------------

    @Override
    public void onLoginSuccess(User user) {
        Session.setUser(user);
        showLoginSuccess();
    }

    @Override
    public void onLoginCancel() {
        showHome();
    }

    @Override
    public void onShowReport() {
        showReport();
    }

    // ---------------------------------------------------------------
    // LoginSuccessPanel.PanelNavigator
    // ---------------------------------------------------------------

    @Override
    public void onLogout() {
        Session.clear();
        showHome();
    }

    @Override
    public void onClose() {
        dispose();
        System.exit(0);
    }

    // ---------------------------------------------------------------
    // ReportPanel.PanelNavigator
    // ---------------------------------------------------------------

    @Override
    public void onReportClose() {
        showLoginSuccess();
    }
}
