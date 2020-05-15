package FF_1234_Pupkin_Init.view.spline;

import FF_1234_Pupkin_Init.InitMainWindow;
import FF_1234_Pupkin_Init.Transformator;
import FF_1234_Pupkin_Init.matrix.Matrix;
import FF_1234_Pupkin_Init.matrix.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SplineView extends JPanel {
    /**
     * Constructs object
     */
    List<Point> points = new ArrayList<>();
    List<Point> subPoints = new ArrayList<>();
    SplineMenu menu;
    int pointSize = 16;
    int subPointSize = pointSize / 2;
    int selectedPoint;
    int movingPoint;
    InitMainWindow mw;
    Transformator transf;
    List<Point> pointsToDraw;
    int n = 10;
    int m;
    int k = 15;
    public SplineView(InitMainWindow mw) {
        this.mw=mw;
        setPreferredSize(new Dimension(800, 600));
        transf = new Transformator(getWidth()/2, getHeight()/2, 16);
        generateSubpoint();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                transf = new Transformator(getWidth()/2, getHeight()/2, transf.getScale());
                repaint();
            }
        });

        MouseMotionListener mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (movingPoint != -1) {
                    if (e.getX() < 0 || e.getX() > getWidth() - 1 || e.getY() < 0 || e.getY() > getHeight() - 1) {
                        return;
                    }
                    points.get(movingPoint).setX(transf.xAToB(e.getX()));
                    points.get(movingPoint).setY(transf.yAToB(e.getY()));
                    sendSelectedPointInfo();
                    generateSubpoint();
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (int i = 0; i < subPoints.size(); i++) {
                    Point point = subPoints.get(i);
                    int px = transf.xBToA(point.getX());
                    int py = transf.yBToA(point.getY());
                    int size = point.getSize() / 2;
                    if ((px > x - size && px < x + size) && (py > y - size && py < y + size)) {
                        createPoint(x, y, i + 1);
                        return;
                    }
                }
                if (e.getButton() == 3) {//FIXME: константу
                    for (int i = 0; i < points.size(); i++) {
                        Point point = points.get(i);
                        int px = transf.xBToA(point.getX());
                        int py = transf.yBToA(point.getY());
                        int size = point.getSize() / 2;
                        if ((px > x - size && px < x + size) && (py > y - size && py < y + size)) {
                            deletePoint(i);
                            return;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                findPoint(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                resetPoint();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        addMouseListener(ml);
        addMouseMotionListener(mml);
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getUnitsToScroll() > 0) {
                    transf.setScale(transf.getScale() / 2);
                } else if (e.getUnitsToScroll() < 0) {
                    transf.setScale(transf.getScale() * 2);
                }
                //System.out.println(transf.getScale());
                repaint();
            }
        });
    }
    public void setMenu(SplineMenu menu) {
        this.menu=menu;
    }
    private void deletePoint(int i) {
        points.remove(i);
        //переотправить всё о новой точке
        generateSubpoint();
        repaint();
    }

    private void createPoint(int x, int y, int num) {
        points.add(num, new Point(transf.xAToB(x), transf.yAToB(y), pointSize));
        generateSubpoint();
    }
    private void resetPoint() {
        movingPoint =-1;
        //menu.setSelectedPoint(-1);
    }
    private int findPoint(int x, int y) {
        int px;
        int py;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            px = transf.xBToA(point.getX());
            py = transf.yBToA(point.getY());
            if ((px > x - point.getSize() / 2 && px < x + point.getSize() / 2) && (py > y - point.getSize() / 2 && py < y + point.getSize() / 2)) {
                selectedPoint = i;
                movingPoint = i;
                menu.setSelectedPoint(selectedPoint);
                sendSelectedPointInfo();
                return i;
            }
        }
        movingPoint = -1;//FIXME:
        return -1;
    }

    /**
     * Performs actual component drawing
     *
     * @param g Graphics object to draw component to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        generateSubpoint();
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics gi = image.getGraphics();
        gi.setColor(Color.WHITE);
        gi.drawLine(0, transf.yBToA(0), getWidth(), transf.yBToA(0));
        gi.drawLine(transf.xBToA(0), 0, transf.xBToA(0), getHeight());
        gi.setColor(Color.WHITE);
        int riskSize=5;
        gi.drawLine(transf.xBToA(1), getHeight()/2 - riskSize, transf.xBToA(1), getHeight()/2 + riskSize);
        gi.drawLine(getWidth() / 2 - riskSize, transf.yBToA(1), getWidth() / 2 + riskSize, transf.yBToA(1));
        calcPointsToDraw();
        gi.setColor(Color.WHITE);
        for (int i = 0; i < pointsToDraw.size() - 1; i++) {
            Point point1 = pointsToDraw.get(i);
            Point point2 = pointsToDraw.get(i + 1);
            gi.drawLine(transf.xBToA(point1.getX()), transf.yBToA(point1.getY()), transf.xBToA(point2.getX()), transf.yBToA(point2.getY()));
        }
        gi.setColor(Color.GREEN);
        for (int i = 0; i < points.size() - 1; i++) {
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);
            gi.drawLine(transf.xBToA(point1.getX()), transf.yBToA(point1.getY()), transf.xBToA(point2.getX()), transf.yBToA(point2.getY()));
        }
        gi.setColor(Color.BLUE);
        for(int i=0;i<points.size();i++) {
            Point point = points.get(i);
            gi.fillRect(transf.xBToA(point.getX()) - pointSize / 2, transf.yBToA(point.getY()) - pointSize / 2, pointSize, pointSize);
        }

        for (Point point : subPoints) {
            gi.fillRect(transf.xBToA(point.getX()) - pointSize / 4, transf.yBToA(point.getY()) - pointSize / 4, pointSize / 2, pointSize / 2);
        }
        if(selectedPoint !=-1 && selectedPoint < points.size()) {
            Point selectedPoint = points.get(this.selectedPoint);
            gi.setColor(Color.CYAN);
            gi.fillRect(transf.xBToA(selectedPoint.getX()) - pointSize / 2, transf.yBToA(selectedPoint.getY()) - pointSize / 2, pointSize, pointSize);

        }
        gi.setColor(Color.GREEN);
        g.drawImage(image, 0, 0, null);
    }

    private void generateSubpoint() {
        subPoints = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Point point1 = points.get(i);
            Point point2 = points.get(i + 1);
            Point subPoint = new Point((point2.getX() - point1.getX()) / 2 + point1.getX(), (point2.getY() - point1.getY()) / 2 + point1.getY(), subPointSize);
            subPoints.add(subPoint);
        }
    }

    public void resetTransformator() {
        //transf = new Transformator(getWidth() / 2, getHeight() / 2, 16);
    }

    public List<Point> getPoints() {
        return points;
    }

    public int getPointSize() {
        return pointSize;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
        generateSubpoint();
        repaint();
    }
    public List<Point> calcPointsToDraw() {
        Matrix m = new Matrix(new double[][]{
                {-1, 3, -3, 1},
                {3, -6, 3, 0},
                {-3, 0, 3, 0},
                {1, 4, 1, 0}
        });
        /*List<Point>*/ pointsToDraw = new ArrayList<>();
        int K = points.size();
        for (int i = 3; i < K; ++i) {
            Matrix gx = new Matrix(4, 1, new double[]{
                    points.get(i - 3).getX(),
                    points.get(i - 2).getX(),
                    points.get(i - 1).getX(),
                    points.get(i).getX()
            });
            Matrix gy = new Matrix(4, 1, new double[]{
                    points.get(i - 3).getY(),
                    points.get(i - 2).getY(),
                    points.get(i - 1).getY(),
                    points.get(i).getY()
            });
            double dt = 1.0 / n;
            for (int j = 0; j < n + 1; ++j) {
                double t = j * dt;
                Matrix T = new Matrix(1, 4, new double[]{Math.pow(t, 3), Math.pow(t, 2), Math.pow(t, 1), 1.0});
                Matrix tm = T.mul(m);
                double x = tm.mul(gx).mul(1.0 / 6.0).getValue();
                double y = tm.mul(gy).mul(1.0 / 6.0).getValue();
                pointsToDraw.add(new Point(x, y, 0));
            }
        }
        return pointsToDraw;
    }
    public List<Point> getPointsToDraw() {
        return pointsToDraw;
    }

    public void setN(int n) {
        this.n=n;
        calcPointsToDraw();
        repaint();
    }

    public void setX(double x) {
        points.get(selectedPoint).setX(x);
        repaint();
    }

    public void setY(double y) {
        points.get(selectedPoint).setY(y);
        repaint();
    }

    public int getN() {
        return n;
    }

    public void setSelectedPoint(int value) {
        selectedPoint = value;
        sendSelectedPointInfo();
        repaint();
    }

    public void sendSelectedPointInfo() {
        menu.setX(points.get(selectedPoint).getX());
        menu.setY(points.get(selectedPoint).getY());
    }
}