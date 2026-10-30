package com.mycompany.mskproduction.ui.dialog;

import com.mycompany.mskproduction.ui.Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Komponen dasar dialog: judul, isi, dan tombol aksi.
 */
public final class DialogSupport {

    private DialogSupport() {
    }

    public static JDialog create(Window owner, String title) {
        JDialog dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setResizable(false);
        return dialog;
    }

    public static JPanel content(Component body) {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(22, 26, 18, 26));
        panel.add(body, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel header(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Ui.FONT.deriveFont(java.awt.Font.BOLD, 20));
        titleLabel.setForeground(Ui.TEXT);
        panel.add(titleLabel, BorderLayout.NORTH);
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(Ui.FONT_SMALL);
        subtitleLabel.setForeground(Ui.MUTED);
        panel.add(subtitleLabel, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel footer(Component... components) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(6, 0, 0, 0));
        for (Component component : components) {
            panel.add(component);
        }
        return panel;
    }
}
