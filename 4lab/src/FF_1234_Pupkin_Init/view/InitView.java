package FF_1234_Pupkin_Init.view;

import FF_1234_Pupkin_Init.InitMainWindow;
import FF_1234_Pupkin_Init.matrix.Point;
import FF_1234_Pupkin_Init.view.spline.SplineMenu;
import FF_1234_Pupkin_Init.view.spline.SplineView;
import FF_1234_Pupkin_Init.view.view3dscene.View3d;

import java.awt.*;
import java.util.List;
import javax.swing.JPanel;

public class InitView extends JPanel {
    /**
     * Constructs object
     */
    InitMainWindow mw;
    SplineView view;
    SplineMenu menu;
    View3d view3d;
    public InitView(InitMainWindow mw, View3d view3d) {
        this.mw = mw;
        this.view3d=view3d;
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(600,600));
        GridBagConstraints constraints1 =
                new GridBagConstraints();
        constraints1.fill = GridBagConstraints.BOTH;
        constraints1.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints1.weightx=0.8f;
        constraints1.weighty=0.8f;
        constraints1.gridy=0;

        GridBagConstraints constraints2 =
                new GridBagConstraints();
        constraints2.fill = GridBagConstraints.BOTH;
        constraints2.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints2.weightx=0.2f;
        constraints2.weighty=0.2f;
        constraints2.gridy=1;
        view = new SplineView(mw);
        menu = new SplineMenu(this,view);
        view.setMenu(menu);
        add(view, constraints1);
        add(menu,constraints2);
        setVisible(true);
    }

    public void resetTransformator() {
        view.resetTransformator();
    }

    public int getPointSize() {
        return view.getPointSize();
    }

    public void setPoints(List<Point> points) {
        view.setPoints(points);
    }

    public List<Point> getPointsToDraw() {
        return view.getPointsToDraw();
    }

    public List<Point> getPoints() {
        return view.getPoints();
    }

    public JPanel getParametersPanel() {
        return this;
    }

    public List<Point> calcPointsToDraw() {
        return view.calcPointsToDraw();
    }
    public void setM(int m) {
        view3d.setM(m);
    }


    public void setK(Integer value) {
    }

    public void setPointNumber(Integer value) {
    }

    public void setCircleN(Integer value) {
    }

    public int getN() {
        return view.getN();
    }

    public int getM() {
        return view3d.getM();
    }

    public void setSelectedPoint(int value) {
        menu.setSelectedPoint(value);
    }
}