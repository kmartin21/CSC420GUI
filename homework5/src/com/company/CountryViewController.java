package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryViewController {
    private ArrayList<String> countries = new ArrayList<>();
    private CountryView countryView;
    private SplashScreenView splashScreenView;
    private Interface interfaceView;
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    public CountryViewController(Interface interfaceView) {
        this.interfaceView = interfaceView;
        this.countryView = this.interfaceView.getCountryView();
        this.splashScreenView = this.interfaceView.getSplashScreenView();
        loadImages();
        setSliders();
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
        setCountryList();
        setCanvasTransferHandler();
        interfaceView.removeSplashScreenView(splashScreenView);
        interfaceView.addCountryView(countryView);
    }

    private void setCountryListComboBox() {
        countryView.getCountryList().setModel(listModel);
        for (String countryName : countries) {
            listModel.addElement(countryName);
        }
    }

    private void setSliders() {
        countryView.getCanvas().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                countryView.getLeftVerticalSlider().setMaximum(countryView.getCanvas().getHeight());
                countryView.getRightVerticalSlider().setMaximum(countryView.getCanvas().getHeight());
                countryView.getTopHorizontalSlider().setMaximum(countryView.getCanvas().getWidth());
                countryView.getBottomHorizontalSlider().setMaximum(countryView.getCanvas().getWidth());
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        countryView.getLeftVerticalSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                countryView.getCanvas().repaint();
            }
        });
        countryView.getTopHorizontalSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                countryView.getCanvas().repaint();
            }
        });
    }

    private void setCountryList() {
        countryView.getCountryList().setDragEnabled(true);
        countryView.getCountryList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        countryView.getCountryList().setTransferHandler(new TransferHandler(){
            @Override
            public int getSourceActions(JComponent c) {
                return DnDConstants.ACTION_MOVE;
            }

            @Override
            protected Transferable createTransferable(JComponent component) {
                TransferableCountry transferableCountry = null;
                if (component instanceof JList) {
                    JList list = (JList) component;
                    Object selectedCountry = list.getSelectedValue();
                    Country country = new Country(selectedCountry + ".png");
                    transferableCountry = new TransferableCountry(country);
                }
                return transferableCountry;
            }
        });
    }

    private void setCanvasTransferHandler() {
        countryView.getCanvas().setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(TransferableCountry.getCountryDataFlavor());
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (canImport(support)) {
                    Transferable transferable = support.getTransferable();
                    Object country = null;
                    try {
                        country = transferable.getTransferData(TransferableCountry.getCountryDataFlavor());
                    } catch (UnsupportedFlavorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (country instanceof Country) {
                        setFlag((Country) country);
                         return true;
                    }
                }
                return false;
            }
        });
    }

    private void setFlag(Country country) {
        BufferedImage flagImage = country.getImage();
        Image scaledFlagImage = flagImage.getScaledInstance(50, 25,  Image.SCALE_SMOOTH);
        countryView.setFlagImage(scaledFlagImage);
        countryView.getCanvas().revalidate();
        countryView.getCanvas().repaint();
    }
}
