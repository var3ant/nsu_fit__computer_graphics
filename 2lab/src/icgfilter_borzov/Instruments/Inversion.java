package icgfilter_borzov.Instruments;

import icgfilter_borzov.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class Inversion implements Instrument{

    @Override
    public BufferedImage doWork(BufferedImage image) {
        BufferedImage toReturn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int R = (pixel & 0x00FF0000) >> 16; //красный
                int G = ((pixel & 0x0000FF00) >> 8); // зеленый
                int B = (pixel & 0x000000FF); // синий
                int A = (pixel & 0xFF000000) >> 24;
                pixel = (255 - B) | ((255 - G) << 8) | ((255 - R) << 16) | (A << 24);
                toReturn.setRGB(x, y, pixel);
                //System.out.print("result");
            }
        }
        return toReturn;
    }
    @Override
    public MyDialog getParameterDialog() {
        return null;
    }
}
