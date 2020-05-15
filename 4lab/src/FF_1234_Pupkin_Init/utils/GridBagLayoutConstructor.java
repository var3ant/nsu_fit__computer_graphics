package FF_1234_Pupkin_Init.utils;

import javax.swing.*;
import java.awt.*;

public class GridBagLayoutConstructor {
    private final JPanel panel;
    private final GridBagLayout layout;
    private int xCursor = 0;
    private int yCursor = 0;

    public GridBagLayoutConstructor(JPanel panel, GridBagLayout layout) {
        this.panel = panel;
        this.layout = layout;
    }

    public GridBagConstraints getConstr(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        getConstr(x, y, c);
        return c;
    }

    public GridBagConstraints getConstr(int x, int y, GridBagConstraints c) {
        //c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 5, 5, 0);
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    public void addOnRow(Component comp, int ipadx) {
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = ipadx;
        addOnRow(comp, c);
    }

    public void addOnRow(Component comp) {
        panel.add(comp, getConstr(xCursor++, yCursor));
    }

    public void addOnRow(Component comp, GridBagConstraints c) {
        panel.add(comp, getConstr(xCursor++, yCursor, c));
    }

    public void nextRow() {
        xCursor = 0;
        yCursor += 1;
    }

    public void addOnRow(Component comp, int gridwidth, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.weightx = 0;
        c.fill = fill;
        panel.add(comp, getConstr(xCursor++, yCursor, c));
    }

    public void spec(Component comp) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        //gbc.weightx = 1.0;
        //gbc.weighty = 1.0;
        addOnRow(comp, gbc);
    }

    public void remove(Component addNewCreator) {
        layout.removeLayoutComponent(addNewCreator);
    }
}
