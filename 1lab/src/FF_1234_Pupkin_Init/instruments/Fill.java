package FF_1234_Pupkin_Init.instruments;

import FF_1234_Pupkin_Init.InitView;
import FF_1234_Pupkin_Init.dialogs.FillParametersDialog;
import FF_1234_Pupkin_Init.dialogs.ParametersDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Fill extends MouseAdapter implements Instrument {
    FillParametersDialog paramsDialog;
    InitView view;
    int color;
    public Fill(InitView _view) {
        view = _view;
        paramsDialog = new FillParametersDialog();
    }

    @Override
    public ParametersDialog getParameterDialog() {
        return paramsDialog;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        //System.out.println("fill");
        if (e.getButton() == 1) {
            view.myFill(e.getPoint(), color);
        }
    }

}