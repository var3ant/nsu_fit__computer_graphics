package icgfilter_borzov.Instruments;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class None implements Instrument {
    @Override
    public BufferedImage doWork(BufferedImage image) {
        return image;
    }

    @Override
    public JPanel getParameterDialog() {
        return null;
    }
}
