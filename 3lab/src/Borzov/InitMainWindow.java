package Borzov;

import Borzov.view.ParametersDialog;
import ru.nsu.cg.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InitMainWindow extends MainFrame {
    private final InitView view;
    private final String FILE_PATH = "parameters.txt";
    private final JToggleButton grid;
    private final JRadioButtonMenuItem mGrid;

    //private final JToggleButton isoline;
    //private final JRadioButtonMenuItem mIsoline;

    private final JToggleButton colorMap;
    private final JRadioButtonMenuItem mColorMap;

    private final JToggleButton gradient;
    private final JRadioButtonMenuItem mGradient;

    private final AbstractButton file;
    private final JMenuItem mFile;
    private final JMenuItem mSettings;
    private final AbstractButton settings;
    private String aboutText;
    public InitMainWindow() {
        super(640, 480, "Init application");
        setMinimumSize(new Dimension(640, 480));
        try {
            aboutText = new String(Files.readAllBytes(Paths.get("readme.txt")), StandardCharsets.UTF_8);
        } catch (IOException e) {
            aboutText = "error";
        }
        try {
            addSubMenu("File", KeyEvent.VK_F);
            addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "Exit.gif", "onExit", false);

            addSubMenu("Help", KeyEvent.VK_H);
            addMenuItem("Help/About...", "Shows program version and copyright information", KeyEvent.VK_A, "Info.png", "onAbout", false);

            addSubMenu("Settings", KeyEvent.VK_F);
            mFile = (JMenuItem) addMenuItem("File/Open", "open file settings", KeyEvent.VK_X, "File.png", "onLoad", false);
            mGradient = (JRadioButtonMenuItem) addMenuItem("Settings/Gradient", "set gradient mode", KeyEvent.VK_X, "Gradient.png", "onGradient", true);
            //mIsoline = (JRadioButtonMenuItem) addMenuItem("Settings/Isoline", "show grid", KeyEvent.VK_X, "Info.png", "onIsoline", true);
            mColorMap = (JRadioButtonMenuItem) addMenuItem("Settings/Color map", "set color map mode", KeyEvent.VK_X, "ColorMap.png", "onColorMap", true);
            mGrid = (JRadioButtonMenuItem) addMenuItem("Settings/Grid", "show grid", KeyEvent.VK_X, "Grid.png", "onGrid", true);
            mSettings = (JMenuItem) addMenuItem("Settings/Settings", "open settings", KeyEvent.VK_X, "Settings.png", "onSettings", true);
            //addToolBarButton("File/Exit", false);
            file = addToolBarButton("File/Open", false);
            settings = addToolBarButton("Settings/Settings", false);
            addToolBarSeparator();
            //isoline = (JToggleButton) addToolBarButton("Settings/Isoline", true);
            colorMap = (JToggleButton) addToolBarButton("Settings/Color map", true);
            gradient = (JToggleButton) addToolBarButton("Settings/Gradient", true);
            addToolBarSeparator();
            grid = (JToggleButton) addToolBarButton("Settings/Grid", true);
            addToolBarButton("Help/About...", false);
            view = new InitView();
            add(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!readSettings(FILE_PATH)) {
            System.exit(0);
        }
        onGradient();
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

    public void onGrid() {
        boolean state = view.toggleGridMode();
        mGrid.setSelected(state);
        grid.setSelected(state);
    }

    public void onSettings() {
        JDialog dialog = new JDialog(this, "Set parameters", true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.add(new ParametersDialog(view));
        dialog.pack();
        dialog.setBounds((int) (screenSize.getWidth() / 2 - dialog.getWidth() / 2), (int) (screenSize.getHeight() / 2 - dialog.getHeight() / 2), dialog.getWidth(), dialog.getHeight());
        dialog.setVisible(true);
    }

    public void onLoad() {
        File file = getOpenFileName("3lab", "state");
        if (file == null) {
            return;
        }
        readSettings(file.getPath());
    }
    /*
    public void onFile() {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(new File(FILE_PATH));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    */
    /*public void onIsoline() {
        boolean state = view.toggleIsolineMode();
        //mIsoline.setSelected(state);
        //isoline.setSelected(state);
    }*/
    public void onColorMap() {
        view.setColorMap(true);
        mGradient.setSelected(false);
        gradient.setSelected(false);
        mColorMap.setSelected(true);
        colorMap.setSelected(true);
    }

    public void onGradient() {
        view.setColorMap(false);
        mColorMap.setSelected(false);
        colorMap.setSelected(false);
        mGradient.setSelected(true);
        gradient.setSelected(true);
    }

    /**
     * File/Exit - exits application
     */
    public void onExit() {
        System.exit(0);
    }

    boolean readSettings(String filePath){
        String[] errors = new Parser(view).pars(filePath);
        StringBuilder errorToPrint = new StringBuilder();
        if (errors.length != 0) {
            for (String error : errors) {
                errorToPrint.append(error);
                errorToPrint.append(System.getProperty("line.separator"));
            }
            JOptionPane.showMessageDialog(this, errorToPrint.toString());
            return false;
        }
        return true;
    }
}
