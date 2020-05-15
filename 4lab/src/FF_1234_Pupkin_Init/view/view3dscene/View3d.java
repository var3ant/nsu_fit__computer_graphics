package FF_1234_Pupkin_Init.view.view3dscene;

import FF_1234_Pupkin_Init.matrix.Matrix;
import FF_1234_Pupkin_Init.matrix.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class View3d extends JPanel {
        List<Point> points;
        List<List<Matrix>> wires;
        Matrix RoxFirst;
        Matrix Rox;
        Matrix Roz;
        Matrix Rsum;
        int prevX = -1;
        int prevY = -1;
        double zoom = 1000;
        int m = 6;
        double angleX = 100;
        double angleZ = 225;
        int countCirclePoints = 10;//FIXME:из предыдущего
        int pointsBetweenCircles=5;
        public View3d(List<Point> points) {
            this.setPreferredSize(new Dimension(600, 600));
            this.points = points;
            wires = new ArrayList<>();
            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (e.getUnitsToScroll() > 0) {
                        zoom = zoom * 2;
                    } else if (e.getUnitsToScroll() < 0) {
                        zoom = zoom / 2;
                    }
                    //System.out.println(transf.getScale());
                    repaint();
                }
            });

            MouseListener ml = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //prevX = -1;
                    //prevY = -1;
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            };
            addMouseListener(ml);

            MouseMotionListener mml = new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    angleZ = (angleZ - (e.getX() - prevX)) % 360;
                    angleX = (angleX - (e.getY() - prevY)) % 360;
                    prevX = e.getX();
                    prevY = e.getY();
                    calcAngles();
                    repaint();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            };
            addMouseMotionListener(mml);
            resetAngles();
        }

        private void resetAngles() {
            angleX = 100;
            angleZ = 225;
            RoxFirst = Matrix.getRx(0);
            calcAngles();
        }

        private void calcAngles() {
            Rox = Matrix.getRx(Math.toRadians(angleX));
            Roz = Matrix.getRz(Math.toRadians(angleZ));
            Rsum = Rox.mul(Roz).mul(RoxFirst);
        }

        private Matrix getTransfMatrix() {
            Matrix cameraBasis = new Matrix(4, 4, new double[]{
                    1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, zoom,
                    0, 0, 0, 1
            });
            double Sw = 8;
            double Sh = 8;
            double zf = 100.5;
            double zb = 15;
            Matrix Mpsp = Matrix.getMproj(Sw, Sh, zf, zb);
            return Mpsp.mul(cameraBasis).mul(Rsum);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            Graphics gi = image.getGraphics();
            gi.setColor(Color.MAGENTA);
            Matrix T = getTransfMatrix();
            double degBetweenWires = 360.0 / m;
            for (int j = 0; j < m; j++) {
                double deg = j * degBetweenWires;
                Matrix Rz = Matrix.getRz(Math.toRadians(deg));
                Matrix R = T.mul(Rz);
                List<Matrix> curPoints = new ArrayList<>();
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    Matrix result1 = R.mul(new Matrix(4, 1, new double[]{0, p1.getY(), p1.getX(), 1}));
                    Matrix result2 = R.mul(new Matrix(4, 1, new double[]{0, p2.getY(), p2.getX(), 1}));
                    int x1 = (int) (result1.getX() / result1.getZ());
                    int y1 = (int) (result1.getY() / result1.getZ());
                    int x2 = (int) (result2.getX() / result2.getZ());
                    int y2 = (int) (result2.getY() / result2.getZ());
                    //System.out.println("x1 " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2);
                    gi.drawLine(x1 + getWidth() / 2, y1 + getHeight() / 2, x2 + getWidth() / 2, y2 + getHeight() / 2);
                }
                /*double degBetweenPoints = 360.0/(countCirclePoints*m);
                List<List<Point>> circles =  new ArrayList<>();
                for(int i=-1;i< points.size();i+=pointsBetweenCircles) {
                    circles.add(new ArrayList<>());
                }*/
            }
            //TODO:кружки
            g.drawImage(image, 0, 0, null);
        }
        private void addCircle() {

        }

    public void setPointsToDraw(List<Point> splinePoints) {
            this.points = splinePoints;
            wires = new ArrayList<>();
            resetAngles();
            repaint();
    }

    public void setM(int m) {
            this.m=m;
    }

    public int getM() {
        return m;
    }
}