package mdnt.gl;

import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import org.joml.Vector3f;

import mdnt.core.IOHandler;
import mdnt.environ.GameObject;

/*
 * This class controls the view-matrix, or the perspective of the user in the 3D environment.
 */
public class Camera {

	private IOHandler keyboardHandler;
	private Vector3f position;
	private float roll;
	private float pitch;
	private float yaw;
	GameObject object;
	float deltaX;
	float deltaY;
	float deltaZ;
	private float MAX_ZOOM = 2;
	private float MIN_ZOOM = -2;
	private float camZoom = 0;
	private float theta;
	boolean debug = false;
	public boolean closeProgram = false;

	public Camera(GameObject object, Display display) {
		this.object = object;
		position = new Vector3f(0, 0, 0);
		roll = 0;
		pitch = 0;
		yaw = 0;
		keyboardHandler = display.getKeyboardHandler();
	}

	public float getCamZoom() {
		return camZoom;
	}

	public float getDeltaX() {
		return deltaX;
	}

	public float getDeltaY() {
		return deltaY;
	}

	public float getDeltaZ() {
		return deltaZ;
	}

	public IOHandler getKeyboardHandler() {
		return keyboardHandler;
	}

	public float getMAX_ZOOM() {
		return MAX_ZOOM;
	}

	public float getMIN_ZOOM() {
		return MIN_ZOOM;
	}

	public GameObject getObject() {
		return object;
	}

	public float getPitch() {
		return pitch;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRoll() {
		return roll;
	}

	public float getTheta() {
		return theta;
	}

	public float getYaw() {
		return yaw;
	}

	public boolean isDebug() {
		return debug;
	}

	public void move() {
		position.x = object.getPosition().x;
		position.y = object.getPosition().y;
		position.z = object.getPosition().z;

	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public boolean shouldCloseProgram() {
		return closeProgram;
	}

	public void update(Display display) {
		if (debug) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		}

		// deltaX = object.getPosition().x ;
		yaw = -object.getRotY();
		roll = -object.getRotZ();
		pitch = -object.getRotX();
		move();

	}

}
