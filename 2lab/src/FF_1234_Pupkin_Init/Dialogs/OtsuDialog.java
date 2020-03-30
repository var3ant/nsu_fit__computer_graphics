package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Instruments.OrderedDither;
import FF_1234_Pupkin_Init.Instruments.Otsu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OtsuDialog extends JPanel {
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 9;
    final int MAX_VALUE_LENGTH = 1;
    Otsu otsu;
    JTextField erodeText;
    JTextField dilateText;
    /*JRadioButton blackEroseButton;
    JRadioButton whiteEroseButton;
    JRadioButton blackDilateButton;
    JRadioButton whiteDilateButton;*/
    public OtsuDialog(Otsu otsu) {
        super();
        this.otsu = otsu;
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(3, 3));
        JPanel buttonPanel = new JPanel();
        paramsPanel.add(new Label("Ops/params:"));
        paramsPanel.add(new Label("Dilate"));
        paramsPanel.add(new Label("Erode"));
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, otsu.dilate.getShift());
        paramsPanel.add(new Label("Shift:"));
        dilateText = ts.text;
        paramsPanel.add(dilateText);
        paramsPanel.add(ts.slider);

        ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, otsu.erode.getShift());
        erodeText = ts.text;
        paramsPanel.add(erodeText);
        paramsPanel.add(ts.slider);

        paramsPanel.add(new Label("Mode:"));
        ButtonGroup dilateMode = new ButtonGroup();
        JRadioButton blackDilateButton= new JRadioButton("black", false);
        JRadioButton whiteDilateButton= new JRadioButton("white", false);
        dilateMode.add(blackDilateButton);
        dilateMode.add(whiteDilateButton);
        JPanel dialoteModePanel = new JPanel();
        dialoteModePanel.add(blackDilateButton);
        dialoteModePanel.add(whiteDilateButton);
        paramsPanel.add(dialoteModePanel);

        ButtonGroup eroseMode = new ButtonGroup();
        JRadioButton blackEroseButton= new JRadioButton("black", false);
        JRadioButton whiteEroseButton= new JRadioButton("white", false);
        eroseMode.add(blackEroseButton);
        eroseMode.add(whiteEroseButton);
        JPanel eroseModePanel = new JPanel();
        eroseModePanel.add(blackEroseButton);
        eroseModePanel.add(whiteEroseButton);
        paramsPanel.add(eroseModePanel);
        if(otsu.erode.getMode() == 0) {
            blackEroseButton.setSelected(true);
        } else {
            whiteEroseButton.setSelected(true);
        }
        if(otsu.dilate.getMode() == 0) {
            blackDilateButton.setSelected(true);
        } else {
            whiteDilateButton.setSelected(true);
        }
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                otsu.dilate.setShift(Integer.parseInt(dilateText.getText()));
                otsu.erode.setShift(Integer.parseInt(erodeText.getText()));
                if(blackDilateButton.isSelected()) {
                    otsu.dilate.setMode(0);
                } else {
                    otsu.dilate.setMode(1);
                }
                if(blackEroseButton.isSelected()) {
                    otsu.erode.setMode(0);
                } else {
                    otsu.erode.setMode(1);
                }
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                erodeText.setText(String.valueOf(otsu.erode.getShift()));
                dilateText.setText(String.valueOf(otsu.dilate.getShift()));
                if(otsu.erode.getMode() == 0) {
                    blackEroseButton.setSelected(true);
                } else {
                    whiteEroseButton.setSelected(true);
                }
                if(otsu.dilate.getMode() == 0) {
                    blackDilateButton.setSelected(true);
                } else {
                    whiteDilateButton.setSelected(true);
                }
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}