package mdnt.core;

import org.lwjgl.glfw.GLFW;

import mdnt.environ.GameObject;
import mdnt.gl.Display;
import mdnt.gl.MasterRender;
import mdnt.gl.RenderGUI;
import mdnt.gl.StaticShader;
import mdnt.physics.CollisionDetector;

public class Game extends App {

	public static void createInstance(int w, int h, String windowName, String filepath) {
		Game game = new Game(w, h, windowName, filepath);
	}

	private float currentTime;
	private String scriptPath;

	public Game(int w, int h, String windowName, String filepath) {
		super(w, h, windowName, filepath);
		scriptPath = filepath;

	}

	@Override
	public void fpsRegulator() {

	}

	@Override
	public void init() {

		running = true;
		GLFW.glfwInit();
		super.loader = new AssetLoader();
		super.display = new Display(WIDTH, HEIGHT);
		super.shader = new StaticShader();
		super.masterRender = new MasterRender(display, shader);
		super.interpreter = new Interpreter();
		super.renderGui = new RenderGUI(loader);
		loadInstance(Launcher.getProg());
		loadGui();
		collisionDetector = new CollisionDetector(gameObjects);
		lastTime = System.nanoTime();
		currentTime = System.nanoTime();
		run();
	}

	@Override
	protected void loadGui() {
		// hud.add(new HUDTexture(loader.loadTexture("test-0-1-5"), new
		// Vector2f(0.9f,-.95f), new Vector2f(0.1f,0.04f)));

	}

	@Override
	public synchronized void run() {

		while (running) {

			masterRender.renderAllObjects(lights, camera, renderableObjects);
			renderGui.render(hud);
			// masterRender.renderSky(sky);
			update();

		}

		exit();
	}

	public void SecondaryUpdate() { // updates once every x number of frames.

	}

	public void update() {
		player.update();
		camera.update(display);

		sky.setPosition(camera.getPosition());
		currentTime = System.nanoTime();
		float dt = currentTime - lastTime;
		lastTime = currentTime;
		dt /= 1000000000;

		for (GameObject obj : gameObjects) {
			obj.update(dt);
		}
		collisionDetector.update();

		fpsRegulator();
		if (display.shouldExit) {
			running = false;
		}
	}

}
