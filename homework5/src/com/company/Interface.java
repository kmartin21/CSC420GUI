package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keithmartin on 10/28/16.
 */
public class Interface {

    private JFrame frame;
    private SplashScreenView splashScreenView;
    private CountryView countryView;

    public Interface() {
        this.frame = new JFrame("Countries");
        this.frame.setResizable(false);
        frame.setSize(400, 300);
        this.splashScreenView = new SplashScreenView();
        this.countryView = new CountryView();
        frame.add(splashScreenView, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        new CountryViewController(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Interface();
            }
        });
    }

    public SplashScreenView getSplashScreenView() {
        return splashScreenView;
    }

    public CountryView getCountryView() {
        return countryView;
    }

    public void addCountryView(CountryView view) {
        frame.add(view);
        frame.setSize(800, 400);
        frame.validate();
        frame.repaint();
    }

    public void removeSplashScreenView(SplashScreenView view) {
        frame.getContentPane().remove(view);
        frame.validate();
        frame.repaint();
    }
}