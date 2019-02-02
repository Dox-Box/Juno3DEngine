package mdnt.gl;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import mdnt.core.IOHandler;
import mdnt.environ.GameObject;

import static org.lwjgl.glfw.GLFW.*;

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
		position = new Vector3f(0,0,0);
		roll = 0;
		pitch = 0; 
		yaw = 0;	
		this.keyboardHandler = (IOHandler) display.getKeyboardHandler();
	}
	
	public void update(Display display) {
			if(debug) {
			 GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			}

			//deltaX = object.getPosition().x ;
			yaw = -object.getRotY();
			roll = -object.getRotZ();
			pitch = -object.getRotX();
			move();
			

		}
	
	public void move() {
		position.x = object.getPosition().x;
		position.y = object.getPosition().y;
		position.z = object.getPosition().z;
		
	}
	
	

	public IOHandler getKeyboardHandler() {
		return keyboardHandler;
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRoll() {
		return roll;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public GameObject getObject() {
		return object;
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

	public float getMAX_ZOOM() {
		return MAX_ZOOM;
	}

	public float getMIN_ZOOM() {
		return MIN_ZOOM;
	}

	public float getCamZoom() {
		return camZoom;
	}

	public float getTheta() {
		return theta;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean shouldCloseProgram() {
		return closeProgram;
	}
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
