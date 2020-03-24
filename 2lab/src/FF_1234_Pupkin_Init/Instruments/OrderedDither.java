package FF_1234_Pupkin_Init.Instruments;

import FF_1234_Pupkin_Init.Dialogs.OrderedDitherDialog;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class OrderedDither implements Instrument {
    private final int cSize = 256;
    private int[][] matrix;
    private int k;
    private int quantumCountR = 5;
    private int quantumCountG = 5;
    private int quantumCountB = 5;
    private OrderedDitherDialog dialog;
    public OrderedDither() { ;
        dialog = new OrderedDitherDialog(this);
        matrix = new int[][]{
                {0, 32, 8, 40, 2, 34, 10, 42},
                {48, 16, 56, 24, 50, 18, 58, 26},
                {12, 44, 4, 36, 14, 46, 6, 38},
                {60, 28, 52, 20, 62, 30, 54, 22},
                {3, 35, 11, 43, 1, 33, 9, 41},
                {51, 19, 59, 27, 49, 17, 57, 25},
                {15, 47, 7, 39, 13, 45, 5, 37},
                {61, 31, 55, 23, 61, 29, 53, 21},
        };
        k = 64;
    }

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        toReturn.getGraphics().drawImage(image, 0, 0, null);
        int[] colors = new int[cSize];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = 0;
        }
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int R = (mainPixel & 0x00FF0000) >> 16; //красный
                int G = ((mainPixel & 0x0000FF00) >> 8); // зеленый
                int B = (mainPixel & 0x000000FF); // синий
                int resultA = (mainPixel & 0xFF000000) >> 24; // синий
                int resultR = dither(R,x,y, quantumCountR);
                int resultG = dither(G,x,y,quantumCountG);
                int resultB = dither(B,x,y,quantumCountB);
                int resultPixel = resultB | (resultG << 8) | (resultR << 16) | (resultA << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }

    @Override
    public JPanel getParameterDialog() {
        return dialog;
    }

    int dither(int bright,int x, int y, int quantumCount) {
        int quantumSize = (cSize - 1) / quantumCount;
        int resultBright = (int) Math.round(Math.round((bright + quantumSize * ((double) matrix[y % matrix.length][x % matrix.length] / k - 0.5)) / quantumSize) * quantumSize);
        return Math.max(0, Math.min(resultBright, cSize - 1));
    }

    public int getRed() {
        return quantumCountR;
    }
    public int getGreen() {
        return quantumCountG;
    }
    public int getBlue() {
        return quantumCountB;
    }

    public void setRed(int red) {
        this.quantumCountR = red;
    }
    public void setGreen(int green) {
        this.quantumCountG = green;
    }
    public void setBlue(int blue) {
        this.quantumCountB = blue;
    }
}
