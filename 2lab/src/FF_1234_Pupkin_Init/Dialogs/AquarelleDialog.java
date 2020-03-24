package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Instruments.Aquarelle;
import FF_1234_Pupkin_Init.Instruments.Sobel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AquarelleDialog  extends JPanel {
    final int MIN_VALUE = 1;
    final int MAX_VALUE = 100;
    final int MAX_VALUE_LENGTH = 3;
    public JFormattedTextField valueText;
    Aquarelle aquarelle;
    public AquarelleDialog(Aquarelle aquarelle) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        this.aquarelle = aquarelle;
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, aquarelle.getValue());
        paramsPanel.add(new Label("parameter:"));
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
                JDialog frame = (JDialog)p.getRootPane().getParent();
                aquarelle.setValue(Integer.parseInt(valueText.getText()));
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog)p.getRootPane().getParent();
                valueText.setText(String.valueOf(aquarelle.getValue()));
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}
