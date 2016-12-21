package com.company;

import javax.swing.*;

/**
 * Created by keithmartin on 9/25/16.
 */
public class Country {

    private String name;
    private String imageFilePath;
    private ImageIcon image;

    public Country(String name) {
        this.name = name;
        this.imageFilePath = "FlagImages/" + this.name + ".png";
        this.image = createImage(imageFilePath);
    }

    private ImageIcon createImage(String path) {
        return new ImageIcon(path, null);
    }

    public String getName() {
        return name;
    }

    public ImageIcon getImage() {
        return image;
    }
}
