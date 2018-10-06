package engine;
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
import graphicsEngine.Display;

public class Launcher extends JFrame {
	
	/* authors : J. Dox
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame frame;
  
	private static String musicFile = ".wav";

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
		
		 frame = new JFrame();
	      frame.getContentPane().setBackground(new Color(.0f,.0f,.0f));
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setLocationRelativeTo(null);
	      frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	      frame.setResizable(true);
	      frame.getContentPane().setLayout(null);
	      frame.setTitle("Juno Game Engine");
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
	      
	      JLabel lblNewLabel = new JLabel("Juno Simulation Engine");
	      lblNewLabel.setFont(new Font("Courier", Font.ITALIC, 26));					
	      lblNewLabel.setBounds(-99, 13, 602, 44);
	      lblNewLabel.setForeground(new Color(.8f,.8f,.8f));
	      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	      lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
	      frame.getContentPane().add(lblNewLabel);
	      
	       JButton start = new JButton("RUN");
	       start.setFont(new Font("Noto Sans", Font.PLAIN, 24));
	       start.setForeground(new Color(1f,1f,1f));
	       start.setBackground(new Color(0.6f,0.15f,0.15f));
	       start.addMouseListener(new MouseAdapter() {
	        @Override
	         public void mouseClicked(MouseEvent e) {
	          frame.setVisible(false);
	          
				Main test = new Main(WIDTH, HEIGHT, "0.0.0");			
	        }	
	        });
	       start.setBounds(40, HEIGHT - 800, 190, 48);

	       frame.getContentPane().add(start);  
	       
	       JButton load = new JButton("LOAD DATA");
	          load.setFont(new Font("Noto Sans", Font.PLAIN, 24));
	          load.setBackground(new Color(0.58f,0.15f,0.15f));
	          load.setForeground(new Color(.9f,.9f,.9f));
	          load.addMouseListener(new MouseAdapter() {
	           @Override
	           public void mouseClicked(MouseEvent e) {
	           

	           }
	          });

	          load.setBounds(40, HEIGHT-650, 190, 48);
	          frame.getContentPane().add(load);

		       
		       JButton options = new JButton("OPTIONS");
		          options.setFont(new Font("Noto Sans", Font.PLAIN, 24));
		          options.setBackground(new Color(0.58f,0.15f,0.15f));
		          options.setForeground(new Color(.9f,.9f,.9f));
		          options.addMouseListener(new MouseAdapter() {
		           @Override
		           public void mouseClicked(MouseEvent e) {
		           

		           }
		          });

		          options.setBounds(40, HEIGHT-500, 190, 48);
		          frame.getContentPane().add(options);
		          
		      
		          JButton exit = new JButton("EXIT");
		          exit.setFont(new Font("Courier",Font.PLAIN,24));
		          exit.setBackground(new Color(0.16f,0.46f,0.16f));
		          exit.setForeground(new Color(0.9f,0.9f,0.9f));
		          exit.addMouseListener(new MouseAdapter() {
			           public void mouseClicked(MouseEvent e) {
			        	   System.exit(0);
			           }
			          });
		          exit.setBounds(60,HEIGHT-200,100,48);
		          frame.getContentPane().add(exit);
	       
	       /* version of engine */
	       JLabel version = new JLabel("version 0.1.4");
	       version.setForeground(Color.WHITE);
	       version.setFont(new Font("Arial",Font.PLAIN,16));
	       																		
	       version.setBounds(30, HEIGHT-75,130,35);
	       frame.getContentPane().add(version);
	       
	       
	       /* background for menu */
	       JLabel lblNewLabel_1 = new JLabel("");
	       lblNewLabel_1.setIcon(new ImageIcon("res/menu-icon.png"));			
	       lblNewLabel_1.setBounds(10, 0, WIDTH+10, HEIGHT+10);
	       frame.getContentPane().add(lblNewLabel_1);
	       
	       
	       
	       
	}
	
	
	
	
	}