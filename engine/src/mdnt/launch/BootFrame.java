package mdnt.launch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class BootFrame extends JFrame{

	private static final long serialVersionUID = 3L;
	private static int sWIDTH;
	private static int sHEIGHT;
	private int width;
	private int height;

	private static String filepath;

	private JFrame frame;
	private JPanel contentPane;
	private File[] scripts;
	private Color itemColor;
	private Color itemHoverColor;

	public BootFrame(String filepath) {
		this.filepath = filepath;
		width = 200;
		height = 400;
		display();
	}

	public void display() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		sWIDTH = (int) screenSize.getWidth();
		sHEIGHT = (int) screenSize.getHeight();
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
	
	}

	public void setVisible(boolean flag) {
		this.frame.setVisible(flag);
	}
	
	public JFrame getJFrame() {
		return this.frame;
	}

	
	
	
	
	
}
