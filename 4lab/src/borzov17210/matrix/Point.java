package borzov17210.matrix;

public class Point {
    private double x,y;
    private int size;
    public Point(double x,double y, int size) {
        this.x=x;
        this.y=y;
        this.size=size;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}
