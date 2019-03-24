package entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector3f;

import core.KeyboardHandler;
import opengl.Display;
import opengl.Obj;

public class Focus extends Entity {

	KeyboardHandler keyboardHandler;

	private static final float MOVE_SPEED = 0.02f;
	private static final float MAX_TURN_SPEED = 1.1f;
	private Display display;

	private float deltaX;
	private float deltaY;
	private float deltaZ;
	private float dRotX;
	private float dRotY;
	private float dRotZ;
	private int id;



	public Focus(Obj texturedObj, Vector3f position, float rotX, float rotY, float rotZ, float scale, Display display) {
		super(texturedObj, position, rotX, rotY, rotZ, scale);
		keyboardHandler = (display.getKeyboardHandler());
		this.display = display;
		deltaX = 0;
		deltaY = 0;
		deltaZ = 0;
		dRotX = 0;
		dRotY = 0;
		dRotZ = 0;
	}




	public void input() {
		if(keyboardHandler.isKeyDown(GLFW_KEY_W)) {
			deltaZ += MOVE_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_S)) {
			deltaZ -= MOVE_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_A)) {
			//dRotY = -MAX_TURN_SPEED; //yaw
			dRotY = -MAX_TURN_SPEED;
		}

		if(keyboardHandler.isKeyDown(GLFW_KEY_D)) {
			//dRotY = MAX_TURN_SPEED;		//yaw
			dRotY = MAX_TURN_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_R)) {
			deltaY -= MOVE_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_F)) {
			deltaY += MOVE_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_UP)) {
			dRotX = MAX_TURN_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_DOWN)) {
			dRotX = -MAX_TURN_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_LEFT)) {
			dRotY = MAX_TURN_SPEED;
		}
		if(keyboardHandler.isKeyDown(GLFW_KEY_RIGHT)) {
			dRotY = -MAX_TURN_SPEED;
		}





		if(keyboardHandler.isKeyDown(GLFW_KEY_P)) {
			display.pause(true);
		}

	}


	public void move() {

		deltaX = -(float) (Math.sin(Math.toRadians(dRotX)) * MOVE_SPEED);
		deltaZ = -(float) (Math.cos(Math.toRadians(dRotY)) * MOVE_SPEED);
		deltaX += (float) (Math.cos(Math.toRadians(dRotX)) * MOVE_SPEED);
		deltaZ += -(float) (Math.sin(Math.toRadians(dRotZ)) * MOVE_SPEED);


	}

	public void update() {
		input();
		move();
		changePosition(deltaX, deltaY, deltaZ);
		changeRotation(dRotX, dRotY, dRotZ);
		dRotX = 0;
		dRotY = 0;
		dRotZ = 0;
	}

}
