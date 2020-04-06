package icgfilter_borzov.Dialogs;

import icgfilter_borzov.Dialogs.TextAndSlider.TextAndSlider;
import icgfilter_borzov.Dialogs.TextAndSlider.TextAndSliderCreator;
import icgfilter_borzov.Instruments.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RotateDialog extends JPanel {
    final int MIN_VALUE = -180;
    final int MAX_VALUE = 180;
    final int MAX_VALUE_LENGTH = 4;
    JTextField valueText;
    Rotate rotate;

    public RotateDialog(Rotate rotate) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.rotate = rotate;
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, rotate.getAngle());
        paramsPanel.add(new Label("Angle:"));
        valueText = ts.text;
        paramsPanel.add(valueText);
        paramsPanel.add(ts.slider);
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel p = this;
        okButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            rotate.setAngle(Integer.parseInt(valueText.getText()));
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            valueText.setText(String.valueOf(rotate.getAngle()));
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}