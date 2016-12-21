package com.company;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;

/**
 * Created by keithmartin on 10/5/16.
 */
public class CanvasView {

    private static final String SHOW = "Show";
    private static final String HIDE = "Hide";
    private JFrame frame = new JFrame("Canvas");
    private JSlider verticalSlider = new JSlider(JSlider.VERTICAL);
    private JSlider horizontalSlider = new JSlider(JSlider.HORIZONTAL);
    private JPanel canvas;
    private JComboBox colorComboBox = new JComboBox();
    private JButton togglePaintCircleButton = new JButton();
    private Color selectedColor;


    public CanvasView() {
        createCanvasView();
    }

    private void createCanvasView() {
        initCanvas();
        customizeSliders();
        customizeColorComboBox();
        customizePaintCircleButton();
        new CanvasViewController(this);
        frame.setLayout(new MigLayout());
        frame.add(verticalSlider, "cell 0 0");
        frame.add(canvas, "cell 1 0");
        frame.add(horizontalSlider, "cell 1 1");
        frame.add(colorComboBox, "cell 1 2");
        frame.add(togglePaintCircleButton, "cell 1 2");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initCanvas() {
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final int y = verticalSlider.getValue();
                final int x = horizontalSlider.getValue();
                switch (togglePaintCircleButton.getText()) {
                    case SHOW:
                        g.setColor(Color.white);
                        g.fillOval(x - 25, y - 25, 50, 50);
                        g.setColor(Color.black);
                        g.fillOval(x - 3, y - 3, 6, 6);
                        break;
                    case HIDE:
                        g.setColor(selectedColor);
                        g.fillOval(x - 25, y - 25, 50, 50);
                        break;
                }
            }
        };
        canvas.setBackground(Color.white);
        canvas.setPreferredSize(new Dimension(400, 400));
    }

    private void customizeSliders() {
        verticalSlider.setPreferredSize(new Dimension(100, 400));
        verticalSlider.setInverted(true);
        horizontalSlider.setPreferredSize(new Dimension(400, 100));

    }

    private void customizeColorComboBox() {
        colorComboBox.setPreferredSize(new Dimension(50, 50));
    }

    private void customizePaintCircleButton() {
        togglePaintCircleButton.setText("Show");
        togglePaintCircleButton.setPreferredSize(new Dimension(50, 50));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CanvasView();
            }
        });
    }

    public JComboBox getColorComboBox() {
        return colorComboBox;
    }

    public JButton getTogglePaintCircleButton() {
        return togglePaintCircleButton;
    }

    public JSlider getVerticalSlider() {
        return verticalSlider;
    }

    public JSlider getHorizontalSlider() {
        return horizontalSlider;
    }

    public JPanel getCanvas() {
        return canvas;
    }

    public void setSelectedColor(Color color) {
        selectedColor = color;
    }
}
