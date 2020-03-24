package FF_1234_Pupkin_Init.instruments;

import org.w3c.dom.Text;

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
    public static TextAndSlider create(int minvalue, int maxvalue, int maxlength, int defaultvalue, Consumer<Integer> setter) {
        JFormattedTextField text =new JFormattedTextField();
        JSlider jSlider = new JSlider(minvalue, maxvalue, defaultvalue);;
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
        text.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if("".equals(text.getText())){
                    text.setText(String.valueOf(1));
                    return;
                }
                int inputValue=Integer.parseInt(text.getText());
                if(minvalue>inputValue){
                    text.setText(String.valueOf(minvalue));
                    return;
                }
                if(maxvalue<inputValue) {
                    text.setText(String.valueOf(maxvalue));
                    return;
                }
                int value = Integer.parseInt(text.getText());
                //line.setSize(value);
                setter.accept(value);
                jSlider.setValue(Integer.parseInt(text.getText()));
            }
        });
        //value = line.getSize();
        text.setText(String.valueOf(defaultvalue));
        //add(new JLabel("width"),BorderLayout.WEST);
        //add(text, BorderLayout.EAST);
        text.setVisible(true);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        //jSlider.setMajorTickSpacing(10);
        //jSlider.setMinorTickSpacing(1);

        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = (jSlider.getValue());
                //line.setSize(value);
                setter.accept(value);
                text.setText(String.valueOf(value));
            }
        });
        //add(jSlider);
        return new TextAndSlider(text,jSlider);
    }
}
