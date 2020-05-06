package Borzov.view;

import Borzov.DoubleToInt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class ViewLegend extends JPanel {
    private DoubleToInt colorFromValue;
    private double max;
    private int k;
    private double min;
    private BufferedImage image;
    private boolean isColorMap = false;
    private double legend=0;
    public ViewLegend() {
    }

    ViewLegend(int k, int[][] rgb, int min, int map, DoubleToInt colorFromValue) {
        setLayout(null);
        this.k = k;
        //this.rgb = rgb;
        this.min = min;
        this.max = max;
        colorFromValue = percent -> {
            int number = (int) Math.floor(percent * k);
            int[] mrgb0 = rgb[number];
            int[] mrgb1 = rgb[number + 1];
            double rStep = mrgb1[0] - mrgb0[0];
            double gStep = mrgb1[1] - mrgb0[1];
            double bStep = mrgb1[2] - mrgb0[2];
            double partOfPart = percent * k - number;
            int r = (int) (mrgb0[0] + rStep * partOfPart);
            int g = (int) (mrgb0[1] + gStep * partOfPart);
            int b = (int) (mrgb0[2] + bStep * partOfPart);
            return getColor(r, g, b);
        };
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

            }
        });
    }

    public void setIsolines(int k, int[][] rgb) {
        this.k = k;
    }

    public void setMinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        super.paintComponent(g);
        if (colorFromValue == null) {
            return;
        }
        int indent = (int) Math.ceil((double) getHeight() / 3);
        int startX = indent;
        int startY = indent / 2;
        int endX = getWidth() - startX;
        int endY = getHeight() - startY;
        int width = endX - startX;
        int height = endY - startY;
        double xStep = (double) width / (k + 1);
        double delta = max - min;
        if (delta == 0) {
            return;
        }

        for (int x = startX; x < endX; x++) {
            int color = colorFromValue.apply((double) (x - startX) / width);
            for (int y = startY; y < endY; y++) {
                image.setRGB(x, y, color);
            }
        }
        int numbers = getWidth() / (k + 1) / 10;
        int textShift;
        if (isColorMap) {
            textShift = (int) (xStep / 3);
        } else {
            textShift = 0;
        }
        for (int x = 0; x < k + 1; x++) {
            int xpos = (int) (startX + x * xStep);
            double value = x * delta / k + min;
            for (int y = startY; y < endY; y++) {
                image.setRGB(xpos, y, Color.BLACK.getRGB());
                g.setColor(Color.BLACK);
            }
        }
        if (isColorMap) {
            for (int x = 0; x < k + 1; x++) {
                int xpos = (int) (startX + x * xStep);
                double value = x * delta / k + min;
                String formatted = String.format("%." + numbers + "f", value);
                g.drawString(formatted, (int) (xpos - startX * 0.9 + textShift), (int) (startY * 0.9));
            }
        } else {
            for (int x = 0; x < k + 2; x++) {
                int xpos = (int) (startX + x * xStep);
                double value = x * delta / (k + 1) + min;
                String formatted = String.format("%." + numbers + "f", value);
                g.drawString(formatted, xpos - startX + textShift, (int) (startY * 0.9));
            }
        }
        g.setColor(Color.BLACK);
        String formatted = String.format("%." + numbers + "f", legend);
        g.drawString(formatted, startX + (endX - startX)/2, getHeight() - startY/3);
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.BLACK);
        g.drawRect(startX, startY, width, height);
    }

    int getColor(int r, int g, int b) {
        int color = b | (g << 8) | (r << 16) | (255 << 24);
        return color;
    }

    public void setColorGetter(DoubleToInt getterColorFromValue, boolean isColorMap) {
        this.isColorMap = isColorMap;
        this.colorFromValue = getterColorFromValue;
        repaint();
    }

    public void setLegend(double legend) {
        this.legend = legend;
        repaint();
    }
}
