package FF_1234_Pupkin_Init.dialogs;

import FF_1234_Pupkin_Init.instruments.TextAndSlider;
import FF_1234_Pupkin_Init.instruments.TextAndSliderCreator;

import javax.swing.*;
import java.awt.*;

public class ResizePanel extends JPanel {
    private final int MIN_HEIGHT = 1;
    private final int MAX_HEIGHT = 10000;
    private final int MAX_HEIGHT_LENGTH = 5;
    private final int MIN_WIDTH = 1;
    private final int MAX_WIDTH = 10000;
    private final int MAX_WIDTH_LENGTH = 5;
    private int newWidth;
    private int newHeight;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JPanel view;

    public ResizePanel(int defaultWidth, int defaultHeight, JPanel _view) {
        super();
        view = _view;
        setLayout(new GridLayout(0, 2));
        TextAndSlider ts = TextAndSliderCreator.create(MIN_HEIGHT, MAX_HEIGHT, MAX_HEIGHT_LENGTH, defaultHeight, this::setHeightField);//FIXME:600
        add(new JLabel("Height: "));
        heightField = ts.text;
        add(heightField);

        ts = TextAndSliderCreator.create(MIN_WIDTH, MAX_WIDTH, MAX_WIDTH_LENGTH, defaultWidth, this::setWidthField);//FIXME:600
        add(new JLabel("Width: "));
        widthField = ts.text;
        add(widthField);
    }

    public int getInputWidth() {
        //return Integer.parseInt(widthField.getText());
        return newWidth;
    }

    public int getInputHeight() {
        //return Integer.parseInt(heightField.getText());
        return newHeight;
    }

    public void setWidthField(int width) {
        widthField.setText(String.valueOf(width));
        newWidth = width;

    }

    public void setHeightField(int height) {
        heightField.setText(String.valueOf(height));
        newHeight = height;
    }
}
