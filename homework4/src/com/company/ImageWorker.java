package com.company;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by keithmartin on 10/27/16.
 */
public class ImageWorker extends SwingWorker<Void, Void> {

    private HashMap<String, Country> countries = new HashMap<String, Country>();

    @Override
    protected Void doInBackground() throws Exception {
        final File folder = new File("FlagImages");
        setProgress(0);
        int fileCount = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (!(fileEntry.getName().equals(".DS_Store")) && !(fileEntry.getName().equals("BlankFlag.png"))) {
                String countryName = fileEntry.getName().replace(".png", "");
                countries.put(countryName, new Country(fileEntry.getName()));
                fileCount++;
                setProgress((int) ((fileCount / (double) folder.listFiles().length) * 100));
            }
        }
        return null;
    }
    public HashMap<String, Country> getCountriesList() {
        return countries;
    }
}
