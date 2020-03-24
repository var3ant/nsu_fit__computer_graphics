package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Instruments.Gamma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GammaDialog extends JPanel {
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 100;
    final int MAX_VALUE_LENGTH = 3;
    JTextField valueText;
    Gamma gamma;
    public GammaDialog(Gamma gamma) {
        super();
        setLayout(new GridLayout(2, 1));
        this.gamma = gamma;
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, (int)(gamma.getGamme()*10));
        paramsPanel.add(new Label("p:"));
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
                gamma.setGamma((double)Integer.parseInt(valueText.getText())/10);
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog) p.getRootPane().getParent();
                valueText.setText(String.valueOf((int)(gamma.getGamme()*10)));
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}
