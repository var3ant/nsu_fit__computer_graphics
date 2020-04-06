package icgfilter_borzov.Instruments;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface Instrument {
    BufferedImage doWork(BufferedImage image);

    JPanel getParameterDialog();
}
