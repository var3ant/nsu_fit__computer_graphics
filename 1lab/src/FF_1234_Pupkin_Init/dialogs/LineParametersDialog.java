package FF_1234_Pupkin_Init.dialogs;

import FF_1234_Pupkin_Init.instruments.Line;
import FF_1234_Pupkin_Init.instruments.TextAndSlider;
import FF_1234_Pupkin_Init.instruments.TextAndSliderCreator;

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

public class LineParametersDialog extends ParametersDialog {
    JFormattedTextField sizeField;
    Line line;
    int value;
    private final int MAXLENGTH = 3;
    private final int MAXVALUE = 50;
    private final int MINVALUE = 1;
    private JSlider lineWidthSlider;
    public LineParametersDialog(Line _line) {
        /*
        sizeField = new JFormattedTextField();
        sizeField.setColumns(2);
        sizeField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                Integer newValue = (Integer) propertyChangeEvent.getNewValue();
                if (sizeField.getText().length() != 0) {
                    value = Integer.parseInt(sizeField.getText());
                    line.setSize(value);
                }
            }
        });
        add(sizeField);*/
        line = _line;
        /*sizeField=new JFormattedTextField();
        sizeField.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
                if(str.length()+getLength()>MAXLENGTH){
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
        sizeField.setText(String.valueOf(value));
        sizeField.setPreferredSize(new Dimension(35,20));
        sizeField.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if("".equals(sizeField.getText())){
                    //JOptionPane.showMessageDialog(null,"You should enter value between " + MINVALUE + " and "+MAXVALUE);
                    sizeField.setText(String.valueOf(value));
                    return;
                }
                int inputValue=Integer.parseInt(sizeField.getText());
                if(MINVALUE>inputValue||MAXVALUE<inputValue){
                    //JOptionPane.showMessageDialog(null,"You should enter value between " + MINVALUE + " and "+MAXVALUE);
                    sizeField.setText(String.valueOf(value));
                    return;
                }
                value = Integer.parseInt(sizeField.getText());
                line.setSize(value);
                lineWidthSlider.setValue(value);
            }
        });
        value = line.getSize();
        sizeField.setText(String.valueOf(value));
        add(new JLabel("width"),BorderLayout.WEST);
        add(sizeField, BorderLayout.EAST);
        sizeField.setVisible(true);

        lineWidthSlider = new JSlider(MINVALUE, MAXVALUE, value);
        lineWidthSlider.setPaintTicks(true);
        lineWidthSlider.setPaintLabels(true);
        lineWidthSlider.setMajorTickSpacing(10);
        lineWidthSlider.setMinorTickSpacing(1);

        lineWidthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                value = (lineWidthSlider.getValue());
                line.setSize(value);
                sizeField.setText(String.valueOf(value));
            }
        });
        add(lineWidthSlider);*/
        TextAndSlider ts= TextAndSliderCreator.create(MINVALUE, MAXVALUE, MAXLENGTH, line.getSize(), value -> line.setSize(value));
        add(ts.text);
        add(ts.slider);
    }
    public int getValue() {
        return value;
    }
}
