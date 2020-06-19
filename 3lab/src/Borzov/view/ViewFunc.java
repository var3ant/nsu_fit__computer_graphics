package Borzov.view;

import Borzov.ContourLineCreator;
import Borzov.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ViewFunc extends JPanel {
    /**
     * Constructs object
     */
    int n = 250;
    int m = 250;
    int k;
    int[][] rgb;
    double a;
    double b;
    double c;
    double d;
    double[][] grid;
    double min;
    double max;
    boolean isShowGrid = false;
    boolean isIsoline = false;
    boolean isDinamicIsoline = false;
    int isolineX;
    int isolineY;
    BufferedImage image;
    BufferedImage dinamicIsoline;
    ContourLineCreator contourLineCreator;
    ViewLegend legend;

    public ViewFunc(int n, int m, ViewLegend legend) {
        contourLineCreator = new ContourLineCreator(n, m);
        addLegend(legend);
        setNM(n, m);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                image = null;
                repaint();
            }
        });
        ViewFunc t = this;
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //JOptionPane.showMessageDialog(t, "x: " + e.getX() + " y: " + e.getY(), "About Init", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isDinamicIsoline = true;
                isolineX = e.getX();
                isolineY = e.getY();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDinamicIsoline = false;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                isolineX = e.getX();
                isolineY = e.getY();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                legend.setLegend(calcLegend(e.getX(), e.getY()));
            }
        });
    }

    private double calcLegend(int x, int y) {
        double xdelta = b - a;
        double ydelta = c - d;
        double xstep = xdelta / getWidth();
        double ystep = ydelta / getHeight();
        return Function.f(a + x * xstep, d + y * ystep);
    }

    int[] getGridPos(int x, int y) {
        double xQuantum = (double) (getWidth() - 1) / (grid.length - 1);
        double yQuantum = (double) (getHeight() - 1) / (grid[0].length - 1);
        int[] pos = new int[2];
        pos[0] = (int) (x / xQuantum);
        pos[1] = (int) (y / yQuantum);
        return pos;
    }

    private void resizeImage() {
        image = null;
        double[] minmax = new double[2];
        calcGrid(grid, minmax);
        min = minmax[0];
        max = minmax[1];
    }

    /**
     * Performs actual component drawing
     *
     * @param g Graphics object to draw component to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double delta = max - min;
        if (image == null) {
            image = contourLineCreator.createImage(grid, min, max, delta, getWidth(), getHeight(), isShowGrid);
        }
        g.drawImage(image, 0, 0, null);
        if(isDinamicIsoline) {
            dinamicIsoline = contourLineCreator.createIsoline(grid, min, max, delta, getWidth(), getHeight(), isolineX,isolineY);
            g.drawImage(dinamicIsoline, 0, 0, null);
        }
    }

    void calcGrid(double[][] grid, double[] minmax) {
        minmax[0] = Integer.MAX_VALUE;
        minmax[1] = Integer.MIN_VALUE;
        double xdelta = b - a;
        double ydelta = c - d;
        double xstep = xdelta / grid.length;
        double ystep = ydelta / grid[0].length;
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                double realX = a + x * xstep;
                double realY = d + y * ystep;
                double f = Function.f(realX, realY);
                grid[x][y] = f;
                if (minmax[0] > f) {
                    minmax[0] = f;
                }
                if (minmax[1] < f) {
                    minmax[1] = f;
                }
            }
        }
        min = minmax[0];
        max = minmax[1];
    }

    public boolean toggleGridMode() {
        isShowGrid = !isShowGrid;
        image = null;
        repaint();
        return isShowGrid;
    }

    public void setABCD(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        contourLineCreator.setABCD(a, b, c, d);
        resizeImage();
    }

    public void setIsolines(int k, int[][] rgb) {
        if (rgb.length != k + 2) {
            throw new IllegalArgumentException();
        }
        this.rgb = rgb;
        this.k = k;
        contourLineCreator.setIsolines(k, rgb);
    }

    public void setNM(int n, int m) {
        this.n = n;
        this.m = m;
        grid = new double[n][m];
        contourLineCreator.setNM(n, m);
        resizeImage();
    }

    public boolean toggleIsolineMode() {
        isIsoline = !isIsoline;
        image = null;
        repaint();
        return isIsoline;
    }

    public boolean toggleColorMap() {
        boolean state = contourLineCreator.toggleColorMap();
        setIsolines(k, rgb);
        image = null;
        repaint();
        return state;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public void addLegend(ViewLegend legend) {
        contourLineCreator.addLegend(legend);
    }

    public void setColorMap(boolean isColorMap) {
        contourLineCreator.setColorMap(isColorMap);
        setIsolines(k, rgb);
        image = null;
        repaint();
    }

    public double[] getABCD() {
        return new double[]{a,b,c,d};
    }

    public int[] getNM() {
        return new int[]{n,m};
    }

    public int getK() {
        return k;
    }

    public int[][] getRGBs() {
        return rgb;
    }
}