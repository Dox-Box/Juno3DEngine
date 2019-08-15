package core;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWKeyCallback;

public class IOHandler extends GLFWKeyCallback{

	public static boolean[] keys = new boolean[65536];




	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action !=GLFW_RELEASE;
	}

	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}










}


