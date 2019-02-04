package mdnt.core;

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
import javax.swing.border.EmptyBorder;

public class ScriptSelector extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private JFrame frame;
	private JPanel contentPane;
	private static int WIDTH;
	private static int HEIGHT;
	private File[] scripts;
	private static int itemHeight;
	private static int itemWidth;
	private static int startPosX;
	private static int startPosY;
	private Color itemColor;
	private Color itemHoverColor;
	
	public ScriptSelector(File[] files) {
		scripts = files;
		display();
	}
	
	public static void start(File[] files) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  	WIDTH = (int) screenSize.getWidth();
	  	HEIGHT = (int) screenSize.getHeight();
		
	
		ScriptSelector select = new ScriptSelector(files);
	}
	
	public void display() {
		frame = new JFrame();
		contentPane = new JPanel();
		
		frame.getContentPane().setBackground(new Color(.5f,.5f,.5f));
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setResizable(false);									/* TODO: add scaling if files exceed frame limit. */
	    frame.getContentPane().setLayout(null);
	    frame.setTitle(" ");
	    frame.setFocusable(false);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 920, 540);
	    frame.setBounds(690,320, 200, 600);

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
	
	    for(int i = 0; i < scripts.length; i++) {
	    	if(scripts[i] != null) {
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
        	
        	public void mouseEntered(MouseEvent e) {
        		fileButton.setBackground(itemHoverColor);
        		
        	}
        	
        	public void mouseExited(MouseEvent e) {
        		fileButton.setBackground(itemColor);
        	}
        	
        	public void mouseClicked(MouseEvent e) {
        		Launcher.setProg(fileButton.getName());
        		System.out.println(fileButton.getName());
        }
        });
        
        
        
        
		return fileButton;
	}
	
}
