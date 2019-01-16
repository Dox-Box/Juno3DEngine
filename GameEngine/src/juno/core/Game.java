package juno.core;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import juno.environ.GameObject;
import juno.gl.Display;
import juno.gl.HUDTexture;
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
		super.renderGui = new RenderGUI(loader);
		loadInstance("game1");
		loadGui();
		collisionDetector = new CollisionDetector(gameObjects);
		lastTime = System.nanoTime();
		run();
	}
	
	public synchronized void run(){

		
		while(running) {	
			
			masterRender.renderAllObjects(lights, camera, renderableObjects);
			renderGui.render(hud);
			// masterRender.renderSky(sky);
			update();

		}
		
		exit();
	}
	
	public void update() {
		player.update();
		camera.update(display);
		
		
		sky.setPosition(camera.getPosition());
		float current = System.nanoTime(); 
		float dt = current - lastTime;
		dt /= 1000000000;
		for(GameObject obj : gameObjects ) {
			obj.update(dt);
		}
		collisionDetector.update();
		fpsRegulator();
		lastTime = current;

	}
	
	protected void loadGui() {
		//hud.add(new HUDTexture(loader.loadTexture("test-0-1-5"), new Vector2f(0.9f,-.95f), new Vector2f(0.1f,0.04f)));

	}
	
	
	
	
	
	

	
	
	
	
	
}
