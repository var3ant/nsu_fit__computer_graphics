package FF_1234_Pupkin_Init.dialogs;

import FF_1234_Pupkin_Init.instruments.DrawShape;
import FF_1234_Pupkin_Init.instruments.TextAndSlider;
import FF_1234_Pupkin_Init.instruments.TextAndSliderCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ShapeParametersDialog extends ParametersDialog {
    //Format amountFormat;
    final int MIN_ANGLE = 0;
    final int MAX_ANGLE = 360;
    final int MAX_ANGLE_LENGTH = 3;
    final int MAX_VERTEXES = 16;
    final int MIN_VERTEXES = 3;
    final int MAX_VERTEXES_LENGTH = 2;
    final int MIN_RADIUS = 1;
    final int MAX_RADIUS = 200;
    final int MAX_RADIUS_LENGTH = 4;
    //JFormattedTextField sizeField;
    //JFormattedTextField radiusField;
    //JFormattedTextField vertexCountField;
    //JFormattedTextField angleField;
    JCheckBox starMode;
    DrawShape drawShape;
    //int size;
    //int radius;
    //int vertexCount;
    //int angle;

    //ParamChangeListener p;
    public ShapeParametersDialog(DrawShape _drawShape) {
        setLayout(new GridLayout(4, 2));
        drawShape = _drawShape;
        /*amountFormat = NumberFormat.getIntegerInstance();
        p = new ParamChangeListener();
        sizeField = new JFormattedTextField(amountFormat);
        radiusField = new JFormattedTextField(amountFormat);
        vertexCountField = new JFormattedTextField(amountFormat);
        angleField = new JFormattedTextField(amountFormat);
        sizeField.setColumns(5);
        sizeField.addPropertyChangeListener("value", p);
        add(new JLabel("size:"));
        add(sizeField);

        radiusField.setColumns(5);
        radiusField.addPropertyChangeListener("value", p);
        add(new JLabel("radius:"));
        add(radiusField);

        vertexCountField.setColumns(5);
        vertexCountField.addPropertyChangeListener("value", p);
        add(new JLabel("vertexes:"));
        add(vertexCountField);

        angleField.setColumns(5);
        angleField.addPropertyChangeListener("value", p);
        add(new JLabel("angle:"));
        add(angleField);

        starMode = new JCheckBox("is star");
        starMode.setSelected(false);
        add(starMode);
        starMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                drawShape.setStar(e.getStateChange() == 1);
            }
        });
        angle = drawShape.getAngle();
        angleField.setText(String.valueOf(angle));
        vertexCount = drawShape.getVertexCount();
        vertexCountField.setText(String.valueOf(vertexCount));
        radius = drawShape.getRadius();
        radiusField.setText(String.valueOf(radius));
        starMode.setSelected(drawShape.getIsStar());*/
        TextAndSlider ts = TextAndSliderCreator.create(MIN_RADIUS, MAX_RADIUS, MAX_RADIUS_LENGTH, drawShape.getRadius(), value -> drawShape.setRadius(value));
        add(new Label("radius:"));
        add(ts.text);
        add(ts.slider);

        ts = TextAndSliderCreator.create(MIN_ANGLE, MAX_ANGLE, MAX_ANGLE_LENGTH, drawShape.getAngle(), value -> drawShape.setAngle(value));
        add(new Label("angle:"));
        add(ts.text);
        add(ts.slider);

        ts = TextAndSliderCreator.create(MIN_VERTEXES, MAX_VERTEXES, MAX_VERTEXES_LENGTH, drawShape.getVertexCount(), value -> drawShape.setVertexCount(value));
        add(new Label("vertexes:"));
        add(ts.text);
        add(ts.slider);
        starMode = new JCheckBox("is star");
        starMode.setSelected(false);
        add(starMode);
        starMode.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                drawShape.setStar(e.getStateChange() == 1);
            }
        });
    }

    /*class ParamChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            Object source = e.getSource();
            if (source == sizeField) {
                size = ((Number) sizeField.getValue()).intValue();
                if (size > 8) {
                    size = 8;
                }
                if (size < 0) {
                    size = 0;
                }
                sizeField.setText(Integer.toString(size));
                drawShape.setSize(size);
            } else if (source == radiusField) {
                radius = ((Number) radiusField.getValue()).intValue();
                drawShape.setRadius(radius);
            } else if (source == vertexCountField) {
                vertexCount = ((Number) vertexCountField.getValue()).intValue();
                drawShape.setVertexCount(vertexCount);
            } else if (source == angleField) {
                angle = ((Number) angleField.getValue()).intValue();
                drawShape.setAngle(angle);
            }
        }
    }*/
}