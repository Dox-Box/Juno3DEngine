package mdnt.core;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

public class OptionsMenu extends JFrame{

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
		
		frame.getContentPane().setBackground(new Color(.2f,.2f,.2f));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setResizable(true);
	    frame.getContentPane().setLayout(null);
	    frame.setTitle(" ");
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
	    title.setFont(new Font("Courier", Font.PLAIN, 34));
	    title.setForeground(Color.BLACK);
	    frame.getContentPane().add(title);
		
	    JButton exit = new JButton("BACK");
	    exit.setBounds(WIDTH-140, HEIGHT-140, 100, 47);
	    exit.setFont(new Font("Courier", Font.ITALIC, 25));
	    exit.setForeground(Color.WHITE);
	    exit.setBackground(new Color(0.16f,0.46f,0.16f));
	    
	    frame.getContentPane().add(exit);

	    exit.addMouseListener(new MouseAdapter() {
		          
	    	public void mouseClicked(MouseEvent e) {
	    		// TODO: setConfig
	    		
	    		frame.setVisible(false);
	    		frame.dispose();
		        	  	
		    }
		});
		
	    //JSlider resolution = new JSlider(JSlider.HORIZONTAL,1280,1920,1920);
	    JLabel masterVolLabel = new JLabel("Master Volume");
	    masterVolLabel.setBackground(null);
	    masterVolLabel.setForeground(new Color(0.45f, 0.65f, 0.45f));
	    masterVolLabel.setFont(new Font("Arial", Font.BOLD, 13));
	    masterVolLabel.setBounds(WIDTH-415, 250, 160, 40);
	    masterVolLabel.setVisible(true);
	    frame.getContentPane().add(masterVolLabel);
	    
	    JSlider masterVolume = new JSlider(JSlider.VERTICAL, 0, 100, 100);
	    masterVolume.setVisible(true);
	    masterVolume.setBounds(WIDTH-400,300, 60, 100);
	    masterVolume.setForeground(new Color(0.8f, 0.8f, 0.8f));
	    masterVolume.setBackground(new Color (0.3f, 0.3f, 0.3f));
	    frame.getContentPane().add(masterVolume);
	    
	    
	   
						   	    
	}
	
	
	

	
}
