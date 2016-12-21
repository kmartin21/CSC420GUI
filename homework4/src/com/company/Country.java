package com.company;

import javax.swing.*;

/**
 * Created by keithmartin on 10/27/16.
 */
public class Country {
    private String name;
    private String imageFilePath;
    private ImageIcon image;

    public Country(String filePath) {
        this.name = filePath;
        this.name = this.name.replace(".png", "");
        this.imageFilePath = "FlagImages/" + filePath;
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
