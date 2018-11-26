package juno.core;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.*;
import javax.swing.JButton;
import java.awt.Color;

public class GraphDisplay {

	    private JFrame frame;
	    private Canvas canvas;

	    private String title;
	    private int width;
	    private int height;
	    private int monitorWidth;
	    private int monitorHeight;
		
	    public GraphDisplay(String title, int width, int height){
	      this.title = title;
	      this.width = width;
	      this.height = height;
	      createDisplay();
	    }

	    private void createDisplay(){
	      frame = new JFrame(title);
	      frame.setSize(width,height);
	      frame.setResizable(false);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(false);

	      frame.setLocationRelativeTo(null);
	      frame.requestFocusInWindow();
	      frame.setUndecorated(true);
	      canvas = new Canvas();
	      canvas.setPreferredSize( new Dimension( width,  height));
	      canvas.setMinimumSize(new Dimension(width,height));
	      canvas.setFocusable(false);
	      frame.add(canvas);

	      frame.pack();
	      frame.setVisible(true);
	      }

	    public JFrame getFrame(){
	        return frame;
	    }

	    public Canvas getCanvas(){
		return canvas;
	    }

	}

