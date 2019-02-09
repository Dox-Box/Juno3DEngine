package launch;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import core.App;

public class Launcher extends JFrame {

	/*
	 * authors : Jim. Dox.
	 */

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
	
	public static Launcher launcher;
	public static final String VERSION_STR = "Build 0.1.6";
	private static String selectedProg;
	private static boolean readyToStart;	
	



	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private static int sWIDTH;
	private static int sHEIGHT; 
	//private static int applicationWidth;
	//private static int applicationHeight;
	

	public static void main(String[] args) {
		
					GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
					sWIDTH = gd.getDisplayMode().getWidth();
					sHEIGHT = gd.getDisplayMode().getHeight();
					launcher = new Launcher();
					Launcher.setVisibility(true);

					//App.createInstance(sWIDTH, sHEIGHT, " ");
					readyToStart = true;

	}

	public Launcher() {
		init();

	}
	
	public void init() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		sWIDTH = (int) screenSize.getWidth();
		sHEIGHT = (int) screenSize.getHeight();
		int width = 200;
		int height = 400;
		
		frame = new JFrame();
		contentPane = new JPanel();
		frame.setUndecorated(true);

		frame.getContentPane().setBackground(new Color(.5f, .5f, .5f));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false); 																											/* TODO: add scaling if files exceed frame limit. */
		frame.getContentPane().setLayout(null);
		frame.setTitle(" ");
		frame.setFocusable(true);
		frame.setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setBounds(sWIDTH/2 - 100, sHEIGHT/4, width, height);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(true);
		frame.setVisible(false);
		
		JLabel ver = new JLabel(Launcher.VERSION_STR.substring(5));
		ver.setForeground(new Color(0.75f, 0.75f, 0.75f));
		ver.setFont(new Font("Courier", Font.PLAIN,10));
		ver.setBounds(0,  height - 85, 40, 150);
		frame.getContentPane().add(ver);

		JLabel title = new JLabel("");
		title.setIcon(new ImageIcon("res/splash.png"));
		title.setBounds(0, 0, width, height);
		frame.getContentPane().add(title);
		//frame.setVisible(true);
	}

	public JFrame getJFrame() {
		return this.frame;
	}
	
	

	public File[] getRunnableFiles() {
		File folder = new File("res/scripts/");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (!file.getName().contains(".orbt")) {
				file = null;
			}
		}

		return files;
	}

	public static void setVisibility(boolean flag) {
		launcher.getJFrame().setVisible(flag);
	}

	/* starts app once selected by user */
	public static void setProg(String filepath) {
		selectedProg = filepath;
		readyToStart = true;
		if (filepath != null) {
			//App.createInstance(applicationWidth, applicationHeight, " ");
		}
	}

	public static String getProg() {
		return selectedProg;
	}
	
	public static int getScreenWidth() {
		return WIDTH;
	}
	
	public static int getScreenHeight() {
		return HEIGHT;
	}
	
	
	


}