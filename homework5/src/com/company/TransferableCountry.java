package com.company;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by keithmartin on 12/2/16.
 */
public class TransferableCountry implements Transferable {

    private static final DataFlavor countryDataFlavor = new DataFlavor(Country.class, "Country");
    private Country country;

    public TransferableCountry(Country country) {
        this.country = country;
    }

    public static DataFlavor getCountryDataFlavor() {
        return countryDataFlavor;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{countryDataFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(countryDataFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return country;
    }
}
