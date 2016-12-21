package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keithmartin on 9/25/16.
 */
public class CountryView {

    private JFrame frame = new JFrame("Countries");
    private JPanel countryListPanel = new JPanel();
    private JComboBox countryListComboBox = new JComboBox();
    private JPanel flagPanel = new JPanel();
    private JLabel flagLabel = new JLabel();


    public CountryView() {
        new CountryViewController(countryListComboBox, flagLabel);
        countryListPanel.add(countryListComboBox);
        flagPanel.add(flagLabel);
        frame.add(countryListPanel, BorderLayout.WEST);
        frame.add(flagPanel, BorderLayout.EAST);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CountryView();
            }
        });
    }
}
