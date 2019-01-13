package juno.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OptionsMenu extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel contentPane;
	private static int WIDTH;
	private static int HEIGHT;

	public OptionsMenu() {
		display();
	}
	
	public static void start() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  	WIDTH = (int) screenSize.getWidth();
	  	HEIGHT = (int) screenSize.getHeight();
		
	
		OptionsMenu options = new OptionsMenu();
	}
	
	public void display() {
		frame = new JFrame();
		contentPane = new JPanel();
		
		frame.getContentPane().setBackground(new Color(.7f,.7f,.7f));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setResizable(true);
	    frame.getContentPane().setLayout(null);
	    frame.setTitle("Options Menu");
	    frame.setFocusable(false);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 920, 540);

	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    setResizable(true);
	    frame.setVisible(true);

	    JLabel title = new JLabel("Options");
	    title.setBounds(20,10,190,70);
	    title.setFont(new Font("Courier", Font.PLAIN, 40));
	    title.setForeground(Color.BLACK);
	    frame.getContentPane().add(title);
		
	    JButton exit = new JButton("EXIT");
	    exit.setBounds(WIDTH-140, HEIGHT-140, 100, 70);
	    exit.setFont(new Font("Courier", Font.ITALIC, 25));
	    exit.setForeground(Color.WHITE);
	    exit.setBackground(new Color(0.16f,0.46f,0.16f));
	    frame.getContentPane().add(exit);
	    exit.addMouseListener(new MouseAdapter() {
	        @Override
	         public void mouseClicked(MouseEvent e) {
	          frame.setVisible(false);
	          }	
	        });

	    
		
	}
	
	
	public void exit() {
		
	}
	
	
}
