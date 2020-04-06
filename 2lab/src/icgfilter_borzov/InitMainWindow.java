package icgfilter_borzov;

import icgfilter_borzov.InitView.EInstrument;
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
 * @author Alexander Borzov
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
    private JToggleButton blur;
    private JToggleButton roberts;
    private JToggleButton inversion;
    private JToggleButton sobel;
    private JToggleButton emboss;
    private JToggleButton gamma;
    private JToggleButton ordered;
    private JToggleButton aqua;
    private JToggleButton floyd;
    private JToggleButton grey;
    private JToggleButton otsu;
    private JToggleButton sharp;
    private JToggleButton rotate;
    private ButtonGroup effectButtons;
    private ButtonGroup effectMenu;
    private JRadioButtonMenuItem mblur;
    private JRadioButtonMenuItem mroberts;
    private JRadioButtonMenuItem minversion;
    private JRadioButtonMenuItem msobel;
    private JRadioButtonMenuItem memboss;
    private JRadioButtonMenuItem mgamma;
    private JRadioButtonMenuItem mordered;
    private JRadioButtonMenuItem maqua;
    private JRadioButtonMenuItem mfloyd;
    private JRadioButtonMenuItem mgrey;
    private JRadioButtonMenuItem motsu;
    private JRadioButtonMenuItem msharp;
    private JRadioButtonMenuItem mrotate;
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
            effectButtons = new ButtonGroup();
            effectMenu = new ButtonGroup();
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open image", KeyEvent.VK_X, "open.png", "onOpen", false);
            addMenuItem("File/Save", "Save image", KeyEvent.VK_X, "save.png", "onSave", false);
            addMenuItem("File/Apply", "apply changes", KeyEvent.VK_X, "apply.png", "onApply", false);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit", false);
            addSubMenu("Filter", KeyEvent.VK_F);
            minversion = (JRadioButtonMenuItem) addMenuItem("Filter/Inversion", "Inversion filter", KeyEvent.VK_X, "invert.png", "onInverison", true);
            effectMenu.add(minversion);
            mblur = (JRadioButtonMenuItem) addMenuItem("Filter/Blur", "Blur filter", KeyEvent.VK_X, "blur.png", "onBlur", true);
            effectMenu.add(mblur);
            mroberts = (JRadioButtonMenuItem) addMenuItem("Filter/Roberts", "Roberts filter", KeyEvent.VK_X, "roberts.png", "onRoberts", true);
            effectMenu.add(mroberts);
            msobel = (JRadioButtonMenuItem) addMenuItem("Filter/Sobel", "Sobel filter ", KeyEvent.VK_X, "sobel.png", "onSobel", true);
            effectMenu.add(msobel);
            memboss = (JRadioButtonMenuItem) addMenuItem("Filter/Emboss", "Emboss filter", KeyEvent.VK_X, "emboss.png", "onEmboss", true);
            effectMenu.add(memboss);
            mgamma = (JRadioButtonMenuItem) addMenuItem("Filter/Gamma", "Gamma correction", KeyEvent.VK_X, "gamma.png", "onGamma", true);
            effectMenu.add(mgamma);
            mordered = (JRadioButtonMenuItem) addMenuItem("Filter/OrderedDither", "Ordered dither", KeyEvent.VK_X, "ordered.png", "onOrderedDither", true);
            effectMenu.add(mordered);
            maqua = (JRadioButtonMenuItem) addMenuItem("Filter/Aquarelle", "Aquarelle filter", KeyEvent.VK_X, "aquarelle.png", "onAquarelle", true);
            effectMenu.add(maqua);
            mfloyd = (JRadioButtonMenuItem) addMenuItem("Filter/FloydDither", "Floyd dither", KeyEvent.VK_X, "floyd.png", "onFloydDither", true);
            effectMenu.add(mfloyd);
            mgrey = (JRadioButtonMenuItem) addMenuItem("Filter/Grey", "Shades of gray", KeyEvent.VK_X, "grey.png", "onGrey", true);
            effectMenu.add(mgrey);
            motsu = (JRadioButtonMenuItem) addMenuItem("Filter/Otsu", "change settings of display mode", KeyEvent.VK_X, "otsu.png", "onOtsu", true);
            effectMenu.add(motsu);
            msharp = (JRadioButtonMenuItem) addMenuItem("Filter/Sharp", "Sharp filter", KeyEvent.VK_X, "sharp.png", "onSharp", true);
            effectMenu.add(msharp);
            addSubMenu("Parameters", KeyEvent.VK_H);
            addMenuItem("Parameters/Parameters", "open parameters dialog", KeyEvent.VK_X, "settings.png", "onParameters", false);
            mrotate = (JRadioButtonMenuItem) addMenuItem("Parameters/Rotate", "Rotate image", KeyEvent.VK_X, "rotate.png", "onRotate", true);
            effectMenu.add(mrotate);
            fitToScreenMenuItem = (JRadioButtonMenuItem) addMenuItem("Parameters/FitToScreen", "change image display mode", KeyEvent.VK_X, "resize.png", "onFitToScreen", true);
            addMenuItem("Parameters/FitSettings", "change settings of display mode", KeyEvent.VK_X, "FitSettings.png", "onFitSettings", false);
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "about.png", "onAbout", false);
            addToolBarButton("File/Open", true);
            addToolBarButton("File/Save", false);
            addToolBarButton("File/Apply", false);
            addToolBarSeparator();
            sharp = (JToggleButton) addToolBarButton("Filter/Sharp", true);
            effectButtons.add(sharp);
            blur = (JToggleButton) addToolBarButton("Filter/Blur", true);
            effectButtons.add(blur);
            emboss = (JToggleButton) addToolBarButton("Filter/Emboss", true);
            effectButtons.add(emboss);
            aqua = (JToggleButton) addToolBarButton("Filter/Aquarelle", true);
            effectButtons.add(aqua);
            addToolBarSeparator();
            inversion = (JToggleButton) addToolBarButton("Filter/Inversion", true);
            effectButtons.add(inversion);
            grey = (JToggleButton) addToolBarButton("Filter/Grey", true);
            effectButtons.add(grey);
            gamma = (JToggleButton) addToolBarButton("Filter/Gamma", true);
            effectButtons.add(gamma);
            addToolBarSeparator();
            roberts = (JToggleButton) addToolBarButton("Filter/Roberts", true);
            effectButtons.add(roberts);
            sobel = (JToggleButton) addToolBarButton("Filter/Sobel", true);
            effectButtons.add(sobel);
            otsu = (JToggleButton) addToolBarButton("Filter/Otsu", true);
            effectButtons.add(otsu);
            addToolBarSeparator();
            ordered = (JToggleButton) addToolBarButton("Filter/OrderedDither", true);
            effectButtons.add(ordered);
            floyd = (JToggleButton) addToolBarButton("Filter/FloydDither", true);
            effectButtons.add(floyd);
            addToolBarSeparator();
            rotate = (JToggleButton) addToolBarButton("Parameters/Rotate", true);
            effectButtons.add(rotate);
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
            e.printStackTrace();
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
        selectInstrument(gamma,mgamma, EInstrument.GAMMA);
    }

    public void onInverison() {
        selectInstrument(inversion,minversion, EInstrument.INVERSION);
    }
    private void selectInstrument(JToggleButton b, JRadioButtonMenuItem m, EInstrument instrument) {
        effectButtons.clearSelection();
        b.setSelected(true);
        m.setSelected(true);
        if(b.isSelected()) {
            view.setInstrument(instrument);
        } else {
            view.setInstrument(EInstrument.NONE);
        }
        onParameters();
        view.useInstrument();
    }
    public void onOtsu() {
        selectInstrument(otsu,motsu, EInstrument.OTSU);
    }

    public void onEmboss() {
        selectInstrument(emboss,memboss, EInstrument.EMBOSS);
    }

    public void onOrderedDither() {
        selectInstrument(ordered,mordered, EInstrument.ORDERED);
    }

    public void onGrey() {
        selectInstrument(grey,mgrey, EInstrument.GREY);
    }

    public void onBlur() {
        selectInstrument(blur,mblur, EInstrument.BLUR);
    }

    public void onSharp() {
        selectInstrument(sharp,msharp, EInstrument.SHARP);
    }

    public void onFloydDither() {
        selectInstrument(floyd,mfloyd, EInstrument.FLOYD);
    }

    public void onAquarelle() {
        selectInstrument(aqua,maqua, EInstrument.AQUARELLE);
    }

    public void onRoberts() {
        selectInstrument(roberts,mroberts, EInstrument.ROBERTS);
    }

    public void onSobel() {
        selectInstrument(sobel,msobel, EInstrument.SOBEL);
    }
    public void onRotate() {
        selectInstrument(rotate,mrotate, EInstrument.ROTATE);
    }
    public void onOpen() {
        File image = FileUtils.getOpenImageFileName(this);
        view.open(image);
        if (scrollPane != null) {
            scrollPane.revalidate();
        }

    }

    public void onParameters() {
        JPanel p = view.getParametersPanel();
        System.out.println(p);
        if(p != null) {
            JDialog dialog = new JDialog(this,"Set parameters",true);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            dialog.add(view.getParametersPanel());
            dialog.pack();
            dialog.setBounds((int)(screenSize.getWidth()/2 - dialog.getWidth()/2),(int)(screenSize.getHeight()/2 - dialog.getHeight()/2),dialog.getWidth(),dialog.getHeight());
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
