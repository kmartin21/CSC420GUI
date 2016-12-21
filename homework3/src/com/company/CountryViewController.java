package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryViewController {
    private ArrayList<Country> countries;
    private CountryView countryView;
    private SplashScreenView splashScreenView;
    private Interface interfaceView;

    public CountryViewController(Interface interfaceView) {
        this.interfaceView = interfaceView;
        this.countryView = this.interfaceView.getCountryView();
        this.splashScreenView = this.interfaceView.getSplashScreenView();
        loadImages();
    }

    private void loadImages() {
        ImageWorker imageWorker = new ImageWorker();
        imageWorker.execute();
        updateSplashScreen(imageWorker);
    }

    private void updateSplashScreen(ImageWorker imageWorker) {
        SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                while (!imageWorker.isDone()) {
                    try {
                        int progress = imageWorker.getProgress();
                        publish(progress);
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                Border border = BorderFactory.createTitledBorder("Finished loading flag images");
                splashScreenView.getProgressBar().setBorder(border);
                countries = imageWorker.getCountriesList();
                setupCountryView();
                interfaceView.removeSplashScreenView(splashScreenView);
                interfaceView.addCountryView(countryView);
            }

            @Override
            protected void process(List<Integer> chunks) {
                super.process(chunks);
                int progress = chunks.get(chunks.size()-1);
                splashScreenView.getProgressBar().setValue(progress);
            }
        };
        worker.execute();
        splashScreenView.setSplashScreenImage("GlobeImage/Globe.png");
    }

    private void setupCountryView() {
        setCountryListComboBox();
        Country initialCountry = countries.get(countryView.getCountryListComboBox().getSelectedIndex());
        countryView.getFlagLabel().setIcon(initialCountry.getImage());
    }

    private void setCountryListComboBox() {
        for (Country country : countries) {
            countryView.getCountryListComboBox().addItem(country.getName());
        }
        countryView.getCountryListComboBox().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Country selectedCountry = countries.get(countryView.getCountryListComboBox().getSelectedIndex());
                countryView.getFlagLabel().setIcon(selectedCountry.getImage());
            }
        });
    }
}
