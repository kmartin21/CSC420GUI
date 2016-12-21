package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryView extends JPanel {

    private JPanel countryListPanel = new JPanel();
    private JComboBox countryListComboBox = new JComboBox();
    private JPanel flagPanel = new JPanel();
    private JLabel flagLabel = new JLabel();

    public CountryView() {
        countryListPanel.add(countryListComboBox, BorderLayout.NORTH);
        flagPanel.add(flagLabel, BorderLayout.CENTER);
        this.add(countryListPanel);
        this.add(flagPanel);
        this.setSize(500, 400);
    }

    public JComboBox getCountryListComboBox() {
        return countryListComboBox;
    }

    public JLabel getFlagLabel() {
        return flagLabel;
    }
}
