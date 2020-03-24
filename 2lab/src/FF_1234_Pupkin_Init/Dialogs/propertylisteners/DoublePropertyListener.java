package FF_1234_Pupkin_Init.Dialogs.propertylisteners;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class DoublePropertyListener implements PropertyChangeListener {
    private int minvalue;
    private int maxvalue;
    private JFormattedTextField text;
    private JSlider slider;
    private Consumer<Double> setter;
    public DoublePropertyListener(int minvalue, int maxvalue, JFormattedTextField text, JSlider slider, Consumer<Double> setter) {
        super();
        this.maxvalue = maxvalue;
        this.minvalue = minvalue;
        this.text = text;
        this.setter = setter;
        this.slider = slider;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("".equals(text.getText())) {
            text.setText(String.valueOf(1));
            return;
        }
        double inputValue = Double.parseDouble(text.getText())*10;
        if (minvalue > inputValue) {
            text.setText(String.valueOf(minvalue/10));
            return;
        }
        if (maxvalue < inputValue) {
            text.setText(String.valueOf(maxvalue/10));
            return;
        }
        int value = Integer.parseInt(text.getText());
        //line.setSize(value);
        setter.accept((double)value/10);
        slider.setValue((int)Double.parseDouble(text.getText())*10);
    }
}
