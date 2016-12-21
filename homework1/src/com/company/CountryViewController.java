package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by keithmartin on 9/25/16.
 */
public class CountryViewController {

    private Country[] countries = new Country[5];
    private JComboBox countryListComboBox = new JComboBox();
    private JLabel flagLabel = new JLabel();

    public CountryViewController(JComboBox countryListComboBox, JLabel flagLabel) {
        this.countryListComboBox = countryListComboBox;
        this.flagLabel = flagLabel;
        loadCountriesArray();
        setCountryListComboBox();
        Country initialCountry = countries[countryListComboBox.getSelectedIndex()];
        flagLabel.setIcon(initialCountry.getImage());
    }

    private void loadCountriesArray() {
        countries[0] = new Country("France");
        countries[1] = new Country("Germany");
        countries[2] = new Country("Iceland");
        countries[3] = new Country("Italy");
        countries[4] = new Country("United Kingdom");
    }

    private void setCountryListComboBox() {
        for (Country country : countries) {
            countryListComboBox.addItem(country.getName());
        }
        countryListComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Country selectedCountry = countries[countryListComboBox.getSelectedIndex()];
                flagLabel.setIcon(selectedCountry.getImage());
            }
        });
    }
}
