package juno.core;

import org.lwjgl.glfw.GLFW;

import juno.gl.Display;
import juno.gl.MasterRender;
import juno.gl.RenderGUI;
import juno.gl.StaticShader;
import juno.physics.CollisionDetector;

public class Game extends App{
	
	public Game(int w, int h, String title) {
		super(w, h, title);

	}
	
	public void init() {
		this.running = true;
		GLFW.glfwInit();
		super.loader = new AssetLoader();		
		super.display = new Display(WIDTH,HEIGHT);
		super.shader = new StaticShader();
		super.masterRender = new MasterRender(display,shader);
		super.interpreter = new Interpreter();
		super.renderGUI = new RenderGUI(loader);
		loadInstance("game1");
		loadHUD();
		collisionDetector = new CollisionDetector(gameObjects);
		lastTime = System.nanoTime();
		run();
	}
	
	public synchronized void run(){

		
		while(running) {	
			
			masterRender.renderAllObjects(lights, camera, renderableObjects);
			update();

		}
		
		exit();
	}
	
	public void update() {
		
	}
	
	
	
	
}
