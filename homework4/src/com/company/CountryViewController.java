package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.HashMap;
import java.util.List;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryViewController {
    private HashMap<String, Country> countries = new HashMap<>();
    private CountryView countryView;
    private SplashScreenView splashScreenView;
    private Interface interfaceView;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

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
        setupTextField();
    }

    private void setCountryListComboBox() {
        countryView.getCountryList().setModel(listModel);
        for (String countryName : countries.keySet()) {
            listModel.addElement(countryName);
        }
        countryView.getFlagLabel().setIcon(new ImageIcon("FlagImages/BlankFlag.png"));
    }

    private void setupTextField() {
        countryView.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFlag(countryView.getTextField().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFlag(countryView.getTextField().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void updateFlag(String input) {
        if (countries.containsKey(input)) {
            Country country = countries.get(countryView.getTextField().getText());
            countryView.getFlagLabel().setIcon(country.getImage());
        } else {
            countryView.getFlagLabel().setIcon(new ImageIcon("FlagImages/BlankFlag.png"));
        }
    }
}
