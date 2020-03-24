package FF_1234_Pupkin_Init.Instruments;

import FF_1234_Pupkin_Init.Dialogs.FloydDitherDialog;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FloydDither implements Instrument {
    private final int cSize = 256;
    int quantumCount = 4;
    int redCount = 10;
    int blueCount = 10;
    int greenCount = 10;
    FloydDitherDialog dialog;
    public FloydDither() {
        dialog = new FloydDitherDialog(this);
    }
    private final double v1 = 7.0 / 16;
    private final double v2 = 3.0 / 16;
    private final double v3 = 5.0 / 16;
    private final double v4 = 1 - v1 - v2 - v3;
    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        toReturn.getGraphics().drawImage(image, 0, 0, null);
        for (int y = 0; y < toReturn.getHeight(); y++) {
            for (int x = 0; x < toReturn.getWidth(); x++) {
                int pixel = toReturn.getRGB(x, y);
                int[] errors;
                int[] BGRA = getBGRA(pixel);
                int[][] nextBGRA = new int[4][4];
                int[] quantums = new int[3];
                quantums[0] = (cSize - 1) / blueCount;
                quantums[1] = (cSize - 1) / greenCount;
                quantums[2] = (cSize - 1) / redCount;
                for (int neighbor = 0; neighbor < 3; neighbor++) {
                    for (int channel = 0; channel < 3; channel++) {
                        errors = getError(BGRA[channel],quantums[channel]);
                        BGRA[channel] = (int) Math.floor(((double) channel / quantums[channel]) + 0.5) * quantums[channel];
                        nextBGRA[neighbor][channel] = errors[neighbor];
                    }
                    if (x + 1 < toReturn.getWidth()) {
                        toReturn.setRGB(x + 1, y, getIntFromBGRA(addToPixel(getBGRA(toReturn.getRGB(x + 1, y)), nextBGRA[0])));
                    }
                    if (y + 1 < toReturn.getHeight()) {
                        if (x - 1 > 0) {
                            toReturn.setRGB(x - 1, y + 1, getIntFromBGRA(addToPixel(getBGRA(toReturn.getRGB(x - 1, y + 1)), nextBGRA[1])));
                        }
                        toReturn.setRGB(x, y + 1, getIntFromBGRA(addToPixel(getBGRA(toReturn.getRGB(x, y + 1)), nextBGRA[2])));
                        if (x + 1 < toReturn.getHeight()) {
                            toReturn.setRGB(x + 1, y + 1, getIntFromBGRA(addToPixel(getBGRA(toReturn.getRGB(x + 1, y + 1)), nextBGRA[3])));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private int[] getError(int channel, int quantumSize) {
        int[] errors = new int[4];
        int newP = (int) Math.floor(((double) channel / quantumSize) + 0.5) * quantumSize;
        double e = channel - newP; // ошибка
        errors[0] = (int) Math.round(v1 * e);//???:может стоит создавать массив даблов размером с изображение и по нему передавать ошибку? Как-то дорого
        errors[1] = (int) Math.round(v2 * e);
        errors[2] = (int) Math.round(v3 * e);
        errors[3] = (int) Math.round(v4 * e);
        return errors;
    }

    private int[] addToPixel(int[] aBGRA, int[] bBGRA) {
        int[] rBGRA = new int[4];
        for (int i = 0; i < 4; i++) {
            rBGRA[i] = Math.max(Math.min(aBGRA[i] + bBGRA[i], 255),0);
        }
        return rBGRA;
    }

    private int[] getBGRA(int pixel) {
        int[] BGRA = new int[4];
        BGRA[0] = (pixel & 0x000000FF);
        BGRA[1] = ((pixel & 0x0000FF00) >> 8);
        BGRA[2] = (pixel & 0x00FF0000) >> 16;
        BGRA[3] = (pixel & 0xFF000000) >> 24;
        return BGRA;
    }

    private int getIntFromBGRA(int[] BGRA) {
        int pixel = BGRA[0] | (BGRA[1] << 8) | (BGRA[2] << 16) | (BGRA[3] << 24);
        return pixel;
    }
    @Override
    public JPanel getParameterDialog() {
        return dialog;
    }

    public int getBlue() {
        return blueCount;
    }

    public int getGreen() {
        return greenCount;
    }

    public int getRed() {
        return redCount;
    }

    public void setBlue(int blueCount) {
        this.blueCount = blueCount;
    }

    public void setGreen(int greenCount) {
        this.greenCount = greenCount;
    }

    public void setRed(int redCount) {
        this.redCount = redCount;
    }
}
