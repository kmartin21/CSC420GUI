package com.company;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by keithmartin on 10/27/16.
 */
public class CountryView extends JPanel {

    private JList countryList = new JList();
    private JScrollPane scrollPane = new JScrollPane(countryList);
    private JLabel canvas = new JLabel();
    private DefaultBoundedRangeModel verticalSlidermodel = new DefaultBoundedRangeModel();
    private DefaultBoundedRangeModel hortizontalSlidermodel = new DefaultBoundedRangeModel();
    private JSlider leftVerticalSlider = new JSlider(verticalSlidermodel);
    private JSlider rightVerticalSlider = new JSlider(verticalSlidermodel);
    private JSlider bottomHorizontalSlider = new JSlider(hortizontalSlidermodel);
    private JSlider topHorizontalSlider = new JSlider(hortizontalSlidermodel);
    private Image canvasFlagImage;


    public CountryView() {
        setSliders();
        initCanvas();
        this.setLayout(new MigLayout());
        this.add(scrollPane, "cell 0 0, span 1 4, grow");
        this.add(leftVerticalSlider, "cell 1 1, grow");
        this.add(topHorizontalSlider, "cell 2 0, grow");
        this.add(canvas, "cell 2 1");
        this.add(bottomHorizontalSlider, "cell 2 2, grow");
        this.add(rightVerticalSlider, "cell 3 1, grow");
        this.setSize(500, 400);
    }

    private void initCanvas() {
        canvas = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final int y = leftVerticalSlider.getValue();
                final int x = topHorizontalSlider.getValue();
                if (canvasFlagImage != null) {
                    g.drawImage(canvasFlagImage, topHorizontalSlider.getValue() - 25, leftVerticalSlider.getValue() - 12, null);
                } else {
                    g.drawLine(x, y - 5, x, y + 5);
                    g.drawLine(x - 5, y, x + 5, y);
                }
            }
        };
        canvas.setOpaque(true);
        canvas.setBackground(Color.white);
        canvas.setPreferredSize(new Dimension(400, 400));
    }

    public void setFlagImage(Image flagImage) {
        canvasFlagImage = flagImage;
    }

    private void setSliders() {
        leftVerticalSlider.setInverted(true);
        leftVerticalSlider.setOrientation(JSlider.VERTICAL);
        rightVerticalSlider.setInverted(true);
        rightVerticalSlider.setOrientation(JSlider.VERTICAL);
        bottomHorizontalSlider.setOrientation(JSlider.HORIZONTAL);
        topHorizontalSlider.setOrientation(JSlider.HORIZONTAL);
    }

    public JList getCountryList() { return countryList; }
    public JSlider getLeftVerticalSlider() { return leftVerticalSlider; }
    public JSlider getRightVerticalSlider() { return rightVerticalSlider; }
    public JSlider getTopHorizontalSlider() { return topHorizontalSlider; }
    public JSlider getBottomHorizontalSlider() { return bottomHorizontalSlider; }
    public JLabel getCanvas() { return canvas; }
}
