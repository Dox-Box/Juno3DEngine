package mdnt.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mdnt.core.App;
import mdnt.gl.Display;

public class Launcher extends JFrame {

	/*
	 * authors : Jim. Dox.
	 */

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
	private Color defaultColor;
	private Color hoverColor;
	private boolean isHoveringButton;
	private static final String VERSION_STR = "Build 0.1.6";
	private static String selectedProg;
	private static boolean readyToStart;

	private static int itemHeight;
	private static int itemWidth;
	private static int startPosX;
	private static int startPosY;
	private Color fileColor;
	private File[] listFiles;

	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private int WIDTH = gd.getDisplayMode().getWidth();
	private int HEIGHT = gd.getDisplayMode().getHeight();
	private static int applicationWidth;
	private static int applicationHeight;
	private boolean promptFlag;
	private JButton run;
	private JButton worldBuilder;
	private JButton options;
	private JButton exit;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Launcher launcher = new Launcher();
					launcher.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		while (!readyToStart) {

		}
		Game.createInstance(applicationWidth, applicationHeight, " ", selectedProg);
	}

	public Launcher() {
		initialize();

	}

	private void initialize() {
		defaultColor = new Color(0.06f, 0.06f, 0.06f);
		hoverColor = new Color(0.1f, 0.08f, 0.15f);
		isHoveringButton = false;
		int hoverXOffset = 4;
		int hoverYOffset = -2;
		applicationWidth = WIDTH;
		applicationHeight = HEIGHT;
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(.0f, .0f, .0f));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(true);
		frame.getContentPane().setLayout(null);
		frame.setTitle(" ");
		frame.setFocusable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 540);
		setSize(400, 400);
		frame.setVisible(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setUndecorated(false);
		readyToStart = false;
		promptFlag = false;

		run = new JButton("Run");
		run.setFont(new Font("Monospaced", Font.PLAIN, 26));
		run.setForeground(new Color(1f, 1f, 1f));
		run.setBackground(defaultColor);
		run.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!promptFlag) {
					ScriptSelector scriptSelector = new ScriptSelector(getRunnableFiles());
					promptFlag = true;
				}
			}

			public void mouseEntered(MouseEvent e) {
				run.setBounds(410 + hoverXOffset, HEIGHT - 730 + hoverYOffset, 190, 48);
				run.setBackground(hoverColor);

			}

			public void mouseExited(MouseEvent e) {
				run.setBounds(410, HEIGHT - 730, 190, 48);
				run.setBackground(defaultColor);
				isHoveringButton = false;
			}

		});
		run.setBounds(410, HEIGHT - 730, 190, 48);
		run.setBorderPainted(false);
		run.setFocusPainted(false);
		frame.getContentPane().add(run);
		/*
		 * JButton load = new JButton("Load Save"); load.setFont(new Font("Monospaced",
		 * Font.PLAIN, 24)); load.setBackground(defaultColor); load.setForeground(new
		 * Color(.9f,.9f,.9f)); load.addMouseListener(new MouseAdapter() {
		 * 
		 * public void mouseEntered(MouseEvent e) { load.setBounds(410 + hoverXOffset,
		 * HEIGHT - 650 + hoverYOffset, 190, 48); load.setBackground(hoverColor);
		 * 
		 * 
		 * } public void mouseExited(MouseEvent e) { load.setBounds(410, HEIGHT - 650,
		 * 190, 48); load.setBackground(defaultColor); }
		 * 
		 * public void mouseClicked(MouseEvent e) { promptSelectFile(); } });
		 * 
		 * load.setBounds(410, HEIGHT-650, 190, 48); load.setBorderPainted(false);
		 * load.setFocusPainted(false); frame.getContentPane().add(load);
		 */
		worldBuilder = new JButton("World Designer");
		worldBuilder.setFont(new Font("Monospaced", Font.PLAIN, 24));
		worldBuilder.setBackground(defaultColor);
		worldBuilder.setForeground(new Color(.9f, .9f, .9f));
		worldBuilder.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				worldBuilder.setBounds(410 + hoverXOffset, HEIGHT - 610 + hoverYOffset, 240, 48);
				worldBuilder.setBackground(hoverColor);
			}

			public void mouseExited(MouseEvent e) {
				worldBuilder.setBounds(410, HEIGHT - 610, 240, 48);
				worldBuilder.setBackground(defaultColor);
			}
		});
		worldBuilder.setBounds(410, HEIGHT - 610, 240, 48);
		worldBuilder.setBorderPainted(false);
		worldBuilder.setFocusPainted(false);
		frame.getContentPane().add(worldBuilder);

		options = new JButton("Options");
		options.setFont(new Font("Courier", Font.PLAIN, 24));
		options.setBackground(defaultColor);
		options.setForeground(new Color(.9f, .9f, .9f));
		options.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				OptionsMenu.start();
			}

			public void mouseEntered(MouseEvent e) {
				options.setBounds(410 + hoverXOffset, HEIGHT - 490 + hoverYOffset, 190, 48);
				options.setBackground(hoverColor);
			}

			public void mouseExited(MouseEvent e) {
				options.setBounds(410, HEIGHT - 490, 190, 48);
				options.setBackground(defaultColor);
			}
		});

		options.setBounds(410, HEIGHT - 490, 190, 48);
		options.setBorderPainted(false);
		options.setFocusPainted(false);
		frame.getContentPane().add(options);

		exit = new JButton("Exit");
		exit.setFont(new Font("Monospaced", Font.PLAIN, 24));
		exit.setBackground(new Color(0.07f, 0.07f, 0.07f));
		exit.setForeground(new Color(0.9f, 0.9f, 0.9f));
		exit.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
				exit.setBounds(410 + hoverXOffset, HEIGHT - 200 + hoverYOffset, 100, 48);
				exit.setBackground(new Color(0.33f, 0.1f, 0.1f));
			}

			public void mouseExited(MouseEvent e) {
				exit.setBounds(410, HEIGHT - 200, 100, 48);
				exit.setBackground(new Color(0.07f, 0.07f, 0.07f));
			}
		});
		exit.setBounds(410, HEIGHT - 200, 100, 48);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		frame.getContentPane().add(exit);

		/* version of engine */
		JLabel version = new JLabel(VERSION_STR);
		version.setForeground(Color.WHITE);
		version.setFont(new Font("Courier", Font.PLAIN, 13));

		version.setBounds(WIDTH - 200, HEIGHT - 75, 130, 35);
		frame.getContentPane().add(version);

		JLabel title = new JLabel("");
		title.setIcon(new ImageIcon("res/engine.png"));
		title.setBounds(WIDTH / 3 + 100, 80, 800, 100);
		frame.getContentPane().add(title);

		/* background for menu */
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("res/mdnt-launcher3.png"));
		lblNewLabel_1.setBounds(0, 0, WIDTH + 100, HEIGHT);
		frame.getContentPane().add(lblNewLabel_1);

		frame.setResizable(false);

		getRunnableFiles();
	}

	public File[] getRunnableFiles() {
		File folder = new File("res/script/");
		File[] files = folder.listFiles();
		for (File file : files) {
			if (!file.getName().contains(".mdnt")) {
				file = null;
			}
		}

		return files;
	}

	public void setVisible(boolean flag) {
		this.frame.setVisible(flag);
	}

	/* starts app once selected by user */
	public static void setProg(String filepath) {
		selectedProg = filepath;
		readyToStart = true;
		if (filepath != null) {
			Game.createInstance(applicationWidth, applicationHeight, " ", selectedProg);
		}
	}

	public static String getProg() {
		return selectedProg;
	}

}