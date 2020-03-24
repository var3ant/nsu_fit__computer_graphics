package FF_1234_Pupkin_Init.instruments;

import FF_1234_Pupkin_Init.InitView;
import FF_1234_Pupkin_Init.dialogs.LineParametersDialog;
import FF_1234_Pupkin_Init.dialogs.ParametersDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Line extends MouseAdapter implements Instrument {
    private Point begin;
    private Point end;
    private BufferedImage im;
    private InitView view;
    private int size;
    private int color;
    LineParametersDialog paramsDialog;
    public Line(InitView _view) {
        super();
        size = 1;
        paramsDialog = new LineParametersDialog(this);
        view = _view;
        color = Color.WHITE.getRGB();//FIXME: Сделать чтобы на этапе компиляции подставлялось число
    }

    public void setSize(int _size) {
        size = _size;
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
        //System.out.println("begin paint");
        if (e.getButton() == 1) {
            begin = e.getPoint();
        }
    }

    public void mouseReleased(MouseEvent e) {

        //System.out.println("end paint");
        super.mouseReleased(e);
        if (e.getButton() == 1) {
            end = e.getPoint();
            view.myDrawLine(begin.x, begin.y, end.x, end.y, color, size);
            e.getComponent().repaint();
        }
    }

    public int getSize() {
        return size;
    }
}
