package com.company;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;

/**
 * Created by keithmartin on 10/5/16.
 */
public class CanvasViewController {

    private static final String SHOW = "Show";
    private static final String HIDE = "Hide";
    private HashMap<String, Color> colors = new HashMap<>(5);
    private CanvasView canvasView;

    public CanvasViewController(CanvasView canvasView) {
        this.canvasView = canvasView;
        loadColorsHashMap();
        setComboBox();
        setSliders();
        setPaintCircleButton();
    }

    private void loadColorsHashMap() {
        colors.put("Blue", Color.blue);
        colors.put("Red", Color.red);
        colors.put("Green", Color.green);
        colors.put("Yellow", Color.yellow);
        colors.put("Pink", Color.pink);
    }

    private void setComboBox() {
        for (String color : colors.keySet()) {
            canvasView.getColorComboBox().addItem(color);
        }
        canvasView.setSelectedColor(colors.get(canvasView.getColorComboBox().getSelectedItem()));
        canvasView.getColorComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasView.setSelectedColor(colors.get(canvasView.getColorComboBox().getSelectedItem()));
            }
        });
    }

    private void setPaintCircleButton() {
        canvasView.getTogglePaintCircleButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (canvasView.getTogglePaintCircleButton().getText()) {
                    case SHOW:
                        canvasView.getCanvas().repaint();
                        canvasView.getTogglePaintCircleButton().setText("Hide");
                        canvasView.getHorizontalSlider().setEnabled(false);
                        canvasView.getVerticalSlider().setEnabled(false);
                        canvasView.getColorComboBox().setEnabled(false);
                        break;
                    case HIDE:
                        canvasView.getCanvas().repaint();
                        canvasView.getTogglePaintCircleButton().setText("Show");
                        canvasView.getHorizontalSlider().setEnabled(true);
                        canvasView.getVerticalSlider().setEnabled(true);
                        canvasView.getColorComboBox().setEnabled(true);
                        break;
                }
            }
        });
    }

    private void setSliders() {
        canvasView.getCanvas().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvasView.getVerticalSlider().setMaximum(canvasView.getCanvas().getHeight());
                canvasView.getHorizontalSlider().setMaximum(canvasView.getCanvas().getWidth());
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        canvasView.getVerticalSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                canvasView.getCanvas().repaint();
            }
        });
        canvasView.getHorizontalSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                canvasView.getCanvas().repaint();
            }
        });
    }
}
