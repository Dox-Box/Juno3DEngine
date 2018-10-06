package engine;

import java.io.IOException;



import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFWKeyCallback.*;

import environment.PhysicsObject;
import environment.Light;
import environment.Planet;
import environment.Focus;
import environment.Skybox;
import graphicsEngine.Camera;
import graphicsEngine.Display;
import graphicsEngine.HUDTexture;
import graphicsEngine.OBJLoader;
import graphicsEngine.OBJRender;
import graphicsEngine.Obj;
import graphicsEngine.ObjTexture;
import graphicsEngine.RawObj;
import graphicsEngine.RenderGUI;
import graphicsEngine.StaticShader;
import physicsEngine.CollisionDetector;

/*
* @author Jim Dox.
*/
public class Main implements Runnable {

	private int WIDTH;
	private int HEIGHT;
	private String title;
	private Display display;
	static final int FPS = 60;
	static final double time_per_update = 1000000000/FPS;
	static float lastTime;
	private Thread thread;
	private Boolean running; 
	private GLFWKeyCallback keyCallback;
	

	OBJLoader objLoader;
	OBJRender objRender;
	StaticShader shader;
	RenderGUI renderGUI;
	
	ObjTexture texture;
	ObjTexture texture2;
	ArrayList<PhysicsObject> renderObjects = new ArrayList<PhysicsObject>();
	ArrayList<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();
	ArrayList<HUDTexture> hud = new ArrayList<HUDTexture>();
	ArrayList<Light> lights = new ArrayList<Light>();
	Skybox stars;
	Skybox stars2;
	Camera camera;
	Focus focus;
	Planet planet1;
	MapLoader mapLoader;
	CollisionDetector collisionDetector;

	
	public Main(int width, int height, String name) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.title = name;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading simulation!");
			System.exit(-1);
		}
		
	}
	
	public void init(){
		setup();
		running = true;
		GLFW.glfwInit();
		objLoader = new OBJLoader();		
		display = new Display(WIDTH,HEIGHT);
		shader = new StaticShader();
		objRender = new OBJRender(display,shader);
		mapLoader = new MapLoader();
		renderGUI = new RenderGUI(objLoader);
		loadSim("test");
		loadHUD();
		collisionDetector = new CollisionDetector(physicsObjects);
		lastTime = System.nanoTime();
		run();
	    }
	
	public void setup() {
		//thread = new Thread( ,"secondary");
		//thread.start();						// TODO: thread cpu and gpu calls.
		
		
	}

	
	public synchronized void run(){

		
		
		
	while(running) {
		
		
		planet1.changeRotation(0,.14f,0);
		objRender.renderAllObjects(lights, camera, renderObjects);
		renderGUI.render(hud);
		update();

	}
	
	exit();
	}
	 	
	
	public void update() {
		focus.update();
		camera.update(display);
		
		stars.setPosition(camera.getPosition());
		stars2.setPosition(camera.getPosition());
		float current = System.nanoTime(); 
		float dt = current - lastTime;
		dt /= 1000000000;
		for(PhysicsObject obj : physicsObjects ) {
			obj.update(dt);
		}
		collisionDetector.update();
		fpsRegulator();
		lastTime = current;

	}
		
	public void initHUD() {

		
	}
	
	public void fpsRegulator() {
		
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
	

	
	
	public void loadSim(String file) {
		RawObj focusObj = objLoader.loadObjModel("prime");
		ObjTexture focusTex = new ObjTexture(objLoader.loadTexture("red"));
		focus = new Focus(new Obj(focusObj,focusTex),new Vector3f(3,2,-850), 0f,180.0f,0f,0.001f,display);
		mapLoader.loadMapData(file,objLoader);
		mapLoader.link();
		//lights = mapLoader.getLights();
		lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(1000,1,-1000),new Vector3f(1,1,1)));
		lights.add(new Light(new Vector3f(0,0,0),new Vector3f(1,1,1)));

		physicsObjects = mapLoader.getPhysicsObjects();		
		stars = mapLoader.getSkybox();
		stars2 = mapLoader.getSkybox2();
		planet1 = mapLoader.getPlanet();
		camera = new Camera(focus,display);
		renderObjects.add(focus);
		renderObjects.add(stars);
		renderObjects.add(stars2);
		renderObjects.add(planet1);
		for(PhysicsObject obj : physicsObjects) {
			renderObjects.add(obj);
		}
	}
	
	public void loadHUD() {
		hud.add(new HUDTexture(objLoader.loadTexture("test-0-1-4"), new Vector2f(0.8f,-.95f), new Vector2f(0.09f,0.09f)));
	}
	
	
		
		
		
		

		
		
	
	
	public void exit() {
		shader.close();
		objLoader.clearData();
		System.exit(0);
	}
}
