package com.mycompany.mskproduction.ui.dialog;

import com.mycompany.mskproduction.ui.Ui;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Form dua kolom (label dan input) untuk dialog input data.
 */
public class FormPanel extends JPanel {

    private int row;

    public FormPanel() {
        super(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(4, 0, 0, 0));
    }

    public void addRow(String label, JComponent field) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(6, 0, 6, 14);
        JLabel text = new JLabel(label);
        text.setFont(Ui.FONT);
        text.setForeground(Ui.TEXT);
        add(text, labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.weightx = 1;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.insets = new Insets(6, 0, 6, 0);
        add(field, fieldConstraints);
        row++;
    }

    public void addHint(String text) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(2, 0, 6, 0);
        JLabel hint = new JLabel(text);
        hint.setFont(Ui.FONT_SMALL);
        hint.setForeground(Ui.MUTED);
        add(hint, constraints);
        row++;
    }

    public void addFull(JComponent component) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(6, 0, 6, 0);
        add(component, constraints);
        row++;
    }

    public void addSpace() {
        row++;
    }
}
