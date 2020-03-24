package FF_1234_Pupkin_Init.instruments;

import FF_1234_Pupkin_Init.InitView;
import FF_1234_Pupkin_Init.dialogs.ParametersDialog;
import FF_1234_Pupkin_Init.dialogs.ShapeParametersDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawShape extends MouseAdapter implements Instrument{
    InitView view;
    int size=1;
    int radius=15;
    int vertexCount=5;
    int color = Color.BLACK.getRGB();
    int angle = 0;
    boolean isStar=false;
    ShapeParametersDialog paramsDialog;
    DrawShape(InitView _view) {
        view = _view;
        paramsDialog = new ShapeParametersDialog(this);
    }

    @Override
    public ParametersDialog getParameterDialog() {
        return paramsDialog;
    }

    @Override
    public void setColor(int _color) {
        color = _color;
    }

    public void setStar(boolean _isStar) {
        isStar = _isStar;
    }

    public void setSize(int _size) {
        size = _size;
    }

    public void setRadius(int _radius) {
        radius = _radius;
    }

    public void setAngle(int _angle) {
        angle = _angle;
    }
    public void setVertexCount(int _vertexCount) {
        vertexCount = _vertexCount;
    }
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == 1) {
            if(isStar) {
                System.out.println("star vertex: " + vertexCount + " radius: " + radius + " color: " + size);
                view.drawStar(e.getX(),e.getY(),vertexCount, 0, radius, size, color, angle);
                return;
            }
            System.out.println("shape vertex: " + vertexCount + " radius: " + radius + " color: " + size);
            view.drawShape(e.getX(),e.getY(),vertexCount, radius, size, color, angle);
        }
    }

    public int getRadius() {
        return radius;
    }
    public int getVertexCount() {
        return  vertexCount;
    }
    public int getAngle() {
        return angle;
    }
    public boolean getIsStar() {
        return isStar;
    }
}