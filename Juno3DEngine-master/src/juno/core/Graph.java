package juno.core;


import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;

import java.awt.geom.Point2D;
import java.util.Calendar;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import juno.gl.Display;

public class Graph implements Runnable{




  protected GraphDisplay display;
  private Thread thread;


  ArrayList<Float> xData;
  ArrayList<Float> yData;

  public String title;
  public int width;
  public int height;
  private boolean running; 
  private boolean DEBUG;
  static final int FPS = 18;
  static final double time_per_upt = 1000000000/FPS;
  static double timeChange;
  private Color Background;
  private Color lineColor;
  private String xLabel;
  private String yLabel;
  private BufferStrategy bs;
  private BufferedImage image;
  Graphics g;
  private int xShift;
  private int yShift;
  private float yAverage;
  private int speed;
  private float rate;
  private float deltaTime;
  private int graphInterval;
  

  public Graph(String title, String xLabel, String yLabel,int width, int height ) {
    this.title = title;
    this.width = width;
    this.height = height;
    xShift = 0;
    yShift = 0;
    DEBUG = false;
    xData = new ArrayList<Float>();
    yData = new ArrayList<Float>();
    this.xLabel = xLabel;
    this.yLabel = yLabel;
    Background = new Color(0.0f,0.0f,0.0f);
    lineColor = new Color(1f, 0.0f,0.3f);
    deltaTime = 0.0f;
    rate = 0.0f;
    init();
  }


  public void init(){
    display = new GraphDisplay(this.title, this.width, this.height);
    generateDataTest();
    start();
  }

  public synchronized void start(){
    if(running){ 
	return; 
	}
    thread = new Thread(this);
    thread.start();
    running = true;
  }

  public synchronized void stop(){
    if(!running){ return; }
    running = false;
    try{
      thread.join();  /* stops threads */
      display.getFrame().dispose();
      display.getFrame().setVisible(false);
      run();

    }catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  public void run(){
    double d = 0;
    long now;
    /* system clock */
    long last_time = System.nanoTime();
    long timer = 0;
    int upts = 0;
    double d2 = 1;
    while(running){
      now = System.nanoTime();
      d+= (now - last_time)/time_per_upt;
      deltaTime = now-last_time;
      timer+= now-last_time;
      last_time = now;
      timeChange = d;
      if(d>=1){
        update();
        renderGraph();
        upts++;
        d--;
      }
    }
    stop();
  }
  
  public void inputData(){}

  public void update(){
	  yAverage = 0;
	  for(Float data : yData) {
		  yAverage += (int)( (float)data); 
	  }
	  yAverage /= yData.size();
	  
  }
 

  public void renderGraph(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
        display.getCanvas().createBufferStrategy(2);
        return;
        }
        g = bs.getDrawGraphics();
        g.drawRect(0,0,width,height);
        g.setColor(Background);
        g.fillRect(0,0,width,height);
        g.setColor(Color.GREEN);
        g.drawString("(X): " + xLabel,width-100,height -18);
        g.drawString("(Y): " + yLabel, 20,20);
	g.drawString("Avg: " + yAverage,width-90, 20);
        g.setColor(lineColor);
        for(int i = 0; i < xData.size(); i++) {
        	if(xData.size() != yData.size()) {
        		return;
        	}
		rate -= 0.01f;
        	
        	if(i != xData.size() - 1) {
        		int x1 = (int)((float)xData.get(i)+ rate);
        		int x2 = (int)((float)xData.get(i+1)+ rate);
        		int y1 = height - (int) ((float) yData.get(i) + yAverage);
        		int y2 = height - (int) ((float) yData.get(i+1) + yAverage);
        		g.drawLine(x1, y1, x2, y2);
        	
        	}
        }
        /* displays pixel data stored in Buffer onscreen */
        if(bs != null){
        	bs.show();
        	g.dispose();
        	bs.dispose();
        }
    }

   	public void generateDataTest() {
  		for(int i = 0; i < 979; i++) {
  			xData.add((float)(i+1.0f) );
  			yData.add((Float)((float)Math.random()* 30 + 40));
  		}
  	}
        
    public void waitSeconds(int t){
        long init = System.nanoTime()/1000;
        long totalTime = init;
        while(totalTime < t){
            totalTime += (System.nanoTime()/1000 - init);

        }
    }

}
