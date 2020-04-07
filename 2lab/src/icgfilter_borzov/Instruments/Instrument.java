package icgfilter_borzov.Instruments;

import icgfilter_borzov.Dialogs.MyDialog;

import java.awt.image.BufferedImage;

public interface Instrument {
    BufferedImage doWork(BufferedImage image);

    MyDialog getParameterDialog();
}
