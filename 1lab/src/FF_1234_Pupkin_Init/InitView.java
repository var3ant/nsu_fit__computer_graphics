package FF_1234_Pupkin_Init;

import FF_1234_Pupkin_Init.dialogs.ParametersDialog;
import FF_1234_Pupkin_Init.instruments.*;
import FF_1234_Pupkin_Init.myGraphics.Span;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * @author Vasya Pupkin
 */
public class InitView extends JPanel {
    private final ParametersDialog paramDialog;
    private BufferedImage im;
    private Point prev;
    private Point begin;
    private Point end;
    private Instrument instrument;
    private InstrumentFactory instrumentFactory;
    private int color;
    private int width = 800;
    private int height = 600;

    public InitView() {
        super();
        instrumentFactory = new InstrumentFactory(this);
        setPreferredSize(new Dimension(width, height));
        im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        color = Color.BLACK.getRGB();
        //instrument = instrumentFactory.getInstrument(EInstruments.LINE);
        //instrument.setColor(color);
        addMouseListener((MouseListener) instrument);
        paramDialog = new ParametersDialog();
    }

    void setColor(int _color) {
        color = _color;
        instrument.setColor(color);
    }

    void setInstrument(EInstruments eInstrument) {
        removeMouseListener((MouseListener) instrument);
        this.instrument = instrumentFactory.getInstrument(eInstrument);
        instrument.setColor(color);
        addMouseListener((MouseListener) instrument);

    }

    public void resizeImage(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().setColor(Color.WHITE);
        newImage.getGraphics().fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        newImage.getGraphics().drawImage(im, 0, 0, null);
        im = newImage;
    }

    public void setImageSize(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().setColor(Color.WHITE);
        newImage.getGraphics().fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        newImage.getGraphics().drawImage(im, 0, 0, null);
        im = newImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
        super.paintComponent(g2d);
        g2d.drawImage(im, 0, 0, null);
    }
    public void myFill(Point p, int color) {
        Stack<Span> stack = new Stack<>();
        if (p.x >= im.getWidth() | p.y >= im.getHeight() | p.x < 0 & p.y < 0) {
            return;
        }
        int seedColor = im.getRGB(p.x, p.y);
        if (seedColor == color) {
            return;
        }
        Span span = findSpan(p);
        stack.push(span);
        while (!stack.empty()) {
            span = stack.pop();
            myDrawLine(span.x1, span.y, span.x2, span.y, color);
            //im.getGraphics().drawLine(span.x1,span.y,span.x2,span.y);
            findNeighborSpans(span, stack, seedColor);
        }
        repaint();
    }

    void findNeighborSpans(Span span, Stack<Span> stack, int seedColor) {
        int i = span.x1;
        int y = span.y + 1;
        int finish = Math.min(span.x2, im.getWidth() - 1);
        Span newSpan;
        if (y < im.getHeight() && y >= 0) {
            while (i <= finish) {
                if (im.getRGB(i, y) == seedColor) {
                    newSpan = findSpan(new Point(i, y));
                    stack.push(newSpan);
                    //im.getGraphics().drawLine(span.x1,span.y,span.x2,span.y);
                    i = newSpan.x2 + 1;
                }
                i += 1;
            }
        }

        i = span.x1;
        y = span.y - 1;
        if (y < im.getHeight() && y >= 0) {
            while (i <= finish) {
                if (im.getRGB(i, y) == seedColor) {
                    newSpan = findSpan(new Point(i, y));
                    stack.push(newSpan);
                    //getGraphics().drawLine(span.x1,span.y,span.x2,span.y);
                    myDrawLine(span.x1, span.y, span.x2, span.y, seedColor);
                    i = newSpan.x2 + 1;
                }
                i += 1;
            }
        }
    }

    Span findSpan(Point p) {
        int y = p.y;
        int ix = p.x;
        Span span = new Span();
        span.y = y;
        int color = im.getRGB(ix, y);
        int finish = im.getWidth();
        while (ix < finish && im.getRGB(ix, y) == color) {
            ix++;
        }
        span.x2 = ix - 1;
        ix = p.x;
        while (ix >= 0 && im.getRGB(ix, y) == color) {
            ix--;
        }
        span.x1 = ix + 1;
        return span;
    }

    public void myDrawLine(int x1, int y1, int x2, int y2, int _color) {
        myDrawLine(x1, y1, x2, y2, _color, 1);
    }

    public void setParams() {
        if(instrument.getClass() == Fill.class) return;
        JOptionPane.showConfirmDialog(null, instrument.getParameterDialog(), "set Params", JOptionPane.DEFAULT_OPTION);
    }

    public void myDrawLine(int x1, int y1, int x2, int y2, int _color, int size) {
        if (size != 1) {
            Graphics2D g = (Graphics2D) im.getGraphics();
            g.setColor(new Color(_color));
            g.setStroke(new BasicStroke((float) size));
            g.drawLine(x1, y1, x2, y2);
            return;
        }
        int dx = x2 - x1;
        int ady = Math.abs(y2 - y1);
        boolean isInversion = ady > Math.abs(dx);
        if (x1 < im.getWidth() & y1 < im.getHeight() & y1 >= 0 & x1 >= 0) {
            im.setRGB(x1, y1, _color);
        }
        if (isInversion) {
            int tmp = x1;
            x1 = y1;
            y1 = tmp;
            tmp = x2;
            x2 = y2;
            y2 = tmp;
            dx = x2 - x1;
            ady = Math.abs(y2 - y1);
        }

        if (dx < 0) {
            int tmp = x1;
            x1 = x2;
            x2 = tmp;

            tmp = y1;
            y1 = y2;
            y2 = tmp;
            dx = x2 - x1;
        }

        int x = x1;
        int y = y1;
        int incy = y1 < y2 ? 1 : -1;
        int err = -dx;
        int maxX;
        int maxY;
        if (isInversion) {
            maxX = im.getHeight();
            maxY = im.getWidth();
        } else {
            maxX = im.getWidth();
            maxY = im.getHeight();
        }
        for (int i = 0; i < dx; ++i) {
            x += 1;
            err += 2 * ady;
            if (err > 0) {
                err -= 2 * dx;
                y += incy;
            }
            if (y >= 0 & y < maxY & x >= 0 & x < maxX) {
                if (isInversion) {
                    im.setRGB(y, x, _color);
                } else {
                    im.setRGB(x, y, _color);
                }
            }
        }
        //System.out.println("1: " + x1 + " - " + y1  + " c: " + im.getRGB(x1,y1) + " ; 2: " + x2 + " - " + y2 + " c: " + im.getRGB(x2,y2));
        repaint();
    }

    public void drawShape(int x, int y, int vertexes, int radius, int size, int color, int rotation) {
        double angle = Math.PI * 2 / vertexes;
        double rotationRad = rotation * Math.PI / 180;
        int[] xPoints = new int[vertexes];
        int[] yPoints = new int[vertexes];
        for (int i = 0; i < vertexes; i++) {
            xPoints[i] = (int) (x + Math.cos(-i * angle + angle / 2 + rotationRad) * radius);
            yPoints[i] = (int) (y + Math.sin(-i * angle + angle / 2 + rotationRad) * radius);
        }
        Graphics2D g = (Graphics2D) im.getGraphics();
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(size));
        //g.drawPolygon(xPoints,yPoints,vertexes);
        for (int i = 0; i < vertexes; ++i) {
            myDrawLine(xPoints[i], yPoints[i], xPoints[(i + 1) % vertexes], yPoints[(i + 1) % vertexes], color, size);
        }
        repaint();
    }

    public void drawStar(int x, int y, int vertexes, int inRadius, int outRadius, int size, int color, int rotation) {
        int starVertexes = vertexes * 2;
        double angle = Math.PI * 2 / starVertexes;

        double rotationRad = rotation * Math.PI / 180;
        int[] xPoints = new int[starVertexes];
        int[] yPoints = new int[starVertexes];
        int[] ioradius = new int[2];
        ioradius[0] = (int) (outRadius * Math.cos(Math.PI/vertexes*2)/ Math.cos(Math.PI/vertexes));
        if(vertexes < 5) {
            ioradius[0] = outRadius/3;

        }
        ioradius[1] = outRadius;
        for (int i = 0; i < starVertexes; i++) {
            xPoints[i] = (int) (x + Math.cos(-i * angle + angle / 2 + rotationRad) * ioradius[i % 2]);
            yPoints[i] = (int) (y + Math.sin(-i * angle + angle / 2 + rotationRad) * ioradius[i % 2]);
        }
        /*Graphics2D g = (Graphics2D) im.getGraphics();
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(size));
        g.drawPolygon(xPoints,yPoints,vertexes);
        repaint();*/
        for (int i = 0; i < starVertexes; ++i) {
            myDrawLine(xPoints[i], yPoints[i], xPoints[(i + 1) % starVertexes], yPoints[(i + 1) % starVertexes], color, size);
        }
        repaint();
    }

    public void clear() {
        BufferedImage newIm = new BufferedImage(im.getWidth(), im.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) newIm.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, im.getWidth(), im.getHeight());
        //g.drawImage(im,null,0,0);
        im = newIm;
        repaint();
    }
    private void openError() {
        JOptionPane.showMessageDialog(this, "file open error",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }
    public void open(File file) throws IOException {
        BufferedImage newImage = null;
        if(file == null) {
            return;
        }
        try{
            newImage = ImageIO.read(file);
        } catch (IOException e) {
            openError();
            return;
        }
        if(newImage == null) {
            openError();
            return;
        }
        im = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        im.getGraphics().drawImage(newImage,0,0,null);
        setPreferredSize(new Dimension(im.getWidth(), im.getHeight()));
        repaint();
    }
    public int getImageWidth(){
        return im.getWidth();
    }

    public int getImageHeight(){
        return im.getHeight();
    }
    public void save(File image) throws IOException {
        ImageIO.write(im, "png" , image);
    }
}
