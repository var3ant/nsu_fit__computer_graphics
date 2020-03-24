package FF_1234_Pupkin_Init.Instruments;

import javax.swing.*;
import java.awt.image.BufferedImage;

public interface Instrument {
    public BufferedImage doWork(BufferedImage image);

    JPanel getParameterDialog();
}
