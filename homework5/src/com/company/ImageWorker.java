package com.company;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by keithmartin on 10/27/16.
 */
public class ImageWorker extends SwingWorker<Void, Void> {

    private ArrayList<String> countries = new ArrayList<>();

    @Override
    protected Void doInBackground() throws Exception {
        final File folder = new File("FlagImages");
        setProgress(0);
        int fileCount = 0;
        for (final File fileEntry : folder.listFiles()) {
            if (!(fileEntry.getName().equals(".DS_Store")) && !(fileEntry.getName().equals("BlankFlag.png"))) {
                String countryName = fileEntry.getName().replace(".png", "");
                countries.add(countryName);
                fileCount++;
                setProgress((int) ((fileCount / (double) folder.listFiles().length) * 100));
                Thread.sleep(10);
            }
        }
        return null;
    }
    public ArrayList<String> getCountriesList() {
        return countries;
    }
}
