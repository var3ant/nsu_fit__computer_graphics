package icgfilter_borzov.Dialogs;

import icgfilter_borzov.Instruments.Scaler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScalerDialog extends JPanel {
    Scaler scaler;
    public ScalerDialog(Scaler scaler) {
        this.scaler = scaler;
        String[] items = {
                "NEAREST NEIGHBOR",
                "BILINEAR",
                "BICUBIC"
        };
        JComboBox comboBox = new JComboBox(items);
        comboBox.setSelectedIndex(2);
        //XXX:тут какая-то подозрительная копипаста
        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox)e.getSource();
            String item = (String)box.getSelectedItem();
            switch (item) {
                case "BICUBIC":
                    scaler.setTransformType(Scaler.TransformType.BICUBIC);
                    break;
                case "BILINEAR":
                    scaler.setTransformType(Scaler.TransformType.BILINEAR);
                    break;
                case "NEAREST NEIGHBOR":
                    scaler.setTransformType(Scaler.TransformType.NEAREST_NEIGHBOR);
                    break;
            }
        };
        comboBox.addActionListener(actionListener);
        add(comboBox);
    }
}
