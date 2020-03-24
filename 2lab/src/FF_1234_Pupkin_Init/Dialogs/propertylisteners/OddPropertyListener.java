package FF_1234_Pupkin_Init.Dialogs.propertylisteners;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class OddPropertyListener implements PropertyChangeListener {
    private int minvalue;
    private int maxvalue;
    private JFormattedTextField text;
    private JSlider slider;
    public OddPropertyListener(int minvalue, int maxvalue, JFormattedTextField text, JSlider slider) {
        super();
        this.maxvalue = maxvalue;
        this.minvalue = minvalue;
        this.text = text;
        this.slider = slider;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("".equals(text.getText())) {
            text.setText(String.valueOf(1));
            return;
        }
        int inputValue = Integer.parseInt(text.getText());
        if (minvalue > inputValue) {
            text.setText(String.valueOf(minvalue));
            JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
            return;
        }
        if (maxvalue < inputValue) {
            text.setText(String.valueOf(maxvalue));
            JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
            return;
        }
        if (inputValue%2 == 0) {
            inputValue+=1;
            Math.min(Math.max(inputValue,minvalue),maxvalue);
            JOptionPane.showMessageDialog(null,"Entered value must be odd");
            text.setText(String.valueOf(inputValue));
            return;
        }
        int value = Integer.parseInt(text.getText());
        //line.setSize(value);
        slider.setValue(Integer.parseInt(text.getText()));
    }
}
