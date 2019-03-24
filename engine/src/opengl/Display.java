package opengl;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import  org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import core.IOHandler;
import core.MouseHandler;

public class Display {
	private long window;
	private int WIDTH;
	private int HEIGHT;
	private final String TITLE;
	private int sWIDTH;
	private int sHEIGHT;
	private boolean bool_pause;
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback mouseCallback;

	public boolean shouldExit;


	public Display() {
		WIDTH = 900;
		HEIGHT = 900;
		TITLE = "";
		init();
	}

	public Display(int width, int height, String title) {
		WIDTH  = width;
		HEIGHT = height;
		this.TITLE = title;
		sWIDTH = WIDTH;
		sHEIGHT = HEIGHT;
		shouldExit = false;
		bool_pause = false;
		init();
		update();
	}

	private void init() {

		try {

			GLFWErrorCallback.createPrint(System.err).set();
			if ( !glfwInit() ) {
				throw new IllegalStateException("Window error on start");
			}
			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);


			// Create window
			window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, NULL, NULL);
			if ( window == NULL )
				throw new RuntimeException("Display failed to launch!!");

			// called each time a key is pressed
			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

			});
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Display Init Failed!");
			System.exit(0);
		}

		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(window, (videoMode.width()-sWIDTH)/2, videoMode.height()-sHEIGHT/2);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		// enable v-sync
		glfwSwapInterval(1);
		glfwWindowHint(GLFW_SAMPLES, 2);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, 2.0f);
		glfwShowWindow(window);
		glfwSetKeyCallback(window, keyCallback = new IOHandler());
		glfwSetCursorPosCallback(window, mouseCallback = new MouseHandler());
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

	}



	public void update(){

		// Set color of void rendering
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// deallocated memory and stops all openGL processes.
		if( glfwWindowShouldClose(window) ) {
			System.exit(0);
		}

		if( ((IOHandler) keyCallback).isKeyDown(GLFW_KEY_ESCAPE)){
			shouldExit = true;
		}

		glfwPollEvents();
		glfwSwapBuffers(window); // swap the color buffers

	}

	public int getHEIGHT() {
		return HEIGHT;
	}
	public int getWIDTH() {
		return WIDTH;
	}

	public IOHandler getKeyboardHandler() {
		return ((IOHandler)keyCallback);
	}
	public MouseHandler getMouseHandler() {
		return ((MouseHandler)mouseCallback);
	}

	public long getWindowID() {
		return window;
	}

	public void pause(boolean bool)
	{
		bool_pause = bool;
	}

	public void closeDiplay() {
		glfwTerminate();
		System.exit(0);
	}

}
