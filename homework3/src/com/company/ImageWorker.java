package com.company;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by keithmartin on 10/27/16.
 */
public class ImageWorker extends SwingWorker<Void, Void> {

    private ArrayList<Country> countries = new ArrayList<>();

    @Override
    protected Void doInBackground() throws Exception {
        final File folder = new File("FlagImages");
        setProgress(0);
        int fileCount = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (!(fileEntry.getName().equals(".DS_Store"))) {
                countries.add(new Country(fileEntry.getName()));
                fileCount++;
                setProgress((int) ((fileCount / (double) folder.listFiles().length) * 100));
            }
        }
        return null;
    }
    public ArrayList<Country> getCountriesList() {
        return countries;
    }
}
