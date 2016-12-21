package com.company;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryView extends JPanel {

    private JList countryList = new JList();
    private JPanel flagPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(countryList);
    private JLabel flagLabel = new JLabel();
    private JTextField textField = new JTextField(20);

    public CountryView() {
        countryList.setDragEnabled(true);
        countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setLayout(new MigLayout());
        flagPanel.add(flagLabel, BorderLayout.NORTH);
        this.add(scrollPane, "top, wrap, grow");
        this.add(textField);
        this.add(flagPanel, "cell 1 0");
        this.setSize(500, 400);
    }

    public JList getCountryList() { return countryList; }

    public JTextField getTextField() { return textField; }

    public JLabel getFlagLabel() {
        return flagLabel;
    }
}
