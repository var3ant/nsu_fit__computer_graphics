package FF_1234_Pupkin_Init;

import FF_1234_Pupkin_Init.Instruments.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Component which draws two diagonal lines and reacts on mouse clicks
 *
 * @author Vasya Pupkin
 */
public class InitView extends JPanel {
    private int shift = 4;
    private BufferedImage imageToPaint;
    private BufferedImage imageWithEffect;
    private BufferedImage originalImage;
    private Instrument instrument;
    private Gamma gamma = new Gamma();
    private Grey grey = new Grey();
    private Scaler scaler = new Scaler();
    private Inversion inversion = new Inversion();
    private Blur blur = new Blur();
    private Sharp sharp = new Sharp();
    private Emboss emboss = new Emboss();
    private OrderedDither orderedDither = new OrderedDither();
    private Aquarelle aquarelle = new Aquarelle();
    private FloydDither floydDither = new FloydDither();
    private Sobel sobel = new Sobel();
    private Roberts roberts = new Roberts();
    private Rotate rotate = new Rotate();
    private Otsu otsu = new Otsu();
    private int width = 800;
    private int height = 600;
    private boolean isInEffect = false;
    private JLabel label;
    /**
     * Constructs object
     */
    public InitView() {
        instrument = inversion;
        imageWithEffect = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                useInstrument();
            }
        });
    }

    /**
     * Performs actual component drawing
     *
     * @param g Graphics object to draw component to
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float[] dash1 = {2f, 0f, 2f};
        BasicStroke bs1 = new BasicStroke(1,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND,
                1.0f,
                dash1,
                2f);
        g2d.setStroke(bs1);
        if (imageToPaint != null) {
            g2d.drawRect(shift, shift, imageToPaint.getWidth() + 1, imageToPaint.getHeight() + 1);
            //g.drawImage(imageToPaint, shift + 1, shift + 1, null);
			/*
			double scale = 1;
            double scaleW = 1;
            double scaleH = 1;
            if(w > width) {
                scaleW = (double)width/ imageWithEffect.getWidth();
            }
            if(h > height){
                scaleH = (double)height/ imageWithEffect.getHeight();
            }
            scale = Math.min(scaleW,scaleH);
            width = width*scale;
            height = height*scale;
            g.drawImage(imageToPaint,shift + 1,shift + 1,width - 2 * shift, height - 2 * shift,null);
			}*///другой вариант изменения размера
            g.drawImage(imageToPaint, shift + 1, shift + 1, null);
        } else {
            g2d.drawRect(shift, shift, getWidth() - 2 * shift, getHeight() - 2 * shift);
        }
    }

    private void openError() {
        JOptionPane.showMessageDialog(this, "file open error",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    public void open(File file) throws IOException {
        BufferedImage newImage = null;
        if (file == null) {
            return;
        }
        try {
            newImage = ImageIO.read(file);
        } catch (IOException e) {
            openError();
            return;
        }
        if (newImage == null) {
            openError();
            return;
        }
        isInEffect = false;
        originalImage = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        originalImage.getGraphics().drawImage(newImage, 0, 0, null);//FIXME: теперь возможно не нужно копировать изображение. Проверю
        setImage();
    }
    public void setImage(){
        width = getWidth() - 2 * shift - 2;
        height = getHeight() - 2 * shift - 2;
        paintImage();
        if(!scaler.getFitToScreen() && originalImage!=null) {
            setPreferredSize(new Dimension(originalImage.getWidth() + 2*shift + 1, originalImage.getHeight() + 2*shift + 1));
        } else {
            //setPreferredSize(null);
        }
    }
    public void inShadesOfGrey() {
        isInEffect = false;
        instrument = grey;
    }

    public void doInversion() {
        isInEffect = false;
        instrument = inversion;
    }

    public void doGamma() {
        isInEffect = false;
        instrument = gamma;
    }

    public void doFloydDither() {
        isInEffect = false;
        instrument = floydDither;
    }
    void paintImage() {
        if (isInEffect) {
            imageToPaint = scaler.scale(imageWithEffect, width, height);
        } else {
            if (originalImage == null) {
                return;
            }
            imageToPaint = scaler.scale(originalImage, width, height);
        }
        repaint();
    }
    public void useInstrument() {
        if(originalImage != null) {
            if(!isInEffect) {
                imageWithEffect = instrument.doWork(originalImage);
            }
            isInEffect = !isInEffect;
            paintImage();
        }
    }

    public void doBlur() {
        isInEffect = false;
        instrument = blur;
    }

    public void doSharp() {
        isInEffect = false;
        instrument = sharp;
    }

    public void doEmboss() {
        isInEffect = false;
        instrument = emboss;
    }

    public void doOrderedDither() {
        isInEffect = false;
        instrument = orderedDither;
    }

    public void doAquarelle() {
        isInEffect = false;
        instrument = aquarelle;
    }

    public void doSobel() {
        isInEffect = false;
        instrument = sobel;
    }
    public void doOtsu() {
        isInEffect = false;
        instrument = otsu;
    }
    public void doRoberts() {
        isInEffect = false;
        instrument = roberts;
    }
    public void doRotate() {
        isInEffect = false;
        instrument = rotate;
    }
    public JPanel getParametersPanel() {
        return instrument.getParameterDialog();
    }
    public void setImageSize() {
        if(scaler.getFitToScreen() && originalImage != null) {
            this.width = getWidth() - 2*shift - 2;
            this.height = getHeight() - 2*shift - 2;
            paintImage();
        }
    }

    public void setFitToScreen(boolean isFitToScreenMode) {
        scaler.setFitToScreen(isFitToScreenMode);
        setImage();
    }

    public void showFitDialog() {
        JOptionPane.showConfirmDialog(null, scaler.getParameterDialog(), "Set parameters", JOptionPane.DEFAULT_OPTION);
    }

    public void applyChanges() {
        if(isInEffect & imageWithEffect != originalImage) {
            originalImage = imageWithEffect;
        }
        isInEffect = false;
    }

    public void save(File image) throws IOException {
        ImageIO.write(imageWithEffect, "png" , image);
    }
}
