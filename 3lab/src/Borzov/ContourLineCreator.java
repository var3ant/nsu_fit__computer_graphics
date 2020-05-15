package Borzov;

import Borzov.view.ViewLegend;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class ContourLineCreator {
    boolean isColorMap = false;
    int n;
    int m;
    int quantumZCount = 7;
    double a, b, c, d;
    DoubleToInt colorFromValue;
    DoubleToInt colorPartFromValue;
    private double[][] grid;
    private int k;
    private int[][] rgb;
    private ViewLegend legend;
    private double delta;
    private double min;

    public ContourLineCreator(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public void setColorMap(boolean isColorMap) {
        this.isColorMap = isColorMap;
    }

    public BufferedImage createImage(double[][] grid, double min, double max, double delta, int width, int height, boolean isShowGrid) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        double xQuantum = (double) (width - 1) / (grid.length - 1);
        double yQuantum = (double) (height - 1) / (grid[0].length - 1);
        if (isColorMap) {
            resampleSquares2(grid, image, min, delta, a, b, c, d, colorPartFromValue);
        } else {
            resample(grid, image, min, delta, colorFromValue);
        }
        if (isShowGrid) {
            for (int y = 0; y < height; y += 1) {
                for (int x = 0; x < grid.length; x += 1) {
                    double realX = x * xQuantum;
                    int pixel = image.getRGB((int) realX, y);
                    int r = (pixel & 0x00FF0000) >> 16;
                    int g = ((pixel & 0x0000FF00) >> 8);
                    int b = (pixel & 0x000000FF);
                    int newColor = getColor(255 - r, 255 - g, 255 - b);
                    image.setRGB((int) realX, y, newColor);
                }
            }
            for (int y = 0; y < grid[0].length; y += 1) {
                for (int x = 0; x < width; x += 1) {
                    double realY = y * yQuantum;
                    int pixel = image.getRGB(x, (int) realY);
                    int r = (pixel & 0x00FF0000) >> 16;
                    int g = ((pixel & 0x0000FF00) >> 8);
                    int b = (pixel & 0x000000FF);
                    int newColor = getColor(255 - r, 255 - g, 255 - b);
                    image.setRGB(x, (int) realY, newColor);
                }
            }
        }
        return image;
    }

    int getZnumberFromPercent(double percent) {
        return (int) Math.floor(percent * (k + 1));
    }

    int getZnumber(double percent) {
        if (percent < 0 || percent > 1) {
            throw new IllegalArgumentException();
        }
        return (int) Math.floor(percent * (k + 1));
    }

    int hsvToRgb(int h, double s, double v) {
        int r;
        int g;
        int b;
        double dr = 0;
        double dg = 0;
        double db = 0;
        int hi = (h / 60) % 6;
        double vMin = (100 - s) * v / 100;
        double a = (v - vMin) * (h % 60) / 60;
        double vInc = vMin + a;
        double vDec = v - a;
        switch (hi) {
            case 0:
                dr = v;
                dg = vInc;
                db = vMin;
                break;
            case 1:
                dr = vDec;
                dg = v;
                db = vMin;
                break;
            case 2:
                dr = vMin;
                dg = v;
                db = vInc;
                break;
            case 3:
                dr = vMin;
                dg = vDec;
                db = v;
                break;
            case 4:
                dr = vInc;
                dg = vMin;
                db = v;
                break;
            case 5:
                dr = v;
                dg = vMin;
                db = vDec;
                break;
        }
        r = (int) (dr * 255 / 100);
        g = (int) (dg * 255 / 100);
        b = (int) (db * 255 / 100);
        return b | (g << 8) | (r << 16) | (255 << 24);
    }

    int getColor(int r, int g, int b) {
        int color = b | (g << 8) | (r << 16) | (255 << 24);
        return color;
    }
    int getIntFromRGB(int r, int g, int b) {
        int color = b | (g << 8) | (r << 16) | (255 << 24);
        return color;
    }
    void resample(double[][] a, BufferedImage image, double min, double delta, DoubleToInt getColor) {
        double xQuantum = (double) (a.length - 1) / (image.getWidth() - 1);
        double yQuantum = (double) (a[0].length - 1) / (image.getHeight() - 1);
        for (int x = 0; x < image.getWidth(); x += 1) {
            for (int y = 0; y < image.getHeight(); y += 1) {
                int gx1 = (int) (x * xQuantum);
                int gx2 = (int) (x * xQuantum) + 1;
                int gy1 = (int) (y * yQuantum);
                int gy2 = (int) (y * yQuantum) + 1;
                int x1 = (int) (gx1 / xQuantum);
                int x2 = (int) (gx2 / xQuantum);
                int y1 = (int) (gy1 / yQuantum);
                int y2 = (int) (gy2 / yQuantum);
                if (gx2 > a.length - 1) {
                    continue;
                }
                if (gy2 > a[0].length - 1) {
                    continue;
                }
                double fraction1x = (double) (x2 - x) / (x2 - x1);
                double fraction2x = (double) (x - x1) / (x2 - x1);
                double fxy1 = fraction1x * a[gx1][gy1] + fraction2x * a[gx2][gy1];
                double fxy2 = fraction1x * a[gx1][gy2] + fraction2x * a[gx2][gy2];
                double fraction1y = (double) (y2 - y) / (y2 - y1);
                double fraction2y = (double) (y - y1) / (y2 - y1);
                double result = fraction1y * fxy1 + fraction2y * fxy2;
                double h = ((result - min) / delta);
                int color = getColor.apply(h);
                image.setRGB(x, y, color);
            }
        }
    }

    /*void resampleSquares(double[][] grid, BufferedImage image, double min, double delta, double a, double b, double c, double d, DoubleToInt getColor) {
        double xQuantum = (double) (image.getWidth() - 1) / (grid.length - 1);
        double yQuantum = (double) (image.getHeight() - 1) / (grid[0].length - 1);
        for (int gx1 = 0; gx1 < grid.length; gx1 += 1) {
            for (int gy1 = 0; gy1 < grid[0].length; gy1 += 1) {

                int gx2 = gx1 + 1;
                int gy2 = gy1 + 1;
                int x1 = (int) (gx1 * xQuantum);
                int y1 = (int) (gy1 * yQuantum);
                int x2 = (int) (gx2 * xQuantum);
                int y2 = (int) (gy2 * yQuantum);
                if (x1 > image.getWidth() - 1 || y1 > image.getHeight() - 1) {
                    throw new Error();
                }
                if (gx2 > grid.length - 1) {
                    gx2 = grid.length - 1;
                    continue;
                }
                if (gy2 > grid[0].length - 1) {
                    gy2 = grid[0].length - 1;
                    continue;
                }
                //delta = 2;

                int x1y1 = getZnumber((grid[gx1][gy1] - min) / delta);
                int x1y2 = getZnumber((grid[gx1][gy2] - min) / delta);
                int x2y1 = getZnumber((grid[gx2][gy1] - min) / delta);
                int x2y2 = getZnumber((grid[gx2][gy2] - min) / delta);
                //image.setRGB((int)(gx1*xQuantum),(int)(gy1*yQuantum),);

                int xdelta = (image.getWidth() - 1) / (grid.length - 1);
                int ydelta = (image.getHeight() - 1) / (grid[0].length - 1);
                //Stack<Point2D> points = new Stack<>();
                //Stack<> points = new Stack<Point2D>[k+1];
                ArrayList<Stack<Point2D>> points = new ArrayList<>();
                for (int i = 0; i < k + 2; i++) {
                    points.add(i, new Stack<>());
                }
                if (x1y1 != x2y1) {
                    idkX(y1, x1, x2, grid[gx1][gy1], grid[gx2][gy1], delta, min, points);
                }
                if (x1y1 != x1y2) { //1 5
                    idkY(x1, y1, y2, grid[gx1][gy1], grid[gx1][gy2], delta, min, points);
                }
                if (x1y2 != x2y2) {
                    idkX(y2, x1, x2, grid[gx2][gy1], grid[gx2][gy2], delta, min, points);
                }
                if (x2y1 != x2y2) {
                    idkY(x2, y1, y2, grid[gx2][gy1], grid[gx2][gy2], delta, min, points);
                }
                for (Stack<Point2D> p : points) {
                    if (p.size() == 2) {
                        Point2D p1 = p.pop();
                        Point2D p2 = p.pop();
                        Graphics g = image.getGraphics();
                        g.setColor(getColorFromRGB(rgb[k+1]));
                        g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
                    }
                    if (p.size() == 4) {
                        Point2D p1 = p.pop();
                        Point2D p2 = p.pop();
                        Point2D p3 = p.pop();
                        Point2D p4 = p.pop();
                        Graphics g = image.getGraphics();
                        g.setColor(new Color(getIsolineColor()));
                        int px1 = (int) p1.getX();
                        int py1 = (int) p1.getY();
                        int px2 = (int) p2.getX();
                        int py2 = (int) p2.getY();
                        int px3 = (int) p3.getX();
                        int py3 = (int) p3.getY();
                        int px4 = (int) p4.getX();
                        int py4 = (int) p4.getY();
                        int maxX = Math.max(Math.max(Math.max(px1, px2), px3), px4);
                        int minX = Math.min(Math.min(Math.min(px1, px2), px3), px4);
                        int maxY = Math.max(Math.max(Math.max(py1, py2), py3), py4);
                        int minY = Math.min(Math.min(Math.min(py1, py2), py3), py4);
                        int centerX = minX + (maxX - minX) / 2;
                        int centerY = minY + (maxY - minY) / 2;
                        int zCenter = getZFromCoord(centerX, centerY, min, delta, grid, xQuantum, yQuantum);
                        Point2D lu = new Point2D((int) ((int) (px1 / xQuantum) * xQuantum), (int) ((int) (py1 / xQuantum) * xQuantum));
                        if (zCenter == getZFromCoord((int) lu.getX(), (int) lu.getY(), min, delta, grid, xQuantum, yQuantum)) {
                            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p3.getX(), (int) p3.getY());
                        } else {
                            g.drawLine((int) p2.getX(), (int) p2.getY(), (int) p4.getX(), (int) p4.getY());
                        }
                    }
                }
            }
        }
    }
    */
    void idkX(int y, int x1, int x2, double v1, double v2, double delta, double min, ArrayList<Stack<Point>> points) {
        int xx;
        int z1 = getZnumber((v1 - min) / delta);
        //int z2=getZnumber((v2 - min) / delta);
        int xdelta = x2 - x1;
        for (xx = x1; xx <= x2; xx++) {
            double fract1 = (double) (x2 - xx) / (xdelta);
            double fract2 = (double) (xx - x1) / (xdelta);
            double v = v1 * fract1 + v2 * fract2;
            int z = getZnumber((v - min) / delta);
            if (z1 != z) {
                //int zToAdd = Math.min(z1, z);
                z1 = z;
                points.get(z).add(new Point(xx, y));
            }
        }
    }

    void idkY(int x, int y1, int y2, double v1, double v2, double delta, double min, ArrayList<Stack<Point>> points) {
        int yy;
        int z1 = getZnumber((v1 - min) / delta);
        //int z2=getZnumber((v1 - min) / delta);
        int ydelta = y2 - y1;
        for (yy = y1; yy <= y2; yy++) {
            double fract1 = (double) (y2 - yy) / (ydelta);
            double fract2 = (double) (yy - y1) / (ydelta);
            double v = v1 * fract1 + v2 * fract2;
            int z = getZnumber((v - min) / delta);
            if (z1 != z) {
                //int zToAdd = Math.min(z1, z);
                z1 = z;
                points.get(z).add(new Point(x, yy));
            }
        }
    }

    /*private Color getColorFromRGB(int[] rgb) {
        if(rgb.length!=3) {
            throw new IllegalArgumentException("illegal length of int[]. Expected 3");
        }
        return new Color(rgb[2] | (rgb[1] << 8) | (rgb[0] << 16) | (255 << 24));
    }*/
    public void setABCD(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void setIsolines(int k, int[][] rgb) {
        if (rgb.length != k + 2) {
            throw new IllegalArgumentException();
        }
        this.rgb = rgb;
        this.k = k;
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
        colorPartFromValue = percent -> {
            int number = getZnumberFromPercent(percent);
            if(number < 0) {
                throw  new IllegalArgumentException("number: " + number + " must be >=0");
            }
            int[] mrgb = rgb[number];
            int color = mrgb[2] | (mrgb[1] << 8) | (mrgb[0] << 16) | (255 << 24);
            return color;
        };
        updateLegend();
    }

    public void setNM(int n, int m) {
        this.n = n;
        this.m = m;
        grid = new double[n][m];
    }

    public boolean toggleColorMap() {
        isColorMap = !isColorMap;
        updateLegend();
        return isColorMap;
    }

    /*public DoubleToInt getGetterColorFromValue() {
        if (isColorMap) {
            return colorPartFromValue;
        }
        return colorFromValue;
    }

    private int getZFromCoord(int x, int y, double min, double delta, double[][] a, double xQuantum, double yQuantum) {
        //double xQuantum = (double) (a.length - 1) / (image.getWidth() - 1);
        //double yQuantum = (double) (a[0].length - 1) / (image.getHeight() - 1);
        int x1 = (int) (x / xQuantum * xQuantum);
        int y1 = (int) (y / yQuantum * yQuantum);
        int x2 = (int) ((x / xQuantum + 1) * xQuantum);
        int y2 = (int) ((y / yQuantum + 1) * yQuantum);
        int gx1 = (int) (x / xQuantum);
        int gy1 = (int) (y / yQuantum);
        int gx2 = (gx1 + 1);
        int gy2 = (gy1 + 1);
        double fraction1x = (double) (x2 - x) / (x2 - x1);
        double fraction2x = (double) (x - x1) / (x2 - x1);
        double fxy1 = fraction1x * a[gx1][gy1] + fraction2x * a[gx2][gy1];
        double fxy2 = fraction1x * a[gx1][gy2] + fraction2x * a[gx2][gy2];
        double fraction1y = (double) (y2 - y) / (y2 - y1);
        double fraction2y = (double) (y - y1) / (y2 - y1);
        double result = fraction1y * fxy1 + fraction2y * fxy2;
        double h = ((result - min) / delta);
        return getZnumber(h);
    }*/

    public void addLegend(ViewLegend legend) {
        this.legend = legend;
    }

    private void updateLegend() {
        if (isColorMap) {
            legend.setColorGetter(colorPartFromValue, isColorMap);
        } else {
            legend.setColorGetter(colorFromValue, isColorMap);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    int getZByF(double f) {
        return (int) (getProcentByF(f) * (quantumZCount));
    }

    int getZ(int gx, int gy) {
        return getZByF(grid[gx][gy]);
    }

    double getProcentByF(double f) {
        return (f - min) / delta;
    }

    boolean foo(int x1, int y1, int x2, int y2, int z) {
        int sign1 = 0;
        int sign2 = 0;
        if (getZ(x1, y1) >= z) {
            sign1 = 1;
        }
        if (getZ(x2, y2) >= z) {
            sign2 = 1;
        }
        return sign1 == sign2;
    }
    boolean between(double v1, double v2, double v) {
        return v1 < v && v < v2 || v2 < v && v < v1;
    }
    double linear(int x1, int x2, int x, double f1, double f2) {
        return f1 * (x2 - x) / (x2 - x1) + f2 * (x - x1) / (x2 - x1);
    }

    double bilinear(int x, int y, int x1, int x2, int y1, int y2, double f11, double f12, double f21, double f22) {
        double fraction1x = (double) (x2 - x) / (x2 - x1);
        double fraction2x = (double) (x - x1) / (x2 - x1);
        double fxy1 = fraction1x * f11 + fraction2x * f21;
        double fxy2 = fraction1x * f12 + fraction2x * f22;
        double fraction1y = (double) (y2 - y) / (y2 - y1);
        double fraction2y = (double) (y - y1) / (y2 - y1);
        double result = fraction1y * fxy1 + fraction2y * fxy2;
        return result;
    }
    private int getIsolineColor() {
        return getIntFromRGB(rgb[k+1][0],rgb[k+1][1],rgb[k+1][2]);
    }
    void resampleSquares2(double[][] grid, BufferedImage image, double min, double delta, double a, double b, double c, double d, DoubleToInt getColor) {
        double xQuantum = (double) (image.getWidth() - 1) / (grid.length - 1);
        double yQuantum = (double) (image.getHeight() - 1) / (grid[0].length - 1);
        this.grid = grid;
        this.delta = delta;
        this.min = min;
        List<IsoLine> isoLines = new ArrayList<>();
        for (int z = 0; z < quantumZCount; z += 1) {
            //��������� ��� z
            for (int gx1 = 0; gx1 < grid.length - 1; gx1 += 1) {
                for (int gy1 = 0; gy1 < grid[0].length - 1; gy1 += 1) {
                    int gx2 = gx1 + 1;
                    int gy2 = gy1 + 1;
                    int x1 = (int) (gx1 * xQuantum);
                    int x2 = (int) (gx2 * xQuantum);
                    int y1 = (int) (gy1 * yQuantum);
                    int y2 = (int) (gy2 * yQuantum);
                    int z11 = getZ(gx1, gy1);
                    int z12 = getZ(gx1, gy2);
                    int z21 = getZ(gx2, gy1);
                    int z22 = getZ(gx2, gy2);
                    int[][] points = new int[4][2];
                    for (int[] point : points) {
                        point[0] = -1;
                        point[1] = -1;
                    }
                    if (!foo(gx1, gy1, gx2, gy1, z)) {
                        double f1 = grid[gx1][gy1];
                        double f2 = grid[gx2][gy1];
                        for (int x = x1; x <= x2; x += 1) {
                            int expected;
                            if (z11 >= z) {
                                expected = z - 1;
                            } else {
                                expected = z;
                            }
                            double fx = linear(x1, x2, x, f1, f2);
                            if (getZByF(fx) == expected) {
                                points[0][0] = x;
                                points[0][1] = (int) (gy1 * yQuantum);
                                break;
                            }
                        }
                    }
                    if (!foo(gx2, gy1, gx2, gy2, z)) {
                        double f1 = grid[gx2][gy1];
                        double f2 = grid[gx2][gy2];
                        for (int y = y1; y <= y2; y += 1) {
                            int expected;
                            if (z21 >= z) {
                                expected = z - 1;
                            } else {
                                expected = z;
                            }
                            double fy = linear(y1, y2, y, f1, f2);
                            if (getZByF(fy) == expected) {
                                points[1][0] = (int) (gx2 * xQuantum);
                                points[1][1] = y;
                                break;
                            }
                        }
                    }
                    if (!foo(gx1, gy2, gx2, gy2, z)) {
                        double f1 = grid[gx1][gy2];
                        double f2 = grid[gx2][gy2];
                        for (int x = x1; x <= x2; x += 1) {
                            int expected;
                            if (z12 >= z) {
                                expected = z - 1;
                            } else {
                                expected = z;
                            }
                            double fx = linear(x1, x2, x, f1, f2);
                            if (getZByF(fx) == expected) {
                                points[2][0] = x;
                                points[2][1] = (int) (gy2 * yQuantum);
                                break;
                            }
                        }
                    }
                    if (!foo(gx1, gy1, gx1, gy2, z)) {
                        double f1 = grid[gx1][gy1];
                        double f2 = grid[gx1][gy2];
                        for (int y = y1; y <= y2; y += 1) {
                            int expected;
                            if (z11 >= z) {
                                expected = z - 1;
                            } else {
                                expected = z;
                            }
                            double fy = linear(y1, y2, y, f1, f2);
                            if (getZByF(fy) == expected) {
                                points[3][0] = (int) (gx1 * xQuantum);
                                points[3][1] = y;
                                break;
                            }
                        }
                    }
                    Graphics g = image.getGraphics();
                    g.setColor(new Color(getIsolineColor()));
                    if (points[0][0] != -1 && points[1][0] != -1 && points[2][0] != -1 && points[3][0] != -1) {
                        int xCenter = x1 + (x2 - x1) / 2;
                        int yCenter = y1 + (y2 - y1) / 2;
                        double fCenter = bilinear(xCenter, yCenter, x1, x2, y1, y2, grid[gx1][gy1], grid[gx2][gy1], grid[gx1][gy2], grid[gx2][gy2]);
                        int zCenter = getZByF(fCenter);
                        z11 = getZ(gx1, gy1);
                        if (z11 == zCenter) {
                            g.drawLine(points[0][0], points[0][1], points[1][0], points[1][1]);
                            g.drawLine(points[2][0], points[2][1], points[3][0], points[3][1]);
                            isoLines.add(new IsoLine(new Point(points[0][0], points[0][1]), new Point(points[1][0], points[1][1])));
                            isoLines.add(new IsoLine(new Point(points[2][0], points[2][1]), new Point(points[3][0], points[3][1])));
                        } else {
                            g.drawLine(points[0][0], points[0][1], points[3][0], points[3][1]);
                            g.drawLine(points[1][0], points[1][1], points[2][0], points[2][1]);
                            isoLines.add(new IsoLine(new Point(points[0][0], points[0][1]), new Point(points[3][0], points[3][1])));
                            isoLines.add(new IsoLine(new Point(points[1][0], points[1][1]), new Point(points[2][0], points[2][1])));
                        }
                    } else {
                        List<Point> lissOfPoints = new LinkedList<>();
                        for (int i = 0; i < points.length; i++) {
                            if (points[i][0] != -1) {
                                lissOfPoints.add(new Point(points[i][0], points[i][1]));
                            }
                        }
                        if (lissOfPoints.size() != 2 && lissOfPoints.size() != 0) {
                            throw new Error("gx: " + gx1 + " gy: " + gy1 + " z: " + z + " Must be 0, 2 or 4 points, not " + lissOfPoints.size());
                        }
                        if (lissOfPoints.size() == 2) {
                            Point p1 = lissOfPoints.get(0);
                            Point p2 = lissOfPoints.get(1);
                            g.drawLine(p1.x, p1.y, p2.x, p2.y);
                            isoLines.add(new IsoLine(p1, p2));
                        }
                    }
                }
            }
        }
        for (IsoLine isoLine : isoLines) {
            int x, y, x1, x2, y1, y2;
            double f11, f12, f21, f22;

            x = isoLine.p1.x - 1;
            y = isoLine.p1.y;
            while(x>=0 && getIsolineColor() == image.getRGB(x,y)) {
                x-=1;
            }
            if(x>=-1) {
                fillBilinear(x, y, xQuantum, yQuantum, image, getColor);
            }
            x = isoLine.p1.x + 1;
            y = isoLine.p1.y;
            while(x<image.getWidth() && getIsolineColor() == image.getRGB(x,y)) {
                x+=1;
            }
            if(x>=image.getWidth()) {
                fillBilinear(x, y, xQuantum, yQuantum, image, getColor);
            }

            x = isoLine.p1.x;
            y = isoLine.p1.y - 1;
            while(y>=0 && getIsolineColor() == image.getRGB(x,y) ) {
                y-=1;
            }
            if(y!=-1) {
                fillBilinear(x, y, xQuantum, yQuantum, image, getColor);
            }

            x = isoLine.p1.x;
            y = isoLine.p1.y + 1;
            while(y<image.getHeight() && getIsolineColor() == image.getRGB(x,y)) {
                y+=1;
            }
            if(y!=image.getHeight()) {
                fillBilinear(x, y, xQuantum, yQuantum, image, getColor);
            }
        }
    }

    public BufferedImage createIsoline(double[][] grid, double min, double max, double delta, int width, int height, int isolineX, int isolineY) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        double xQuantum = (double) (image.getWidth() - 1) / (grid.length - 1);
        double yQuantum = (double) (image.getHeight() - 1) / (grid[0].length - 1);
        this.grid = grid;
        this.delta = delta;
        this.min = min;
        int isolineX1 = (int)((double)isolineX/(image.getWidth() - 1) * (grid.length - 1));
        int isolineY1 = (int)((double)isolineY/(image.getHeight() - 1) * (grid[0].length - 1));
        double isolineValue = bilinear(isolineX,isolineY, (int) (isolineX1*xQuantum), (int) ((isolineX1 + 1)*xQuantum), (int) (isolineY1*yQuantum),(int) ((isolineY1+1)*yQuantum),grid[isolineX1][isolineY1],grid[isolineX1][isolineY1 + 1],grid[isolineX1 + 1][isolineY1],grid[isolineX1 + 1][isolineY1 + 1]);
        List<IsoLine> isoLines = new ArrayList<>();
        for (int gx1 = 0; gx1 < grid.length - 1; gx1 += 1) {
                for (int gy1 = 0; gy1 < grid[0].length - 1; gy1 += 1) {
                    int gx2 = gx1 + 1;
                    int gy2 = gy1 + 1;
                    int x1 = (int) (gx1 * xQuantum);
                    int x2 = (int) (gx2 * xQuantum);
                    int y1 = (int) (gy1 * yQuantum);
                    int y2 = (int) (gy2 * yQuantum);
                    int z11 = getZ(gx1, gy1);
                    int z12 = getZ(gx1, gy2);
                    int z21 = getZ(gx2, gy1);
                    int z22 = getZ(gx2, gy2);
                    int[][] points = new int[4][2];
                    for (int[] point : points) {
                        point[0] = -1;
                        point[1] = -1;
                    }
                    if (between(grid[gx1][gy1],grid[gx2][gy1],isolineValue)) {
                        double f1 = grid[gx1][gy1];
                        double f2 = grid[gx2][gy1];
                        boolean expectedLess;
                        if (f1 >= isolineValue) {
                            expectedLess = true;
                        } else {
                            expectedLess = false;
                        }
                        for (int x = x1; x <= x2; x += 1) {
                            double fx = linear(x1, x2, x, f1, f2);
                            if (fx < isolineValue && expectedLess || fx > isolineValue && !expectedLess) {
                                points[0][0] = x;
                                points[0][1] = (int) (gy1 * yQuantum);
                                break;
                            }
                        }
                    }
                    if (between(grid[gx2][gy1],grid[gx2][gy2],isolineValue)) {
                        double f1 = grid[gx2][gy1];
                        double f2 = grid[gx2][gy2];
                        boolean expectedLess;
                        if (f1 >= isolineValue) {
                            expectedLess = true;
                        } else {
                            expectedLess = false;
                        }
                        for (int y = y1; y <= y2; y += 1) {
                            double fy = linear(y1, y2, y, f1, f2);
                            if (fy < isolineValue && expectedLess || fy > isolineValue && !expectedLess) {
                                points[1][0] = (int) (gx2 * xQuantum);
                                points[1][1] = y;
                                break;
                            }
                        }
                    }
                    if (between(grid[gx1][gy2],grid[gx2][gy2],isolineValue)) {
                        double f1 = grid[gx1][gy2];
                        double f2 = grid[gx2][gy2];
                        boolean expectedLess;
                        if (f1 >= isolineValue) {
                            expectedLess = true;
                        } else {
                            expectedLess = false;
                        }
                        for (int x = x1; x <= x2; x += 1) {
                            double fx = linear(x1, x2, x, f1, f2);
                            if (fx < isolineValue && expectedLess || fx > isolineValue && !expectedLess) {
                                points[2][0] = x;
                                points[2][1] = (int) (gy2 * yQuantum);
                                break;
                            }
                        }
                    }
                    if (between(grid[gx1][gy1],grid[gx1][gy2],isolineValue)) {
                        double f1 = grid[gx1][gy1];
                        double f2 = grid[gx1][gy2];
                        boolean expectedLess;
                        if (f1 >= isolineValue) {
                            expectedLess = true;
                        } else {
                            expectedLess = false;
                        }
                        for (int y = y1; y <= y2; y += 1) {
                            double fy = linear(y1, y2, y, f1, f2);
                            if (fy < isolineValue && expectedLess || fy > isolineValue && !expectedLess) {
                                points[3][0] = (int) (gx1 * xQuantum);
                                points[3][1] = y;
                                break;
                            }
                        }
                    }
                    Graphics g = image.getGraphics();
                    g.setColor(new Color(getIsolineColor()));
                    if (points[0][0] != -1 && points[1][0] != -1 && points[2][0] != -1 && points[3][0] != -1) {
                        int xCenter = x1 + (x2 - x1) / 2;
                        int yCenter = y1 + (y2 - y1) / 2;
                        double fCenter = bilinear(xCenter, yCenter, x1, x2, y1, y2, grid[gx1][gy1], grid[gx2][gy1], grid[gx1][gy2], grid[gx2][gy2]);
                        int zCenter = getZByF(fCenter);
                        z11 = getZ(gx1, gy1);
                        if (z11 == zCenter) {
                            g.drawLine(points[0][0], points[0][1], points[1][0], points[1][1]);
                            g.drawLine(points[2][0], points[2][1], points[3][0], points[3][1]);
                            isoLines.add(new IsoLine(new Point(points[0][0], points[0][1]), new Point(points[1][0], points[1][1])));
                            isoLines.add(new IsoLine(new Point(points[2][0], points[2][1]), new Point(points[3][0], points[3][1])));
                        } else {
                            g.drawLine(points[0][0], points[0][1], points[3][0], points[3][1]);
                            g.drawLine(points[1][0], points[1][1], points[2][0], points[2][1]);
                            isoLines.add(new IsoLine(new Point(points[0][0], points[0][1]), new Point(points[3][0], points[3][1])));
                            isoLines.add(new IsoLine(new Point(points[1][0], points[1][1]), new Point(points[2][0], points[2][1])));
                        }
                    } else {
                        List<Point> lissOfPoints = new LinkedList<>();
                        for (int i = 0; i < points.length; i++) {
                            if (points[i][0] != -1) {
                                lissOfPoints.add(new Point(points[i][0], points[i][1]));
                            }
                        }
                        if (lissOfPoints.size() == 2) {
                            Point p1 = lissOfPoints.get(0);
                            Point p2 = lissOfPoints.get(1);
                            g.drawLine(p1.x, p1.y, p2.x, p2.y);
                            isoLines.add(new IsoLine(p1, p2));
                        }
                    }
                }
            }
        return image;
    }

    private void fillBilinear(int x, int y, double xQuantum, double yQuantum, BufferedImage image, DoubleToInt getColor) {
        if(x>=image.getWidth() || y>=image.getHeight() || y<0 || x<0) {
            return;
        }
        if(image.getRGB(x,y) == getIsolineColor()) {
            return;
        }
        int x1 = (int) ((int) (x / xQuantum) * xQuantum);
        int x2 = (int) ((int) (x / xQuantum + 1) * xQuantum);
        int y1 = (int) ((int) (y / yQuantum) * yQuantum);
        int y2 = (int) ((int) (y / yQuantum + 1) * yQuantum);
        double f11 = grid[(int) (x1 / xQuantum)][(int) (y1 / yQuantum)];
        double f12 = grid[(int) (x1 / xQuantum)][(int) (y2 / yQuantum)];
        double f21 = grid[(int) (x2 / xQuantum)][(int) (y1 / yQuantum)];
        double f22 = grid[(int) (x2 / xQuantum)][(int) (y2 / yQuantum)];
        double bilinearTMP = bilinear(x, y, x1, x2, y1, y2, f11, f12, f21, f22);
        int color = getColor.apply(getProcentByF(bilinearTMP));
        /*if(color != -8323328) {
            System.out.println(color);
        }*/
        fill(image, x, y, color);
    }

    private void fill(BufferedImage image, int x, int y, int color) {
        Stack<Span> stack = new Stack<>();
        Graphics g = image.getGraphics();
        g.setColor(new Color(color));
        if (x >= image.getWidth() || y >= image.getHeight() || x < 0 || y < 0) {
            return;
        }
        int seedColor = image.getRGB(x, y);
        if (seedColor == color) {
            return;
        }
        Span span = findSpan(image, x, y);
        stack.push(span);
        while (!stack.empty()) {
            span = stack.pop();
            g.drawLine(span.x1, span.y, span.x2, span.y);
            findNeighborSpans(image,span, stack, seedColor,color);
        }
    }

    void findNeighborSpans(BufferedImage image, Span span, Stack<Span> stack, int seedColor,int color) {
        int i = span.x1;
        int y = span.y + 1;
        int finish = Math.min(span.x2, image.getWidth() - 1);
        Span newSpan;
        if (y < image.getHeight() && y >= 0) {
            while (i <= finish) {
                if (image.getRGB(i, y) == seedColor) {
                    newSpan = findSpan(image,i, y);
                    stack.push(newSpan);
                    i = newSpan.x2 + 1;
                }
                i += 1;
            }
        }

        i = span.x1;
        y = span.y - 1;
        Graphics g = image.getGraphics();
        g.setColor(new Color(color));
        if (y < image.getHeight() && y >= 0) {
            while (i <= finish) {
                if (image.getRGB(i, y) == seedColor) {
                    newSpan = findSpan(image,i, y);
                    stack.push(newSpan);
                    g.drawLine(span.x1, span.y, span.x2, span.y);
                    i = newSpan.x2 + 1;
                }
                i += 1;
            }
        }
    }

    Span findSpan(BufferedImage image, int px, int py) {
        int y = py;
        int ix = px;
        Span span = new Span();
        span.y = y;
        int color = image.getRGB(ix, y);
        int finish = image.getWidth();
        while (ix < finish && image.getRGB(ix, y) == color) {
            ix++;
        }
        span.x2 = ix - 1;
        ix = px;
        while (ix >= 0 && image.getRGB(ix, y) == color) {
            ix--;
        }
        span.x1 = ix + 1;
        return span;
    }


    private class Span {
        public int x1;
        public int x2;
        public int y;

        public Span() {
        }

        Span(int _x1, int _x2, int _y) {
            x1 = _x1;
            x2 = _x2;
            y = _y;
        }
    }

    private class IsoLine {
        public Point p1;
        public Point p2;

        private IsoLine(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
}
