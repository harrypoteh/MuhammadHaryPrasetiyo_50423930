package com.mycompany.mskproduction.report;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.model.Session;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Menghubungkan desain laporan {@code /report/mskreport.jrxml} dengan
 * database aplikasi.
 *
 * <p>Alur kerjanya mengikuti tiga tahap standar JasperReports:</p>
 * <ol>
 *   <li><b>compile</b> — berkas {@code .jrxml} diubah menjadi objek
 *       {@link JasperReport} (hasilnya di-cache agar tidak dikompilasi
 *       berulang kali);</li>
 *   <li><b>fill</b> — laporan diisi data hasil query dari tabel {@code users}
 *       memakai koneksi milik kelas {@link Database}, sehingga laporan selalu
 *       membaca database yang sedang aktif di aplikasi;</li>
 *   <li><b>export</b> — hasil isian dikeluarkan ke PDF, XLSX, atau HTML.</li>
 * </ol>
 */
public final class ReportService {

    /** Lokasi desain laporan di dalam classpath (folder resources). */
    private static final String REPORT_RESOURCE = "/report/mskreport.jrxml";

    private static JasperReport cachedReport;

    private ReportService() {
    }

    /**
     * Mengompilasi desain laporan. Hasilnya disimpan supaya pemanggilan
     * berikutnya tidak perlu mengompilasi ulang.
     */
    public static synchronized JasperReport getReport() throws JRException {
        if (cachedReport == null) {
            try (InputStream input = ReportService.class.getResourceAsStream(REPORT_RESOURCE)) {
                if (input == null) {
                    throw new JRException("Desain laporan " + REPORT_RESOURCE
                            + " tidak ditemukan di dalam aplikasi.");
                }
                cachedReport = JasperCompileManager.compileReport(input);
            } catch (java.io.IOException ex) {
                throw new JRException("Gagal membaca desain laporan: " + ex.getMessage(), ex);
            }
        }
        return cachedReport;
    }

    /**
     * Mengisi laporan dengan data dari database aplikasi.
     *
     * @return hasil laporan yang siap ditampilkan atau diekspor
     */
    public static JasperPrint fill() throws JRException {
        JasperReport report = getReport();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dicetak_oleh", Session.getNama());
        parameters.put("nama_database", Database.getDatabaseName());
        parameters.put("tanggal_cetak", new Date());

        try (Connection connection = Database.getConnection()) {
            return JasperFillManager.fillReport(report, parameters, connection);
        } catch (java.sql.SQLException ex) {
            throw new JRException("Gagal mengambil data laporan dari database.\n\n"
                    + "Periksa koneksi MySQL melalui menu Pengaturan Database.\n\n"
                    + ex.getMessage(), ex);
        }
    }

    /** Mengekspor laporan ke berkas PDF. */
    public static void exportPdf(File target) throws JRException {
        net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(fill(), target.getAbsolutePath());
    }

    /** Mengekspor laporan ke berkas Excel (.xlsx). */
    public static void exportXlsx(File target) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(fill()));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(target));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
    }

    /** Mengekspor laporan ke berkas HTML. */
    public static void exportHtml(File target) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(fill()));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(target));
        exporter.exportReport();
    }
}
