package borzov17210;

import borzov17210.view.InitView;
import borzov17210.view.view3dscene.View3d;
import ru.nsu.cg.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InitMainWindow extends MainFrame {
	InitView view;
	View3d view3d;
	String aboutText;

	public InitMainWindow() {
		super(600, 400, "Init application");
		try {
			aboutText = Files.readString(Paths.get("help.txt"));
		} catch (IOException e) {
			aboutText = "error";
		}
		try {
			addSubMenu("File", KeyEvent.VK_F);
			addMenuItem("File/Save", "", KeyEvent.VK_X, "save.png", "onSave");
			addMenuItem("File/Load", "", KeyEvent.VK_A, "open.png", "onLoad");
			addSubMenu("Scene", KeyEvent.VK_H);
			addSubMenu("About", KeyEvent.VK_H);
			addMenuItem("Scene/Spline", "open spline menu", KeyEvent.VK_A, "spline.png", "onParameters");
			addMenuItem("Scene/Reset", "reset angles", KeyEvent.VK_A, "reset.png", "onReset");
			addMenuItem("About/About", "about program", KeyEvent.VK_A, "About.png", "onAbout");
			addToolBarButton("File/Save");
			addToolBarButton("File/Load");
			addToolBarSeparator();
			addToolBarButton("Scene/Reset");
			addToolBarButton("Scene/Spline");
			addToolBarSeparator();
			addToolBarButton("About/About");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		State state = defaultState();
		State stateFromFile = loadState(new File("default.4lab"));
		if (stateFromFile != null) {
			state = stateFromFile;
		}
		view3d = new View3d(state);
		view = new InitView(this, view3d, state);
		view3d.setState(view.getState());
		add(view3d);
		onParameters();
		pack();
	}


	public static void main(String[] args) {
		InitMainWindow mainFrame = new InitMainWindow();
		mainFrame.setVisible(true);
	}

	/**
	 * Help/About... - shows program version and copyright information
	 */
	public State loadState(File file) {
		FileInputStream fis = null;
		ObjectInputStream oin = null;
		State state = null;
		try {
			fis = new FileInputStream(file);
			oin = new ObjectInputStream(fis);
			state = (State) oin.readObject();
		} catch (IOException | ClassNotFoundException e) {
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (oin != null) {
					oin.close();
				}
			} catch (IOException e) {
				System.err.println("error closing file '" + file.getName() + "'");
			}
		}
		return state;
	}

	public void saveState(State state, File file) throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		fos = new FileOutputStream(file);
		oos = new ObjectOutputStream(fos);
		oos.writeObject(state);
		oos.flush();
		oos.close();
	}

	public void onLoad() {
		File file = getOpenFileName("4lab", "state");
		if (file == null) {
			return;
		}
		view.setState(loadState(file));
		view3d.setState(view.getState());
	}

	public void onParameters() {
		JDialog dialog = new JDialog(this, "Set parameters", true);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				view.OnClose();
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		view = new InitView(this, view3d, view.getState());
		dialog.add(view);//view.getParametersPanel());
		dialog.pack();
		dialog.setBounds((int) (screenSize.getWidth() / 2.0 - dialog.getWidth() / 2.0), (int) (screenSize.getHeight() / 2.0 - dialog.getHeight() / 2.0), dialog.getWidth(), dialog.getHeight());
		dialog.setResizable(false);
		dialog.setVisible(true);
	}

	/**
	 * File/Exit - exits application
	 */
	public State defaultState() {
		return State.createDefault();
	}

	public void onAbout() {
		JOptionPane.showMessageDialog(this, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
	}

	public void onSave() {
		File file = getSaveFileName("4lab", "state");
		if (file == null) {
			return;
		}
		try {
			saveState(view.getState(), file);
		} catch (IOException e) {
			System.err.println("error while saving");
		}
		remove(view);
	}

	public void onReset() {
		view3d.resetAngles();
	}
}
