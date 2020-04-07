package icgfilter_borzov.Instruments;

import icgfilter_borzov.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public class None implements Instrument {
    @Override
    public BufferedImage doWork(BufferedImage image) {
        return image;
    }

    @Override
    public MyDialog getParameterDialog() {
        return null;
    }
}
