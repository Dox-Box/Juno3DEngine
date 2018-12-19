package juno.environ;

import org.joml.Vector3f;

public class Light {

	private Vector3f position;
	private Vector3f color;
	
	public Light(Vector3f position, Vector3f color) {
		super();
		this.position = position;
		this.color = color;
	}
	
	public void changePosition(float dx, float dy, float dz) {
		position.x += dx;
		position.y += dy;
		position.z += dz;	
	}
	public Vector3f getPosition() {
		return position;
	}
	
	public void setColor(Vector3f color) {
		this.color = color;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
}
