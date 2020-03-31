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
    private boolean isFitToScreenMode = false;
    private JPanel panelWithScroll;
    MouseAdapter ma;
    String aboutText;
    private JRadioButtonMenuItem fitToScreenMenuItem;
    private JToggleButton fitToScreenButton;
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
            addMenuItem("File/Open", "Open image", KeyEvent.VK_X, "open.png", "onOpen",false);
            addMenuItem("File/Save", "Save image", KeyEvent.VK_X, "save.png", "onSave",false);
            addMenuItem("File/Apply", "apply changes", KeyEvent.VK_X, "apply.png", "onApply",false);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit",false);
            addSubMenu("Filter", KeyEvent.VK_F);
            addMenuItem("Filter/Inversion", "Inversion filter", KeyEvent.VK_X, "invert.png", "onInverison",false);
            addMenuItem("Filter/Blur", "Blur filter", KeyEvent.VK_X, "blur.png", "onBlur",false);
            addMenuItem("Filter/Roberts", "Roberts filter", KeyEvent.VK_X, "roberts.png", "onRoberts",false);
            addMenuItem("Filter/Sobel", "Sobel filter ", KeyEvent.VK_X, "sobel.png", "onSobel",false);
            addMenuItem("Filter/Emboss", "Emboss filter", KeyEvent.VK_X, "emboss.png", "onEmboss",false);
            addMenuItem("Filter/Gamma", "Gamma correction", KeyEvent.VK_X, "gamma.png", "onGamma",false);
            addMenuItem("Filter/OrderedDither", "Ordered dither", KeyEvent.VK_X, "ordered.png", "onOrderedDither",false);
            addMenuItem("Filter/Aquarelle", "Aquarelle filter", KeyEvent.VK_X, "aquarelle.png", "onAquarelle",false);
            addMenuItem("Filter/FloydDither", "Floyd dither", KeyEvent.VK_X, "floyd.png", "onFloydDither",false);
            addMenuItem("Filter/Grey", "Shades of gray", KeyEvent.VK_X, "grey.png", "onGrey",false);
            addMenuItem("Filter/Otsu", "change settings of display mode", KeyEvent.VK_X, "otsu.png", "onOtsu",false);
            addMenuItem("Filter/Sharp", "Sharp filter", KeyEvent.VK_X, "sharp.png", "onSharp",false);
            addSubMenu("Parameters", KeyEvent.VK_H);
            addMenuItem("Parameters/Parameters", "open parameters dialog", KeyEvent.VK_X, "settings.png", "onParameters",false);
            addMenuItem("Parameters/Rotate", "Rotate image", KeyEvent.VK_X, "rotate.png", "onRotate",false);
            fitToScreenMenuItem = (JRadioButtonMenuItem) addMenuItem("Parameters/FitToScreen", "change image display mode", KeyEvent.VK_X, "resize.png", "onFitToScreen",true);
            addMenuItem("Parameters/FitSettings", "change settings of display mode", KeyEvent.VK_X, "FitSettings.png", "onFitSettings",false);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "about.png", "onAbout",false);
            addToolBarButton("File/Open", false);
            addToolBarButton("File/Save", false);
            addToolBarButton("File/Apply", false);
            addToolBarSeparator();
            addToolBarButton("Filter/Sharp", false);
            addToolBarButton("Filter/Blur", false);
            addToolBarButton("Filter/Emboss", false);
            addToolBarButton("Filter/Aquarelle", false);
            addToolBarSeparator();
            addToolBarButton("Filter/Inversion", false);
            addToolBarButton("Filter/Grey", false);
            addToolBarButton("Filter/Gamma", false);
            addToolBarSeparator();
            addToolBarButton("Filter/Roberts", false);
            addToolBarButton("Filter/Sobel", false);
            addToolBarButton("Filter/Otsu", false);
            addToolBarSeparator();
            addToolBarButton("Filter/OrderedDither", false);
            addToolBarButton("Filter/FloydDither", false);
            addToolBarSeparator();
            addToolBarButton("Parameters/Rotate", false);
            addToolBarButton("Parameters/Parameters", false);
            fitToScreenButton = (JToggleButton) addToolBarButton("Parameters/FitToScreen", true);
            fitToScreenButton.setSelected(isFitToScreenMode);
            addToolBarButton("Parameters/FitSettings", false);
            addToolBarSeparator();
            addToolBarButton("Help/About...", false);
            view = new InitView();
            panelWithScroll = new JPanel();
            panelWithScroll.setLayout(new BorderLayout());
            panelWithScroll.add(view);
            add(panelWithScroll);
            setFitToScreen(isFitToScreenMode);
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
        view.useInstrument();
    }

    public void onInverison() {
        view.doInversion();
        onParameters();
        view.useInstrument();
    }

    public void onOtsu() {
        view.doOtsu();
        onParameters();
        view.useInstrument();
    }

    public void onEmboss() {
        view.doEmboss();
        onParameters();
        view.useInstrument();
    }

    public void onOrderedDither() {
        view.doOrderedDither();
        onParameters();
        view.useInstrument();
    }

    public void onGrey() {
        view.inShadesOfGrey();
        onParameters();
        view.useInstrument();
    }

    public void onBlur() {
        view.doBlur();
        onParameters();
        view.useInstrument();
    }

    public void onSharp() {
        view.doSharp();
        onParameters();
        view.useInstrument();
    }

    public void onFloydDither() {
        view.doFloydDither();
        onParameters();
        view.useInstrument();
    }

    public void onAquarelle() {
        view.doAquarelle();
        onParameters();
        view.useInstrument();
    }

    public void onRoberts() {
        view.doRoberts();
        onParameters();
        view.useInstrument();
    }

    public void onSobel() {
        view.doSobel();
        onParameters();
        view.useInstrument();
    }
    public void onRotate() {
        view.doRotate();
        onParameters();
        view.useInstrument();
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
    public void onFitToScreen() {
        isFitToScreenMode = !isFitToScreenMode;
        setFitToScreen(isFitToScreenMode);

    }
    private void setFitToScreen(boolean isFitToScreenMode) {
        fitToScreenButton.setSelected(isFitToScreenMode);
        fitToScreenMenuItem.setSelected(isFitToScreenMode);
        if (isFitToScreenMode) {
            view.showFitDialog();
            removeScrolls();
        } else {
            addScrolls();
        }
        view.setFitToScreen(isFitToScreenMode);
        panelWithScroll.setPreferredSize(panelWithScroll.getSize());
        pack();
        view.setImage();
        panelWithScroll.setPreferredSize(null);
        repaint();

    }
    public void onFitSettings() {
        view.showFitDialog();
        if(isFitToScreenMode) {
            view.setImage();
        }
    }

    private void addScrolls() {
        panelWithScroll.remove(view);
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
        scrollPane.revalidate();
    }
    private void removeScrolls() {
        panelWithScroll.remove(scrollPane);
        view.setAutoscrolls(false);
        view.removeMouseListener(ma);
        view.removeMouseMotionListener(ma);
        panelWithScroll.add(view);
    }
}
