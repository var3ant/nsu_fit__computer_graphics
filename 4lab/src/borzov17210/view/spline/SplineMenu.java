package borzov17210.view.spline;

import borzov17210.State;
import borzov17210.utils.GridBagLayoutConstructor;
import borzov17210.view.InitView;
import borzov17210.view.view3dscene.View3d;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplineMenu extends JPanel {
    private final SplineView splineView;
    private State state;
    private InitView view;
    private GridBagLayoutConstructor constructor;
    private JSpinner nText;
    private JSpinner mText;
    private JSpinner numberText;
    private JSpinner circleNText;
    private JSpinner xText;
    private JSpinner yText;
    private JSpinner circleMSpinner;
    private View3d view3d;
    private JButton autoScaleBtn = new JButton("auto scale");
    private JButton applyBtn = new JButton("apply");
    private JButton cancelBtn = new JButton("cancel");
    private JButton plusBtn;
    private JButton minusBtn;
    private State newState;
    private boolean defaultclose = false;
    private ChangeListener circleMlistener;
    public SplineMenu(View3d view3d, SplineView splineView, State state) {
        setPreferredSize(new Dimension(1200, 50));
        this.view3d = view3d;
        this.state = state;
        newState = new State(state);
        this.splineView = splineView;
        SplineMenu t = this;
        splineView.setState(newState);
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        constructor = new GridBagLayoutConstructor(this, layout);
        constructor.addOnRow(new JLabel("n"));
        nText = createSpinner(state.getN(), 2, 100);
        nText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                t.newState.setN(((int) (nText.getValue())));
                splineView.repaint();
            }
        });
        constructor.addOnRow(nText);
        constructor.addOnRow(new JLabel("m"));
        mText = createSpinner(state.getM(), 1, 100);
        constructor.addOnRow(mText);
        mText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                t.newState.setM(((int) (mText.getValue())));
                splineView.repaint();
            }
        });
        constructor.addOnRow(new JLabel("circle n"));
        circleNText = createSpinner(state.getCircleN(), 1, Integer.MAX_VALUE);
        constructor.addOnRow(circleNText);

        constructor.addOnRow(new JLabel("circle m"));
        circleMSpinner = createMSpinner(state.getCircleM(), 2, state.getPoints().size());
        constructor.addOnRow(circleMSpinner, 30);
        constructor.addOnRow(new JLabel("x"));
        xText = createDoubleSpinner(splineView.getSelectedX(), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        xText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                splineView.setX((Double) xText.getValue());
            }
        });
        constructor.addOnRow(xText);
        constructor.addOnRow(new JLabel("y"));
        yText = createDoubleSpinner(splineView.getSelectedY(), Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        yText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                splineView.setY((Double) yText.getValue());
            }
        });
        constructor.addOnRow(yText);
        constructor.addOnRow(new JLabel("point"));
        numberText = createSpinner(splineView.getSelectedNumber(), 0, Integer.MAX_VALUE);
        constructor.addOnRow(numberText);
        numberText.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (t.newState.getPoints().size() == 0) {
                    numberText.setValue("");
                    return;
                }
                if ((int) (numberText.getValue()) >= t.newState.getPoints().size()) {
                    numberText.setValue(t.newState.getPoints().size() - 1);
                }
                splineView.setSelectedPoint((int) (numberText.getValue()));
            }
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyParams();
                JDialog frame = (JDialog) t.getRootPane().getParent();
                defaultclose = true;
                frame.dispose();
            }
        });
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SplineMenu.this.splineView.setState(t.state);
                resetParams();
                JDialog frame = (JDialog) t.getRootPane().getParent();
                defaultclose = true;
                frame.dispose();
            }
        });
        autoScaleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splineView.calcBestScale();
            }
        });
        Icon minus = new ImageIcon("src/borzov17210/resources/minus.png");
        Icon plus = new ImageIcon("src/borzov17210/resources/plus.png");
        plusBtn = new JButton(plus);
        minusBtn = new JButton(minus);
        minusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splineView.removeSelectedPoint();
                int maxCount = newState.getPoints().size();
                //changeSpinner(circleMSpinner,Math.min((int)circleMSpinner.getValue(),maxCount),2,maxCount);
            }
        });
        plusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splineView.addPointAfterSelected();
                //changeSpinner(circleMSpinner,(int)circleMSpinner.getValue(),2,newState.getPoints().size());
            }
        });
        constructor.addOnRow(plusBtn);
        constructor.addOnRow(minusBtn);
        constructor.addOnRow(autoScaleBtn);
        constructor.addOnRow(applyBtn);
        constructor.addOnRow(cancelBtn);
    }

    private JSpinner createDoubleSpinner(double defaultValue, double min, double max, double step) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, step));
        spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.0##E0"));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((double) spinner.getValue() < min) {
                    spinner.setValue(min);
                } else if ((double) spinner.getValue() > max) {
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
                if ((int) spinner.getValue() < min) {
                    spinner.setValue(min);
                } else if ((int) spinner.getValue() > max) {
                    spinner.setValue(max);
                }
            }
        });
        spinner.setValue(defaultValue);
        return spinner;
    }

    private JSpinner createMSpinner(int defaultValue, int min, int max) {
        if (defaultValue < min || defaultValue > max) {
            defaultValue = 2;
        }
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, 1));
        circleMlistener = e -> {
            if ((int) spinner.getValue() < min) {
                spinner.setValue(min);
            } else if ((int) spinner.getValue() > max) {
                spinner.setValue(max);
            }
        };
        spinner.addChangeListener(circleMlistener);
        spinner.setValue(defaultValue);
        return spinner;
    }

    private void changeSpinner(JSpinner spinner, int defaultValue, int min, int max) {
        spinner.setModel(new SpinnerNumberModel(defaultValue, min, max, 1));

        /*for (ChangeListener listener: spinner.getChangeListeners()) {
            spinner.removeChangeListener(listener);
        }*/
        spinner.removeChangeListener(circleMlistener);
        circleMlistener = e -> {
            if ((int) spinner.getValue() < min) {
                spinner.setValue(min);
            } else if ((int) spinner.getValue() > max) {
                spinner.setValue(max);
            }
        };
        spinner.addChangeListener(circleMlistener);
        spinner.setValue(defaultValue);
    }

    private void applyParams() {
        newState.setCircleM((Integer) circleMSpinner.getValue());
        newState.setCircleN((Integer) circleNText.getValue());
        state = newState;
        newState = new State(state);
        view3d.setState(state);
        splineView.setState(state);
    }

    private void resetParams() {
        splineView.setState(state);
        newState = new State(state);
        view3d.setState(state);
        nText.setValue(state.getN());
        mText.setValue(state.getM());
        circleNText.setValue(state.getCircleN());
        circleMSpinner.setValue(state.getCircleM());
        numberText.setValue(0);
    }

    public void setSelectedPoint(int number) {
        numberText.setValue(number);
    }

    public void setX(double x) {
        xText.setValue(x);
    }

    public void setY(double y) {
        yText.setValue(y);
    }

    public void setState(State state) {
        this.state = state;
        resetParams();
    }

    public void onClose() {
        if (!defaultclose) {
            resetParams();
        }
    }

    public void setCountPoints(int maxCount) {
        changeSpinner(circleMSpinner, Math.min((int) circleMSpinner.getValue(), maxCount), 2, maxCount);
    }
}
