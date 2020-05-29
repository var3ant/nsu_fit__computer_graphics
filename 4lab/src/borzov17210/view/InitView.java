package borzov17210.view;

import borzov17210.InitMainWindow;
import borzov17210.State;
import borzov17210.view.spline.SplineMenu;
import borzov17210.view.spline.SplineView;
import borzov17210.view.view3dscene.View3d;

import javax.swing.*;
import java.awt.*;

public class InitView extends JPanel {
    InitMainWindow mw;
    SplineView view;
    SplineMenu menu;
    View3d view3d;

    public InitView(InitMainWindow mw, View3d view3d, State state) {
        this.mw = mw;
        this.view3d = view3d;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints1 =
                new GridBagConstraints();
        constraints1.fill = GridBagConstraints.BOTH;
        constraints1.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints1.weightx = 0.8f;
        constraints1.weighty = 0.8f;
        constraints1.gridy = 0;

        GridBagConstraints constraints2 =
                new GridBagConstraints();
        constraints2.fill = GridBagConstraints.BOTH;
        constraints2.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints2.weightx = 0.1f;
        constraints2.weighty = 0.1f;
        constraints2.gridy = 1;
        view = new SplineView(state);
        menu = new SplineMenu(view3d, view, state);
        view.setMenu(menu);
        add(view, constraints1);
        add(menu, constraints2);
        setVisible(true);
    }

    public JPanel getParametersPanel() {
        return this;
    }

    public State getState() {
        return view.getState();
    }

    public void setState(State state) {
        view.setState(state);
        menu.setState(view.getState());
        view3d.setState(view.getState());
    }

    public void OnClose() {
        menu.onClose();
    }
}