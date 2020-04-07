package icgfilter_borzov.Dialogs;

import icgfilter_borzov.Dialogs.TextAndSlider.TextAndSlider;
import icgfilter_borzov.Dialogs.TextAndSlider.TextAndSliderCreator;
import icgfilter_borzov.Dialogs.propertylisteners.OddPropertyListener;
import icgfilter_borzov.Instruments.Blur;

import javax.swing.*;
import java.awt.*;

public class BlurDialog extends JPanel implements MyDialog {
    final int MIN_VALUE = 3;
    final int MAX_VALUE = 11;
    final int MAX_VALUE_LENGTH = 2;
    private boolean isOk = false;
    Blur blur;
    JTextField valueText;
    public BlurDialog(Blur blur) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            blur.setMatrixSize(Integer.parseInt(valueText.getText()));
            isOk = true;
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            valueText.setText(String.valueOf(blur.getMatrixSize()));
            isOk = false;
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }

    @Override
    public boolean isDialogResult() {
        return isOk;
    }
}