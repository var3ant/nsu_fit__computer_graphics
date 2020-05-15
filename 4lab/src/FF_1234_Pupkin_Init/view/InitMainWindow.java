package FF_1234_Pupkin_Init;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import FF_1234_Pupkin_Init.matrix.Point;
import FF_1234_Pupkin_Init.view.InitView;
import FF_1234_Pupkin_Init.view.view3dscene.View3d;
import ru.nsu.cg.MainFrame;

import javax.swing.*;

/**
 * Main window class
 * @author Vasya Pupkin
 */
public class InitMainWindow extends MainFrame {
	/**
	 * Default constructor to create main window
	 */
	InitView view;
	View3d view3d;
	public InitMainWindow()
	{
		super(600, 400, "Init application");
		try
		{
			addSubMenu("File", KeyEvent.VK_F);
			addMenuItem("File/Save", "", KeyEvent.VK_X, "Exit.gif", "onSave");
			addMenuItem("File/Load", "", KeyEvent.VK_A, "About.gif", "onLoad");
			addSubMenu("Settings", KeyEvent.VK_H);
			addMenuItem("Settings/Parameters", "", KeyEvent.VK_A, "About.gif", "onParameters");
			addToolBarButton("Settings/Parameters");
			addToolBarButton("File/Save");
			addToolBarSeparator();
			addToolBarButton("File/Load");
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		view3d = new View3d(null);
		view = new InitView(this, view3d);
		view.resetTransformator();
		view3d.setPointsToDraw(view.calcPointsToDraw());
		onLoad();
		add(view3d);
		onParameters();
		pack();
	}

	/**
	 * Help/About... - shows program version and copyright information
	 */
	public void onLoad()
	{
		//JOptionPane.showMessageDialog(this, "Init, version 1.0\nCopyright © 2010 Vasya Pupkin, FF, group 1234", "About Init", JOptionPane.INFORMATION_MESSAGE);
		List<Point> points = new ArrayList<>();
		byte[] toRead;
		File file = new File("test");
		try {
			toRead= Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//System.out.println("\tfrom File:");
		for(int i=0;i<toRead.length/(Double.BYTES*2);i++) {
			double d1 = toDouble(toRead,Double.BYTES*2*i);
			double d2 = toDouble(toRead, Double.BYTES*2*i+Double.BYTES);
			points.add(new Point(d1,d2,view.getPointSize()));
			//System.out.println("d: " + d1 + " " + d2);
		}
		view.setPoints(points);
	}
	public void onParameters() {
		JDialog dialog = new JDialog(this,"Set parameters",true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		dialog.add((Component) view.getParametersPanel());
		dialog.pack();
		dialog.setBounds((int)(screenSize.getWidth()/2 - dialog.getWidth()/2),(int)(screenSize.getHeight()/2 - dialog.getHeight()/2),dialog.getWidth(),dialog.getHeight());
		dialog.setVisible(true);
		view3d.setPointsToDraw(view.getPointsToDraw());
	}
	/**
	 * File/Exit - exits application
	 */
	public void onSave()
	{
		remove(view);
		File file = new File("test");
		List<Point> points = view.getPoints();
		byte[] toWrite = new byte[points.size()*Double.BYTES*2];
		//System.out.println("\tfrom program:");
		for(int i=0;i<points.size();i++) {
			byte[] b1= toByteArray(points.get(i).getX());
			byte[] b2= toByteArray(points.get(i).getY());
			System.arraycopy(b1, 0, toWrite, i * Double.BYTES * 2, Double.BYTES);
			System.arraycopy(b2, 0, toWrite, i * Double.BYTES * 2 + Double.BYTES, Double.BYTES);
		}
		try (FileOutputStream fos = new FileOutputStream("test")) {
			fos.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static byte[] toByteArray(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}
	public static double toDouble(byte[] bytes, int startPos) {
		byte[] b = new byte[Double.BYTES];
		System.arraycopy(bytes, startPos, b, 0, b.length);
		return ByteBuffer.wrap(b).getDouble();
	}
	/**
	 * Application main entry point
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args)
	{
		InitMainWindow mainFrame = new InitMainWindow();
		mainFrame.setVisible(true);
	}
}
