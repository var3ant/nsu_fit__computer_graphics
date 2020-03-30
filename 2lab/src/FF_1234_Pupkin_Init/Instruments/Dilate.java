package FF_1234_Pupkin_Init.Instruments;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Dilate implements Instrument {
    int shift = 9;
    int black = 0;
    int white = 255;
    @Override
    public BufferedImage doWork(BufferedImage image) {//изображение на входе должно иметь только два цвета Черный и Белый.
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        toReturn.getGraphics().drawImage(image, 0, 0, null);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int mainPixel = image.getRGB(x, y);
                int mR = (mainPixel & 0x00FF0000) >> 16; //красный
                int result = mR;
                if(mR == black) {
                    result = checkNeibors(image,x,y);
                }
                int resultPixel = result | (result << 8) | (result << 16) | (255 << 24);
                toReturn.setRGB(x, y, resultPixel);
            }
        }
        return toReturn;
    }
    int checkNeibors(BufferedImage image, int x,int y) {
        for (int i = -shift; i <= shift; i++) {
            for (int j = -shift; j <= shift; j++) {
                if (x + i >= 0 & x + i < image.getWidth() & y + j >= 0 & y + j < image.getHeight() & (i!=0 | j!=0)) {
                    int widowPixel = image.getRGB(x + i, y + j);
                    int wR = (widowPixel & 0x00FF0000) >> 16; //красный
                    if(wR == black) {
                        return black;
                    }
                }
            }
        }
        return white;
    }
    @Override
    public JPanel getParameterDialog() {
        return null;
    }
    public void setShift(int shift) {
        this.shift = shift;
    }
    public int getShift() {
        return shift;
    }

    public int getMode() {
        return black/255;
    }
    public void setMode(int mode) {
        if(mode==0) {
            black = 255;
            white = 0;
        } else {
            black = 0;
            white = 255;
        }
    }
}
