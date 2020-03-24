package FF_1234_Pupkin_Init.instruments;

import FF_1234_Pupkin_Init.dialogs.ParametersDialog;

import java.awt.*;

public interface Instrument {
    public ParametersDialog getParameterDialog();
    public void setColor(int _color);
}
