package Borzov.view;

import Borzov.InitView;
import Borzov.Parser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ParametersDialog extends JPanel {
    private final InitView view;
    private final JTextField abcdField;
    private final JTextField nmField;
    private final JTextField kTextField;
    private final JTextPane colorsPane;

    public ParametersDialog(InitView view) {
        this.view = view;
        abcdField = new JTextField();
        nmField = new JTextField();
        kTextField = new JTextField();
        colorsPane = new JTextPane();
        JScrollPane jsp = new JScrollPane(colorsPane);
        abcdField.setBorder(new LineBorder(Color.gray));
        nmField.setBorder(new LineBorder(Color.gray));
        kTextField.setBorder(new LineBorder(Color.gray));
        jsp.setBorder(new LineBorder(Color.gray));
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(10, 10, 0, 10));
        add(abcdField, createFieldConstraints(1, 0));
        add(new JLabel("a, b, c, d "), createLabelConstraints(0, 0));
        add(new JLabel("N M"), createLabelConstraints(0, 1));
        add(nmField, createFieldConstraints(1, 1));
        add(new JLabel("K"), createLabelConstraints(0, 2));
        add(kTextField, createFieldConstraints(1, 2));
        add(new JLabel("R G B"), createLabelConstraints(0, 3));
        add(jsp, createFieldConstraints(1, 3, 0.8, 0.8));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("CANCEL");
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 5;
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = 5;
        c2.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ok, c1);
        add(cancel, c2);
        JPanel p = this;
        double abcd[] = view.getABCD();
        abcdField.setText(abcd[0] + ", " + abcd[1] + ", " + abcd[2] + ", " + abcd[3]);
        int nm[] = view.getNM();
        nmField.setText(nm[0] + " " + nm[1]);
        kTextField.setText(Integer.toString(view.getK()));
        int[][] rgbs = view.getRGBs();
        StringBuilder rgbStrBuilder = new StringBuilder();
        for (int i = 0; i < rgbs.length; i++) {
            rgbStrBuilder.append(rgbs[i][0] + " " + rgbs[i][1] + " " + rgbs[i][2] + System.getProperty("line.separator"));
        }
        colorsPane.setText(rgbStrBuilder.toString());
        ok.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            Parser parser = new Parser(view);
            String[] errors = parser.pars(nmField.getText(), abcdField.getText(), kTextField.getText(), colorsPane.getText());
            if (errors.length != 0) {
                StringBuilder errorToPrint = new StringBuilder();
                for (String error : errors) {
                    errorToPrint.append(error);
                    errorToPrint.append(System.getProperty("line.separator"));
                }
                JOptionPane.showMessageDialog(this, errorToPrint.toString());
                return;
            }
            frame.dispose();
        });
        cancel.addActionListener(e -> {
            JDialog frame = (JDialog) p.getRootPane().getParent();
            frame.dispose();
        });
        setPreferredSize(new Dimension(250, 300));
    }

    private GridBagConstraints createFieldConstraints(int x, int y) {
        GridBagConstraints c =
                new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth =
                GridBagConstraints.REMAINDER;
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(0, 5, 5, 0);
        return c;
    }

    private GridBagConstraints createFieldConstraints(int x, int y, double w, double h) {
        GridBagConstraints c = createFieldConstraints(x, y);
        c.weightx = w;
        c.weighty = h;
        return c;
    }

    private GridBagConstraints createLabelConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        return c;
    }

    private GridBagConstraints createButtonConstraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        return c;
    }
}