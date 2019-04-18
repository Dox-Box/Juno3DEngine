package core;

import java.util.ArrayList;




import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import entity.ViewPort;
import entity.Light;
import entity.Entity;
import entity.Skybox;
import launch.Launcher;
import opengl.BasicShader;
import opengl.Camera;
import opengl.Display;
import opengl.HUDTexture;
import opengl.StaticMesh;
import opengl.Texture;
import opengl.MeshData;
import opengl.RenderGUI;
import opengl.RenderMaster;
import physics.CollisionDetector;

/*
 * @author Jim Doxtader
 */
public class App implements Runnable {

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


	AssetImporter objLoader;
	RenderMaster objRender;
	BasicShader shader;
	RenderGUI renderGUI;

	Texture texture;
	Texture texture2;
	ArrayList<Entity> renderObjects = new ArrayList<Entity>();
	ArrayList<Entity> physicalObjects = new ArrayList<Entity>();
	ArrayList<HUDTexture> hud = new ArrayList<HUDTexture>();
	ArrayList<Light> lights = new ArrayList<Light>();
	Skybox stars;
	Skybox stars2;
	Camera camera;
	ViewPort focus;
	Interpreter mapLoader;
	CollisionDetector collisionDetector;
	
	public static void createInstance(int WIDTH, int HEIGHT, String title) {
		App app = new App(WIDTH, HEIGHT, title);
	}


	public App(int width, int height, String title) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.title = title;
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
		//display = new Display(WIDTH, HEIGHT, title);
		
	}

	public void setup() {
		//thread = new Thread( ,"secondary");
		//thread.start();						// TODO: thread cpu and gpu calls.


	}


	@Override
	public synchronized void run(){


		Launcher.setVisibility(false);

		while(running) {


			
			update();

		}

		exit();
	}


	public void update() {
	
		float current = System.nanoTime();
		float dt = current - lastTime;
		dt /= 1000000000;
		
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
		
	}

	public void loadGUI() {
	}











	public void exit() {
		
		System.exit(0);
	}
}
