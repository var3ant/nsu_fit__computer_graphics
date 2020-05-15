package Borzov;

import Borzov.view.ViewFunc;
import Borzov.view.ViewLegend;

import javax.swing.*;
import java.awt.*;

public class InitView extends JPanel {
    int n = 50;
    int m = 50;
    ViewFunc viewFunc;
    ViewLegend legend;
    public InitView() {
        setLayout(new GridBagLayout());

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
        legend = new ViewLegend();
        viewFunc = new ViewFunc(n, m, legend);
        add(viewFunc, constraints1);
        add(legend,constraints2);
        setVisible(true);
    }

    public boolean toggleGridMode() {
        return viewFunc.toggleGridMode();
    }

    public void setNM(int n, int m) {
        this.n=n;
        this.m=m;
        viewFunc.setNM(n,m);
    }

    public void setABCD(double a, double b, double c, double d) {
        viewFunc.setABCD(a,b,c,d);
    }

    public void setIsolines(int k, int[][] rgb) {
        viewFunc.setIsolines(k,rgb);
        legend.setIsolines(k,rgb);
        legend.setMinMax(viewFunc.getMin(),viewFunc.getMax());
    }
    public boolean toggleIsolineMode() {
        return viewFunc.toggleIsolineMode();
    }

    public boolean toggleColorMap() {
        return viewFunc.toggleColorMap();
    }
    public void setColorMap(boolean isColorMap) {
        viewFunc.setColorMap(isColorMap);
    }

    public double[] getABCD() {
        return viewFunc.getABCD();
    }

    public int[] getNM() {
        return viewFunc.getNM();
    }

    public int getK() {
        return viewFunc.getK();
    }

    public int[][] getRGBs() {
        return viewFunc.getRGBs();
    }
}
    /**
     * Performs actual component drawing
     *
     * @param g Graphics object to draw component to
     */
