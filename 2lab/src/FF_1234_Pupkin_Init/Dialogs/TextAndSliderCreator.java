package FF_1234_Pupkin_Init.Dialogs;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Consumer;

public class TextAndSliderCreator {
    public static TextAndSlider create(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
        TextAndSlider ts = createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
        return ts;
    }
    public static TextAndSlider createDefault(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
        TextAndSlider ts = createTextAndSLider(minvalue,maxvalue,maxlength,defaultvalue);
        ts.text.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if("".equals(ts.text.getText())){
                    ts.text.setText(String.valueOf(1));
                    return;
                }
                int inputValue=Integer.parseInt(ts.text.getText());
                if(minvalue>inputValue){
                    ts.text.setText(String.valueOf(minvalue));
                    JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
                    return;
                }
                if(maxvalue<inputValue) {
                    ts.text.setText(String.valueOf(maxvalue));
                    JOptionPane.showMessageDialog(null,"Enter a numerical value from " + minvalue + " to " + maxvalue);
                    return;
                }
                int value = Integer.parseInt(ts.text.getText());
                //line.setSize(value);
                //setter.accept(value);
                ts.slider.setValue(Integer.parseInt(ts.text.getText()));
            }
        });
        return ts;
    }
    private static TextAndSlider createTextAndSLider(int minvalue, int maxvalue, int maxlength, int defaultvalue) {
        JFormattedTextField text =new JFormattedTextField();
        text.setPreferredSize(new Dimension(35,20));
        JSlider jSlider = new JSlider(minvalue, maxvalue, defaultvalue);
        text.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
                if(str.length()+getLength()>maxlength){
                    return;
                }
                for(int i=0;i<str.length();++i){
                    if(!Character.isDigit(str.charAt(i))){
                        return;
                    }
                }
                super.insertString(offs,str,a);
            }
        });
        text.setText(String.valueOf(defaultvalue));
        text.setPreferredSize(new Dimension(35,20));
        text.setText(String.valueOf(defaultvalue));
        text.setVisible(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = (jSlider.getValue());
                //line.setSize(value);
                //setter.accept(value);
                text.setText(String.valueOf(value));
            }
        });
        return new TextAndSlider(text,jSlider);
    }

}
