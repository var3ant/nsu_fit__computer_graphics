package icgfilter_borzov.Dialogs;

import icgfilter_borzov.Dialogs.TextAndSlider.DoudleTextAndSLiderCreator;
import icgfilter_borzov.Dialogs.TextAndSlider.TextAndSlider;
import icgfilter_borzov.Dialogs.propertylisteners.DoublePropertyListener;
import icgfilter_borzov.Instruments.Gamma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GammaDialog extends JPanel {
    final double MIN_VALUE = 0.1;
    final double MAX_VALUE = 10;
    final int MAX_VALUE_LENGTH = 4;
    JTextField valueText;
    Gamma gamma;
    public GammaDialog(Gamma gamma) {
        super();
        setLayout(new GridLayout(2, 1));
        this.gamma = gamma;
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        TextAndSlider ts = DoudleTextAndSLiderCreator.create(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, gamma.getGamme());
        ts.text.addPropertyChangeListener(new DoublePropertyListener(MIN_VALUE,MAX_VALUE,ts.text,ts.slider));
        paramsPanel.add(new Label("p:"));
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
            gamma.setGamma((double)Math.round(Double.parseDouble(valueText.getText())*10)/10);
            valueText.setText(String.valueOf(gamma.getGamme()));
            frame.dispose();
        });
        cancelButton.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            valueText.setText(String.valueOf(gamma.getGamme()));
            frame.dispose();
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}
