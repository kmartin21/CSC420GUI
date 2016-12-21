package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by keithmartin on 10/27/16.
 */
public class Country {
    private String name;
    private String imageFilePath;
    private BufferedImage image;

    public Country(String filePath) {
        this.name = filePath;
        this.name = this.name.replace(".png", "");
        this.imageFilePath = "FlagImages/" + filePath;
        this.image = createImage(imageFilePath);
    }

    private BufferedImage createImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }
}
