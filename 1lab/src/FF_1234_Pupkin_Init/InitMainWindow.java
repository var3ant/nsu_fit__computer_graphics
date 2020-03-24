package FF_1234_Pupkin_Init;

import FF_1234_Pupkin_Init.dialogs.ResizePanel;
import FF_1234_Pupkin_Init.instruments.EInstruments;
import ru.nsu.cg.FileUtils;
import ru.nsu.cg.MainFrame;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private final InitView view;
    private final JColorChooser colorChooser;
    private final ButtonGroup instrumentsGroup;
    private final ButtonGroup instrumentsMenuGroup;
    private final JToggleButton fill;
    private final JToggleButton line;
    private final JToggleButton shape;
    private final JRadioButtonMenuItem fillMenu;
    private final JRadioButtonMenuItem lineMenu;
    private final JRadioButtonMenuItem shapeMenu;
    private final ResizePanel resizePanel;
    private JLabel statusbarlabel;
    private boolean onStartApplication = true;
    private JScrollPane scrollPane;
    private String aboutText;
    public InitMainWindow() {
        super(800, 600, "Init application");
        setPreferredSize(new Dimension(800, 600));
        configureStatusbar();
        colorChooser = new JColorChooser();
        view = new InitView();
        resizePanel = new ResizePanel(800, 600, view);
        try {
            aboutText = Files.readString(Paths.get("help.txt"));
        } catch (IOException e) {
            aboutText = "error";
            e.printStackTrace();
        }
        scrollPane = new JScrollPane(view);
        add(scrollPane);
        try {
            instrumentsMenuGroup = new ButtonGroup();
            instrumentsGroup = new ButtonGroup();
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Open", "Open image", statusbarlabel, KeyEvent.VK_X, "open.png", "onOpen");
            addMenuItem("File/Save", "Save image", statusbarlabel, KeyEvent.VK_X, "save.png", "onSave");
            addMenuItem("File/Exit", "Exit application", statusbarlabel, KeyEvent.VK_X, "exit.png", "onExit");
            addSubMenu("instruments", KeyEvent.VK_F);
            addSubMenu("other", KeyEvent.VK_F);
            addMenuItem("instruments/eraser", "clear all", statusbarlabel, KeyEvent.VK_A, "eraser.png", "onEraser");
            lineMenu = addMenuRadioMenuItem("instruments/line", "draw line", statusbarlabel, KeyEvent.VK_X, "line.png", "onDrawLine");
            fillMenu = addMenuRadioMenuItem("instruments/fill", "fill area by one color", statusbarlabel, KeyEvent.VK_X, "fill.png", "onFill");
            shapeMenu = addMenuRadioMenuItem("instruments/shape", "draw shape", statusbarlabel, KeyEvent.VK_X, "stamp.png", "onShape");
            lineMenu.setSelected(true);
            instrumentsMenuGroup.add(lineMenu);
            instrumentsMenuGroup.add(fillMenu);
            instrumentsMenuGroup.add(shapeMenu);
            addMenuItem("other/color", "choose color", statusbarlabel, KeyEvent.VK_X, "color.png", "onChooseColor");
            addMenuItem("other/parameters", "choose parameters", statusbarlabel, KeyEvent.VK_X, "settings.png", "onParams");
            addMenuItem("other/resize", "resize working area", statusbarlabel, KeyEvent.VK_X, "resize.png", "onResize");
            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", statusbarlabel, KeyEvent.VK_A, "About.png", "onAbout");
            //addToolBarButton("File/Exit");
            line = addToolBarRadioButton("instruments/line");
            instrumentsGroup.add(line);
            fill = addToolBarRadioButton("instruments/fill");
            instrumentsGroup.add(fill);
            shape = addToolBarRadioButton("instruments/shape");
            instrumentsGroup.add(shape);
            addToolBarButton("instruments/eraser");
            addToolBarButton("other/color");
            addToolBarButton("other/parameters");
            addToolBarButton("other/resize");
            addToolBarButton("Help/About...");
            onDrawLine();
            addToolBarSeparator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(!onStartApplication) {
                    return;
                }
                int newHeight = e.getComponent().getHeight();
                int newWidth = e.getComponent().getWidth();
                if(view.getImageHeight() < newHeight) {
                    view.setImageSize(view.getImageWidth(),newHeight);
                    view.setPreferredSize(new Dimension(view.getWidth(), newHeight));
                }
                if(view.getImageWidth() < newWidth) {
                    view.setImageSize(newWidth,view.getImageHeight());
                    view.setPreferredSize(new Dimension(newWidth, view.getHeight()));
                }
            }
        });
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

    private void configureStatusbar() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusbarlabel = new JLabel("");
        statusbarlabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusbarlabel);
    }

    /**
     * Help/About... - shows program version and copyright information
     */
    public void onAbout() {
        JOptionPane.showMessageDialog(this,aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit(0);
    }


    public void onEraser() {
        view.clear();
        System.out.println("eraser");
    }

    public void onDrawLine() {
        view.setInstrument(EInstruments.LINE);
        System.out.println("DrawLine");
        line.setSelected(true);
        lineMenu.setSelected(true);

    }

    public void onShape() {
        view.setInstrument(EInstruments.SHAPE);
        System.out.println("Shape");
        shape.setSelected(true);
        shapeMenu.setSelected(true);
    }

    public void onResize() {
        int oldWidth = view.getImageWidth();
        int oldHeight = view.getImageHeight();
        resizePanel.setHeightField(view.getImageHeight());
        resizePanel.setWidthField(view.getImageWidth());
        System.out.println("resize");
        JOptionPane.showConfirmDialog(this, resizePanel, "Set size", JOptionPane.DEFAULT_OPTION);
        if(oldWidth != resizePanel.getInputWidth() | oldHeight != resizePanel.getInputHeight()) {
            onStartApplication = false;
        }
        view.setPreferredSize(new Dimension(resizePanel.getInputWidth(), resizePanel.getInputHeight()));
        view.resizeImage(resizePanel.getInputWidth(), resizePanel.getInputHeight());
        repaint();
        view.revalidate();
    }

    public void onOpen() {
        File image = FileUtils.getOpenImageFileName(this);
        try {
            view.open(image);
            scrollPane.revalidate();
        } catch (IOException e) {
            System.out.println("open error");
        }
    }

    public void onSave() {
        File file = FileUtils.getSaveImageFileName(this);
        try {
            view.save(file);
        } catch (IOException e) {
            System.out.println("save error");
        }
    }

    public void onFill() {
        view.setInstrument(EInstruments.FILL);
        System.out.println("fill");
        fill.setSelected(true);
        fillMenu.setSelected(true);
    }

    public void onChooseColor() {
        JOptionPane.showConfirmDialog(null, colorChooser,
                "Choose color",
                JOptionPane.DEFAULT_OPTION);
        view.setColor(colorChooser.getColor().getRGB());
        System.out.println("chooseColor");
    }

    public void onParams() {
        view.setParams();
        System.out.println("params");
    }

    /**
     * Default constructor to create main window
     */
}
