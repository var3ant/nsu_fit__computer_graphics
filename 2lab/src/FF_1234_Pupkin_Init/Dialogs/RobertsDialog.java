package FF_1234_Pupkin_Init.Dialogs;

import FF_1234_Pupkin_Init.Instruments.Roberts;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobertsDialog  extends JPanel {
    final int MIN_VALUE = 10;
    final int MAX_VALUE = 255;
    final int MAX_VALUE_LENGTH = 3;
    Roberts roberts;
    public JFormattedTextField valueText;
    public RobertsDialog(Roberts roberts) {
        super();
        setLayout(new GridLayout(2, 1));
        JPanel paramsPanel = new JPanel(new GridLayout(1, 3));
        JPanel buttonPanel = new JPanel();
        this.roberts = roberts;
        TextAndSlider ts = TextAndSliderCreator.createDefault(MIN_VALUE, MAX_VALUE, MAX_VALUE_LENGTH, roberts.getBinParameter());
        paramsPanel.add(new Label("Binarization parameter:"));
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
                roberts.setBinParameter(Integer.parseInt(valueText.getText()));
                frame.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = (JDialog)p.getRootPane().getParent();
                valueText.setText(String.valueOf(roberts.getBinParameter()));
                frame.dispose();
            }
        });
        add(paramsPanel);
        add(buttonPanel);
    }
}
