package FF_1234_Pupkin_Init;

import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main window class
 *
 * @author Vasya Pupkin
 */
public class InitMainWindow extends MainFrame {
    InitView view;
    private JScrollPane scrollPane;
    private boolean isFitToScreenMode = true;
    private JPanel panelWithScroll;
    MouseAdapter ma;
    String aboutText;
    /**
     * Default constructor to create main window
     */
    public InitMainWindow() {
        super(800, 600, "PhotoStore");
        try {
            aboutText = new String(Files.readAllBytes(Paths.get("help.txt")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            aboutText = "error";
        }
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open image", KeyEvent.VK_X, "open.png", "onOpen");
            addMenuItem("File/Save", "Save image", KeyEvent.VK_X, "save.png", "onSave");
            addMenuItem("File/Apply", "apply changes", KeyEvent.VK_X, "apply.png", "onApply");
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit");
            addSubMenu("Filter", KeyEvent.VK_F);
            addMenuItem("Filter/Inversion", "Inversion filter", KeyEvent.VK_X, "invert.png", "onInverison");
            addMenuItem("Filter/Blur", "Blur filter", KeyEvent.VK_X, "blur.png", "onBlur");
            addMenuItem("Filter/Roberts", "Roberts filter", KeyEvent.VK_X, "roberts.png", "onRoberts");
            addMenuItem("Filter/Sobel", "Sobel filter ", KeyEvent.VK_X, "sobel.png", "onSobel");
            addMenuItem("Filter/Emboss", "Emboss filter", KeyEvent.VK_X, "emboss.png", "onEmboss");
            addMenuItem("Filter/Gamma", "Gamma correction", KeyEvent.VK_X, "gamma.png", "onGamma");
            addMenuItem("Filter/OrderedDither", "Ordered dither", KeyEvent.VK_X, "ordered.png", "onOrderedDither");
            addMenuItem("Filter/Aquarelle", "Aquarelle filter", KeyEvent.VK_X, "aquarelle.png", "onAquarelle");
            addMenuItem("Filter/FloydDither", "Floyd dither", KeyEvent.VK_X, "floyd.png", "onFloydDither");
            addMenuItem("Filter/Grey", "Shades of gray", KeyEvent.VK_X, "grey.png", "onGrey");
            addMenuItem("Filter/Otsu", "change settings of display mode", KeyEvent.VK_X, "otsu.png", "onOtsu");
            addSubMenu("Parameters", KeyEvent.VK_H);
            addMenuItem("Parameters/Parameters", "open parameters dialog", KeyEvent.VK_X, "settings.png", "onParameters");
            addMenuItem("Parameters/Rotate", "Rotate image", KeyEvent.VK_X, "rotate.png", "onRotate");
            addMenuItem("Parameters/FitToScreen", "change image display mode", KeyEvent.VK_X, "resize.png", "onFitToScreen");
            addMenuItem("Parameters/FitSettings", "change settings of display mode", KeyEvent.VK_X, "FitSettings.png", "onFitSettings");
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "about.png", "onAbout");
            addToolBarButton("File/Open");
            addToolBarButton("File/Save");
            addToolBarButton("File/Apply");
            addToolBarSeparator();
            addToolBarButton("Filter/Blur");
            addToolBarButton("Filter/Emboss");
            addToolBarButton("Filter/Aquarelle");
            addToolBarSeparator();
            addToolBarButton("Filter/Inversion");
            addToolBarButton("Filter/Grey");
            addToolBarButton("Filter/Gamma");
            addToolBarSeparator();
            addToolBarButton("Filter/Roberts");
            addToolBarButton("Filter/Sobel");
            addToolBarButton("Filter/Otsu");
            addToolBarSeparator();
            addToolBarButton("Filter/OrderedDither");
            addToolBarButton("Filter/FloydDither");
            addToolBarSeparator();
            addToolBarButton("Parameters/Rotate");
            addToolBarButton("Parameters/Parameters");
            addToolBarButton("Parameters/FitToScreen");
            addToolBarButton("Parameters/FitSettings");
            addToolBarSeparator();
            addToolBarButton("Help/About...");
            view = new InitView();
            add(view);
            this.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    view.setImageSize();
                }
            });
        } catch (Exception e) {
            System.exit(1);
        }
        setMinimumSize(new Dimension(640, 480));
        pack();
    }

    /**
     * Application main entry point
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        InitMainWindow mainFrame = new InitMainWindow();
        mainFrame.setVisible(true);
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog(this, aboutText, "About Init", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit(0);
    }

    public void onSave() {
        File file = FileUtils.getSaveImageFileName(this);
        try {
            if(file != null) {
                view.save(file);
            } else {
                System.out.println("save error");
            }
        } catch (IOException e) {
            System.out.println("save error");
        }
    }
    public void onApply() {
        view.applyChanges();
    }
    public void onGamma() {
        view.doGamma();
        onParameters();
    }

    public void onInverison() {
        view.doInversion();
        onParameters();
    }

    public void onOtsu() {
        view.doOtsu();
        onParameters();
    }

    public void onEmboss() {
        view.doEmboss();
        onParameters();
    }

    public void onFitToScreen() {
        isFitToScreenMode = !isFitToScreenMode;
        if (isFitToScreenMode) {
            view.showFitDialog();
            removeScrolls();
        } else {
            addScrolls();
            scrollPane.repaint();
        }
        System.out.println(isFitToScreenMode);
        view.setFitToScreen(isFitToScreenMode);
        repaint();//ןנט גûחמגו ‎עמדמ לועמהא םו גûחûגאועסÿ paintComponent() view.
    }
    public void onFitSettings() {
        view.showFitDialog();
        if(isFitToScreenMode) {
            view.setImage();
        }
    }
    public void onOrderedDither() {
        view.doOrderedDither();
        onParameters();
    }

    public void onGrey() {
        view.inShadesOfGrey();
        onParameters();
    }

    public void onBlur() {
        view.doBlur();
        onParameters();
    }

    public void onContur() {
        view.doContur();
        onParameters();
    }

    public void onFloydDither() {
        view.doFloydDither();
        onParameters();
    }

    public void onAquarelle() {
        view.doAquarelle();
        onParameters();
    }

    public void onRoberts() {
        view.doRoberts();
        onParameters();
    }

    public void onSobel() {
        view.doSobel();
        onParameters();
    }
    public void onRotate() {
        view.doRotate();
        onParameters();
    }
    public void onOpen() {
        File image = FileUtils.getOpenImageFileName(this);
        try {
            view.open(image);
            if (scrollPane != null) {
                scrollPane.revalidate();
            }

        } catch (IOException e) {
            System.out.println("open error");
        }
    }

    public void onParameters() {
        JPanel p = view.getParametersPanel();
        System.out.println(p);
        if(p != null) {
            JDialog dialog = new JDialog(this,"Set parameters",true);
            dialog.add(view.getParametersPanel());
            dialog.pack();
            dialog.setVisible(true);
        }
    }
    private void addScrolls() {
        remove(view);
        panelWithScroll = new JPanel();
        add(panelWithScroll);
        panelWithScroll.setLayout(new BorderLayout());
        view.setAutoscrolls(true);
        scrollPane = new JScrollPane(view);
        panelWithScroll.add(scrollPane);
        ma = new MouseAdapter() {

            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, view);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle rect = viewPort.getViewRect();
                        rect.x += deltaX;
                        rect.y += deltaY;
                        view.scrollRectToVisible(rect);
                    }
                }
            }
        };
        view.addMouseListener(ma);
        view.addMouseMotionListener(ma);
    }
    private void removeScrolls() {
        remove(panelWithScroll);
        view.setAutoscrolls(false);
        view.removeMouseListener(ma);
        view.removeMouseMotionListener(ma);
        add(view);
    }
}
