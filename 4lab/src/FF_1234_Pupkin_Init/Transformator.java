package FF_1234_Pupkin_Init;

public class Transformator {
    private int x0,y0;
    private double scale;
    Transformator() {
        scale=1;
        x0=0;
        y0=0;
    }
    public Transformator(int x, int y, double scale) {
        this.scale=scale;
        x0=x;
        y0=y;
    }
    public double xAToB(int x) {
        return (x-x0)/scale;
    }
    public double yAToB(int y) {
        return  (y-y0)/scale;
    }
    public int xBToA(double x) {
        return (int)Math.round(x*scale + x0);
    }
    public int yBToA(double y) {
        return (int) Math.round(y*scale + y0);
    }
    public void setScale(double scale) {
        if(scale<=0) {
            return;
        }
        this.scale=scale;
    }
    public double getScale(){
        return scale;
    }
    int scaleAtoB(int x) {
        return (int)(x/scale);
    }
    int scaleBtoA(int x) {
        return (int)(x*scale);
    }
}
