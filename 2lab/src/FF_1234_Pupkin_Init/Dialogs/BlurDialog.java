package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Dialogs.propertylisteners.OddPropertyListener;
import FF_1234_Pupkin_Init.Instruments.Blur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlurDialog extends JPanel {
    final int MIN_VALUE = 3;
    final int MAX_VALUE = 11;
    final int MAX_VALUE_LENGTH = 2;
    Blur blur;
    JTextField valueText;
    public BlurDialog(Blur blur) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        setLayout(new GridLayout(2, 1));
        this.blur = blur;
        TextAndSlider ts = TextAndSliderCreator.create(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, blur.getMatrixSize());
        OddPropertyListener propertyListener = new OddPropertyListener(MIN_VALUE, MAX_VALUE, ts.text, ts.slider);
        ts.text.addPropertyChangeListener(propertyListener);
        paramsPanel.add(new Label("Value:"));
        paramsPanel.add(ts.text);
        paramsPanel.add(ts.slider);
        valueText = ts.text;
        paramsPanel.add(ts.text);
        paramsPanel.add(ts.slider);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                blur.setMatrixSize(Integer.parseInt(valueText.getText()));
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                valueText.setText(String.valueOf(blur.getMatrixSize()));
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}