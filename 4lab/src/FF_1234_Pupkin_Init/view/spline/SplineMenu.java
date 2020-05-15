package FF_1234_Pupkin_Init.view.spline;

import FF_1234_Pupkin_Init.utils.GridBagLayoutConstructor;
import FF_1234_Pupkin_Init.view.InitView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplineMenu extends JPanel {
    private final SplineView splineView;
    InitView view;
    GridBagLayoutConstructor constructor;
    JSpinner nText;
    JSpinner mText;
    JSpinner kText;
    JSpinner numberText;
    JSpinner circleNText;
    JSpinner xText;
    JSpinner yText;
    Button applyBtn = new Button("apply");
    Button cancelBtn = new Button("cancel");
    public SplineMenu(InitView view, SplineView splineView) {
        this.splineView=splineView;
        this.view=view;
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        constructor = new GridBagLayoutConstructor(this, layout);
        constructor.addOnRow(new JLabel("n"));
        nText=createSpinner(10,2,100);
        constructor.addOnRow(nText);
        constructor.addOnRow(new JLabel("m"));
        mText=createSpinner(10,1,100);
        constructor.addOnRow(mText);
        constructor.addOnRow(new JLabel("k"));
        kText=createSpinner(1,1,Integer.MAX_VALUE);
        constructor.addOnRow(kText);
        constructor.addOnRow(new JLabel("Number"));
        numberText=createSpinner(1,1,Integer.MAX_VALUE);
        constructor.addOnRow(numberText);
        numberText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                splineView.setSelectedPoint((int)(numberText.getValue()));
            }
        });
        constructor.addOnRow(new JLabel("circle n"));
        circleNText=createSpinner(1,1,Integer.MAX_VALUE);
        constructor.addOnRow(circleNText);
        constructor.addOnRow(new JLabel("x"));
        xText=createDoubleSpinner(1,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
        xText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                splineView.setX((Double) xText.getValue());
            }
        });
        constructor.addOnRow(xText);
        constructor.addOnRow(new JLabel("y"));
        yText=createDoubleSpinner(1,Integer.MIN_VALUE,Integer.MAX_VALUE,1);
        yText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                splineView.setY((Double) yText.getValue());
            }
        });
        constructor.addOnRow(yText);
        SplineMenu t=this;//FIX
        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyParams();
                JDialog frame = (JDialog)t.getRootPane().getParent();
                frame.dispose();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //FIXME:вернуть всё назад
                JDialog frame = (JDialog)t.getRootPane().getParent();
                frame.dispose();
            }
        });
        constructor.addOnRow(applyBtn);
        //constructor.addOnRow(cancelBtn);
    }
    private JSpinner createDoubleSpinner(double defaultValue, double min, double max, double step) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, step));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.0##E0"));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((double)spinner.getValue() < min) {
                    spinner.setValue(min);
                } else if((double)spinner.getValue() > max) {
                    spinner.setValue(max);
                }
            }
        });
        spinner.setValue(defaultValue);
        return spinner;
    }
    private JSpinner createSpinner(int defaultValue, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, 1));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)spinner.getValue() < min) {
                    spinner.setValue(min);
                } else if((int)spinner.getValue() > max) {
                    spinner.setValue(max);
                }
            }
        });
        spinner.setValue(defaultValue);
        return spinner;
    }
    private void applyParams() {
        splineView.setN((Integer) nText.getValue());
        view.setM((Integer) mText.getValue());
        /*view.setX((Double) xText.getValue());
        view.setY((Double) yText.getValue());
        view.setK((Integer) kText.getValue());
        view.setPointNumber((Integer) numberText.getValue());
        view.setCircleN((Integer) circleNText.getValue());*/
    }

    private void resetParams() {
        nText.setValue(view.getN());
        mText.setValue(view.getM());
        numberText.setValue(0);
        /*xText.setValue(view.getPointX());
        yText.setValue(view.getPointY());
        kText.setValue(view.getK());
        circleNText.setValue(view.getCircleN());*/
    }

    public void setSelectedPoint(int number) {
        System.out.println("selected:" + number);
        numberText.setValue(number);
    }

    public void setX(double x) {
        xText.setValue(x);
    }
    public void setY(double y) {
        yText.setValue(y);
    }
}
