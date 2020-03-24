package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Instruments.FloydDither;
import FF_1234_Pupkin_Init.Instruments.OrderedDither;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FloydDitherDialog extends JPanel {
    final int MIN_VALUE = 2;
    final int MAX_VALUE = 128;
    final int MAX_VALUE_LENGTH = 3;
    FloydDither floydDither;
    JTextField blueText;
    JTextField greenText;
    JTextField redText;
    public FloydDitherDialog(FloydDither floydDither) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(3, 3));
        JPanel buttonPanel = new JPanel();
        this.floydDither = floydDither;
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, floydDither.getRed());
        paramsPanel.add(new Label("Red:"));
        redText = ts.text;
        paramsPanel.add(redText);
        paramsPanel.add(ts.slider);

        ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, floydDither.getGreen());
        paramsPanel.add(new Label("Green:"));
        greenText = ts.text;
        paramsPanel.add(greenText);
        paramsPanel.add(ts.slider);

        ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, floydDither.getBlue());
        paramsPanel.add(new Label("Blue:"));
        blueText = ts.text;
        paramsPanel.add(blueText);
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
                floydDither.setBlue(Integer.parseInt(blueText.getText()));
                floydDither.setGreen(Integer.parseInt(greenText.getText()));
                floydDither.setRed(Integer.parseInt(redText.getText()));
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                blueText.setText(String.valueOf(floydDither.getBlue()));
                greenText.setText(String.valueOf(floydDither.getGreen()));
                redText.setText(String.valueOf(floydDither.getRed()));
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}