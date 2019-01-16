package juno.core;

import java.io.IOException;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFWKeyCallback.*;

import juno.environ.Player;
import juno.environ.Light;
import juno.environ.GameObject;
import juno.environ.Planet;
import juno.environ.Skybox;
import juno.gl.Camera;
import juno.gl.Display;
import juno.gl.HUDTexture;
import juno.gl.MasterRender;
import juno.gl.Obj;
import juno.gl.Texture;
import juno.gl.RawObj;
import juno.gl.RenderGUI;
import juno.gl.StaticShader;
import juno.physics.CollisionDetector;

/*
* @author Jim Dox.
*/
public class App implements Runnable {

	protected int WIDTH;
	protected int HEIGHT;
	protected String title;
	protected Display display;
	static final int FPS = 60;
	static final double time_per_update = 1000000000/FPS;
	static float lastTime;
	protected Thread thread;
	protected Boolean running; 
	protected GLFWKeyCallback keyCallback;
	

	AssetLoader loader;
	MasterRender masterRender;
	StaticShader shader;
	RenderGUI renderGui;
	

	ArrayList<GameObject> renderableObjects = new ArrayList<GameObject>();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	ArrayList<HUDTexture> hud = new ArrayList<HUDTexture>();
	ArrayList<Light> lights = new ArrayList<Light>();
	Skybox sky;
	Camera camera;
	Player player;
	Interpreter interpreter;
	CollisionDetector collisionDetector;

	
	public App(int width, int height, String title) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.title = title;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading game!");
			System.exit(-1);
		}
		
	}
	
	public void init(){
		running = true;
		GLFW.glfwInit();
		loader = new AssetLoader();		
		display = new Display(WIDTH,HEIGHT);
		shader = new StaticShader();
		masterRender = new MasterRender(display,shader);
		interpreter = new Interpreter();
		renderGui = new RenderGUI(loader);
		loadInstance("test");
		loadGui();
		collisionDetector = new CollisionDetector(gameObjects);
		lastTime = System.nanoTime();
		run();
	    }
	
	public void setup() {
		//thread = new Thread( ,"secondary");
		//thread.start();						// TODO: thread rendering
		
		
	}

	
	public synchronized void run(){

		
	while(running) {
		
		masterRender.renderAllObjects(lights, camera, renderableObjects);
		update();

	}
	
	exit();
	}
	 	
	
	private void update() {
		

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
		
	private void initHUD() {

		
	}
	
	protected void fpsRegulator() {
		
		if(display.shouldExit) {
			running = false;
		}
		
	    double delta = 0;
	    long now;
	    long last_time = System.nanoTime();
	    long timer = 0;
	    int upts = 0;
	    double d2 = 1;
	    while(true){
	      now = System.nanoTime();
	      delta += (now - last_time)/time_per_update;
	      timer+= now-last_time;
	      last_time = now;
	      if(delta>=1){
	    	delta--;
	    	upts++;
	    	break;
	      }
	    }
	}
	

	
	
	protected void loadInstance(String file) {
		RawObj focusObj = loader.loadObjModel("objs/planet");
		Texture focusTex = new Texture(loader.loadTexture("textures/asteroid"));
		player = new Player(new Obj(focusObj,focusTex),new Vector3f(3,2,-850), 0f,180.0f,0f,0.1f,display);
		interpreter.loadMapData(file,loader);
		interpreter.link();
		//lights = mapLoader.getLights();
		lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(1000,1,-1000),new Vector3f(1,1,1)));
		lights.add(new Light(new Vector3f(0,0,0),new Vector3f(1,1,1)));

		gameObjects = interpreter.getGameObjects();		
		sky = interpreter.getSkybox();
		camera = new Camera(player,display);
		renderableObjects.add(player);

		for(GameObject obj : gameObjects) {
			renderableObjects.add(obj);
		}
	}
	
	protected void loadGui() {
		//hud.add(new HUDTexture(objLoader.loadTexture("test-0-1-4"), new Vector2f(0.8f,-.95f), new Vector2f(0.09f,0.09f)));
	}
	
	
		
		
	
	
	protected void exit() {
		shader.close();
		loader.clearData();
		System.exit(0);
	}
}
