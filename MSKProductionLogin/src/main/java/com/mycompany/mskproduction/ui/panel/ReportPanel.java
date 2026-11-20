package com.mycompany.mskproduction.ui.panel;

import com.mycompany.mskproduction.report.ReportService;
import com.mycompany.mskproduction.ui.Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Halaman laporan: menampilkan pratinjau laporan JasperReports dan
 * menyediakan tombol ekspor ke PDF, Excel, serta cetak.
 */
public class ReportPanel extends JPanel {

    private final PanelNavigator navigator;
    private final JPanel previewHolder = new JPanel(new BorderLayout());
    private final JLabel statusLabel = Ui.muted("Menyiapkan laporan...");
    private final JButton exportPdf = Ui.secondaryButton("Ekspor PDF");
    private final JButton exportXlsx = Ui.secondaryButton("Ekspor Excel");
    private final JButton print = Ui.secondaryButton("Cetak");
    private final JButton refresh = Ui.secondaryButton("Muat Ulang");

    private JasperPrint jasperPrint;

    public interface PanelNavigator {
        void onReportClose();
    }

    public ReportPanel(PanelNavigator navigator) {
        this.navigator = navigator;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createHeader(), BorderLayout.NORTH);
        add(createPreviewArea(), BorderLayout.CENTER);

        exportPdf.addActionListener(event -> export(true));
        exportXlsx.addActionListener(event -> export(false));
        print.addActionListener(event -> printReport());
        refresh.addActionListener(event -> loadReport());

        loadReport();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(22, 26, 16, 26));
        header.setLayout(new BorderLayout(0, 14));

        JPanel titles = new JPanel();
        titles.setOpaque(false);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));

        JLabel badge = new JLabel("JASPERREPORTS");
        badge.setFont(Ui.FONT_BOLD.deriveFont(11f));
        badge.setForeground(Ui.PRIMARY);
        badge.setAlignmentX(LEFT_ALIGNMENT);
        badge.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));

        JLabel title = new JLabel("Laporan Daftar Akun Administrator");
        title.setFont(Ui.TITLE_FONT.deriveFont(21f));
        title.setForeground(Ui.TEXT);
        title.setAlignmentX(LEFT_ALIGNMENT);
        // BoxLayout memberi lebar tepat sebesar preferred size, sehingga huruf
        // terakhir judul bisa terpotong. Maximum size yang lebar membuat label
        // mengisi seluruh ruang yang tersedia.
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel subtitle = new JLabel("Data diambil langsung dari tabel users pada database aplikasi.");
        subtitle.setFont(Ui.FONT_SMALL);
        subtitle.setForeground(Ui.MUTED);
        subtitle.setAlignmentX(LEFT_ALIGNMENT);
        subtitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 18));

        titles.add(badge);
        titles.add(Box.createVerticalStrut(4));
        titles.add(title);
        titles.add(Box.createVerticalStrut(4));
        titles.add(subtitle);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actions.setOpaque(false);

        JButton close = Ui.secondaryButton("Kembali");
        close.addActionListener(event -> navigator.onReportClose());

        actions.add(refresh);
        actions.add(exportPdf);
        actions.add(exportXlsx);
        actions.add(print);
        actions.add(close);

        // Tombol dibuat lebih ringkas agar judul laporan tidak terpotong
        // pada lebar jendela minimum aplikasi (1180 px).
        for (JButton button : new JButton[]{refresh, exportPdf, exportXlsx, print, close}) {
            button.setBorder(new EmptyBorder(7, 12, 7, 12));
            button.setFont(Ui.FONT_BOLD.deriveFont(12f));
        }

        JPanel top = new JPanel(new BorderLayout(16, 0));
        top.setOpaque(false);
        top.add(titles, BorderLayout.CENTER);
        top.add(actions, BorderLayout.EAST);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.add(statusLabel, BorderLayout.WEST);

        header.add(top, BorderLayout.NORTH);
        header.add(footer, BorderLayout.SOUTH);
        return header;
    }

    private JPanel createPreviewArea() {
        previewHolder.setBackground(new Color(241, 245, 249));
        previewHolder.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Ui.BORDER));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(0, 26, 22, 26));
        wrapper.add(previewHolder, BorderLayout.CENTER);
        return wrapper;
    }

    /** Mengisi laporan di background agar antarmuka tidak membeku. */
    private void loadReport() {
        setBusy(true);
        statusLabel.setText("Mengambil data dari database dan menyiapkan laporan...");

        new SwingWorker<JasperPrint, Void>() {
            @Override
            protected JasperPrint doInBackground() throws Exception {
                return ReportService.fill();
            }

            @Override
            protected void done() {
                try {
                    jasperPrint = get();
                    previewHolder.removeAll();
                    previewHolder.add(new JRViewer(jasperPrint), BorderLayout.CENTER);
                    previewHolder.revalidate();
                    previewHolder.repaint();
                    statusLabel.setText("Laporan siap. Jumlah halaman: " + jasperPrint.getPages().size());
                    setBusy(false);
                } catch (Exception ex) {
                    showFailure(ex);
                }
            }
        }.execute();
    }

    private void showFailure(Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String message = cause.getMessage() == null ? cause.toString() : cause.getMessage();

        previewHolder.removeAll();
        JLabel error = new JLabel("<html><body style='width:520px'>"
                + "<b>Laporan gagal dibuat.</b><br><br>"
                + message.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>")
                + "</body></html>");
        error.setFont(Ui.FONT);
        error.setForeground(Ui.DANGER);
        error.setBorder(new EmptyBorder(24, 24, 24, 24));
        previewHolder.add(error, BorderLayout.NORTH);
        previewHolder.revalidate();
        previewHolder.repaint();

        statusLabel.setText("Laporan gagal dibuat.");
        setBusy(false);
    }

    private void setBusy(boolean busy) {
        exportPdf.setEnabled(!busy);
        exportXlsx.setEnabled(!busy);
        print.setEnabled(!busy);
        refresh.setEnabled(!busy);
    }

    private void export(boolean pdf) {
        if (jasperPrint == null) {
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(pdf ? "Simpan laporan sebagai PDF" : "Simpan laporan sebagai Excel");
        String base = "Laporan-Akun-MSK-" + new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        chooser.setSelectedFile(new File(base + (pdf ? ".pdf" : ".xlsx")));
        chooser.setFileFilter(new FileNameExtensionFilter(pdf ? "Berkas PDF (*.pdf)" : "Berkas Excel (*.xlsx)",
                pdf ? "pdf" : "xlsx"));

        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File target = chooser.getSelectedFile();
        String extension = pdf ? ".pdf" : ".xlsx";
        if (!target.getName().toLowerCase().endsWith(extension)) {
            target = new File(target.getParentFile(), target.getName() + extension);
        }

        final File file = target;
        try {
            if (pdf) {
                ReportService.exportPdf(file);
            } else {
                ReportService.exportXlsx(file);
            }
            int open = JOptionPane.showConfirmDialog(this,
                    "Laporan berhasil disimpan:\n" + file.getAbsolutePath() + "\n\nBuka berkasnya sekarang?",
                    "Ekspor Berhasil", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (open == JOptionPane.YES_OPTION && java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(file);
            }
        } catch (Exception ex) {
            Ui.showError(this, "Gagal mengekspor laporan.\n\n" + ex.getMessage());
        }
    }

    private void printReport() {
        if (jasperPrint == null) {
            return;
        }
        try {
            net.sf.jasperreports.engine.JasperPrintManager.printReport(jasperPrint, true);
        } catch (Exception ex) {
            Ui.showError(this, "Gagal mencetak laporan.\n\n" + ex.getMessage());
        }
    }
}
