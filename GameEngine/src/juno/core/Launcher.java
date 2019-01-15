package juno.core;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import juno.core.App;
import juno.gl.Display;

public class Launcher extends JFrame {
	
	/* authors : J. Dox.
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
    private Color defaultColor;
    private Color hoverColor;
    private boolean isHoveringButton;
    private static final String VERSION_STR = "Build 0.1.5";

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  	private int WIDTH = (int) screenSize.getWidth();

  	private int HEIGHT = (int) screenSize.getHeight();
  	


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
  }
  
  	public Launcher() {
  		initialize();
  		
  	}
	private void initialize() {
		defaultColor = new Color(0.28f, 0.0f, 0.41f);
		hoverColor = new Color(0.22f, 0.0f, 0.5f);
		isHoveringButton = false;
		int hoverXOffset = 4;
		int hoverYOffset = -2;
		
	    frame = new JFrame();
	    frame.getContentPane().setBackground(new Color(.0f,.0f,.0f));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocationRelativeTo(null);
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setResizable(true);
	    frame.getContentPane().setLayout(null);
	    frame.setTitle("JGE");
	    frame.setFocusable(false);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 920, 540);
	    frame.setVisible(true);
	 
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);
	    setResizable(false);
	    setUndecorated(false);
	  
	    JLabel title = new JLabel("Juno Game Engine");
	    title.setFont(new Font("Courier", Font.ITALIC, 36));					
	    title.setBounds(WIDTH/6, 115, 602, 44);
	    title.setForeground(new Color(.8f,.8f,.8f));
	    title.setHorizontalAlignment(SwingConstants.CENTER);
	    title.setVerticalAlignment(SwingConstants.TOP);
	    frame.getContentPane().add(title);
	    
	    JButton run = new JButton("Run");
	    run.setFont(new Font("Monospaced", Font.PLAIN, 26));
	    run.setForeground(new Color(1f,1f,1f));
	    run.setBackground(defaultColor);
	    run.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {
	    	frame.setVisible(false);
	    	App test = new App(WIDTH, HEIGHT, "0.1.5");
	    }
	    public void mouseEntered(MouseEvent e) {
	    	 run.setBounds(460 + hoverXOffset, HEIGHT - 800 + hoverYOffset, 190, 48);
	    	 run.setBackground(hoverColor);
	    	      
	    }
	    public void mouseExited(MouseEvent e) {
	    	 run.setBounds(460, HEIGHT - 800, 190, 48);
	    	 run.setBackground(defaultColor);
	    	 isHoveringButton = false;
	    }
	     
	    });
	    run.setBounds(460, HEIGHT - 800, 190, 48);
	    run.setBorderPainted(false);
	    run.setFocusPainted(false);
	    frame.getContentPane().add(run);  
	   
	    JButton load = new JButton("Load Save");
	    load.setFont(new Font("Monospaced", Font.PLAIN, 24));
	    load.setBackground(defaultColor);
	    load.setForeground(new Color(.9f,.9f,.9f));
	    load.addMouseListener(new MouseAdapter() {

	    	public void mouseEntered(MouseEvent e) {
	    	load.setBounds(460 + hoverXOffset, HEIGHT - 650 + hoverYOffset, 190, 48);
	    	load.setBackground(hoverColor);
	    	}
	    	public void mouseExited(MouseEvent e) {
	    		load.setBounds(460, HEIGHT - 650, 190, 48);
	    		load.setBackground(defaultColor);
	    	}
    	});
	
	    load.setBounds(460, HEIGHT-650, 190, 48);
	    load.setBorderPainted(false);
	    load.setFocusPainted(false);
	    frame.getContentPane().add(load);
	
	    JButton worldBuilder = new JButton("World Designer");
	    worldBuilder.setFont(new Font("Monospaced", Font.PLAIN, 24));
	    worldBuilder.setBackground(new Color(0.3f,0.0f,0.45f));
	    worldBuilder.setForeground(new Color(.9f,.9f,.9f));
	    worldBuilder.addMouseListener(new MouseAdapter() {

	    	public void mouseClicked(MouseEvent e) {
	
	    	}
	    	public void mouseEntered(MouseEvent e) {
	    		worldBuilder.setBounds(460 + hoverXOffset, HEIGHT - 500 + hoverYOffset, 240, 48);
	    		worldBuilder.setBackground(hoverColor);
	    	}
	    	public void mouseExited(MouseEvent e) {
	    		worldBuilder.setBounds(460, HEIGHT - 500, 240, 48);
	    		worldBuilder.setBackground(defaultColor);
	    	}
	    });
	    worldBuilder.setBounds(460, HEIGHT - 500, 240, 48);
	    worldBuilder.setBorderPainted(false);
	    worldBuilder.setFocusPainted(false);
	    frame.getContentPane().add(worldBuilder);
	   
	    JButton options = new JButton("Options");
	    options.setFont(new Font("Courier", Font.PLAIN, 24));
	    options.setBackground(new Color(0.3f,0.0f,0.45f));
	    options.setForeground(new Color(.9f,.9f,.9f));
	    options.addMouseListener(new MouseAdapter() {

	    	public void mouseClicked(MouseEvent e) {
	    		OptionsMenu.start();
	    	}
	    	
	        public void mouseEntered(MouseEvent e) {
	        	options.setBounds(460 + hoverXOffset, HEIGHT - 350 + hoverYOffset, 190, 48);
	        	options.setBackground(hoverColor);
	        }
		    public void mouseExited(MouseEvent e) {
		    	options.setBounds(460, HEIGHT - 350, 190, 48);
		    	options.setBackground(defaultColor);
		    }
	    });
	
	    options.setBounds(460, HEIGHT-350, 190, 48);
	    options.setBorderPainted(false);
	    options.setFocusPainted(false);
	    frame.getContentPane().add(options);
	      
	  
	    JButton exit = new JButton("Exit");
	    exit.setFont(new Font("Monospaced",Font.PLAIN,24));
	    exit.setBackground(new Color(0.16f,0.16f,0.16f));
	    exit.setForeground(new Color(0.9f,0.9f,0.9f));
	    exit.addMouseListener(new MouseAdapter() {
		          
	    	public void mouseClicked(MouseEvent e) {
	    		System.exit(0);
		    }
		    
	    	public void mouseEntered(MouseEvent e) {
	    		exit.setBounds(460 + hoverXOffset, HEIGHT - 200 + hoverYOffset, 100, 48);
	    		exit.setBackground(new Color(0.33f, 0.1f, 0.1f));
	    	}
		    	   
	    	public void mouseExited(MouseEvent e) {
	    		exit.setBounds(460, HEIGHT - 200, 100, 48);
		        exit.setBackground(new Color(0.16f, 0.16f, 0.16f));
		    }
        });
        exit.setBounds(460,HEIGHT-200,100,48);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        frame.getContentPane().add(exit);
	   
	    /* version of engine */
	    JLabel version = new JLabel(VERSION_STR);
	    version.setForeground(Color.WHITE);
	    version.setFont(new Font("Courier",Font.PLAIN,13));
	     																		
	    version.setBounds(WIDTH - 200, HEIGHT-75,130,35);
	    frame.getContentPane().add(version);
	     
	       
	    /* background for menu */
	    JLabel lblNewLabel_1 = new JLabel("");
	    lblNewLabel_1.setIcon(new ImageIcon("res/launcher-gameEngine.png"));
	    lblNewLabel_1.setBounds(0, 0, WIDTH, HEIGHT);
	    frame.getContentPane().add(lblNewLabel_1);
						   
	   
   
   
	}
	
	
	
	
	
	
}