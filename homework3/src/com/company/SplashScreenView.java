package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

/**
 * Created by keithmartin on 10/27/16.
 */
public class SplashScreenView extends JPanel{

    private JProgressBar progressBar;
    private JLabel splashScreenImage;

    public SplashScreenView() {
        setupProgressBar();
        this.splashScreenImage = new JLabel();
        setupLayout();
    }

    private void setupProgressBar() {
        this.progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Loading flag images...");
        progressBar.setBorder(border);
    }

    private void setupLayout() {
        this.setLayout(new MigLayout("fillx"));
        this.setSize(400, 300);
        CC componentConstraints = new CC();
        componentConstraints.alignX("center").spanX();
        this.add(progressBar, componentConstraints);
        this.add(splashScreenImage, componentConstraints);
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setSplashScreenImage(String path) {
        splashScreenImage.setIcon(new ImageIcon(path, null));
    }
}
