package mdnt.launch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class ScriptSelector extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 3L;
	private static int WIDTH;
	private static int HEIGHT;
	private static int itemHeight;
	private static int itemWidth;
	private static int startPosX;
	private static int startPosY;

	public static void start(File[] files) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) screenSize.getWidth();
		HEIGHT = (int) screenSize.getHeight();

		ScriptSelector select = new ScriptSelector(files);
	}

	private JFrame frame;
	private JPanel contentPane;
	private File[] scripts;
	private Color itemColor;

	private Color itemHoverColor;

	public ScriptSelector(File[] files) {
		scripts = files;
		display();
	}

	public void display() {
		frame = new JFrame();
		contentPane = new JPanel();
		frame.setUndecorated(true);

		frame.getContentPane().setBackground(new Color(.5f, .5f, .5f));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false); /* TODO: add scaling if files exceed frame limit. */
		frame.getContentPane().setLayout(null);
		frame.setTitle(" ");
		frame.setFocusable(true);
		frame.setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 540);
		frame.setBounds(690, 320, 200, 600);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(true);
		frame.setVisible(true);

		itemColor = new Color(0.1f, 0.1f, 0.1f);
		itemHoverColor = new Color(0.18f, 0.14f, 0.22f);
		startPosX = 10;
		startPosY = 10;

		itemHeight = 40;
		itemWidth = 180;
		int deltaX = 0;
		int deltaY = 0;

		for (int i = 0; i < scripts.length; i++) {
			if (scripts[i] != null) {
				frame.getContentPane().add(generateButton(scripts[i], deltaX, deltaY));
				deltaY += 65;

			}
		}

	}

	public JButton generateButton(File file, int deltaX, int deltaY) {
		JButton fileButton = new JButton(file.getName());
		fileButton.setBounds(startPosX + deltaX, startPosY + deltaY, itemWidth, itemHeight);
		fileButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
		fileButton.setBackground(itemColor);
		fileButton.setForeground(Color.WHITE);
		fileButton.setBorderPainted(false);
		fileButton.setFocusPainted(false);
		fileButton.setName(file.getName());
		fileButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setAlwaysOnTop(false);
				frame.dispose();
				Launcher.setProg(fileButton.getName());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				fileButton.setBackground(itemHoverColor);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				fileButton.setBackground(itemColor);
			}
		});

		return fileButton;
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}

}
