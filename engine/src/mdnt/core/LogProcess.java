package mdnt.core;

import mdnt.launch.BootFrame;

enum LogEvent{
	BOOT, ERROR, LOAD
}

public class LogProcess implements Runnable{

	private BootFrame boot;
	private Thread thread;
	
	
	
	public LogProcess() {
		
	}
	
	public LogProcess(LogEvent event) {
		thread = new Thread(this);
		thread.start();
		if(event == LogEvent.BOOT) {
			boot = new BootFrame("res/splash.png");
			boot.setVisible(true);
		}
	}
	
	
	public void run() {
	}
	
	public void close() {
		if(boot != null) {
		this.boot.getJFrame().dispose();
		}
	}
	
}
