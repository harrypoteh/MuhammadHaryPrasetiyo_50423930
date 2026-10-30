package com.mycompany.mskproduction;

import com.mycompany.mskproduction.db.Database;
import com.mycompany.mskproduction.ui.HomeFrame;
import com.mycompany.mskproduction.ui.Ui;
import javax.swing.SwingUtilities;

/**
 * Entry point aplikasi MSK Production.
 *
 * <p>Aplikasi desktop Java Swing untuk pengelolaan produksi roti:
 * bahan baku, produk jadi, resep, pesanan, proses produksi,
 * hasil produksi, dan laporan.</p>
 */
public class MSKProduction {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ui.applyLookAndFeel();
            try {
                Database.bootstrap();
            } catch (Exception ex) {
                System.err.println("Database belum siap: " + ex.getMessage());
            }
            new HomeFrame().setVisible(true);
        });
    }
}
