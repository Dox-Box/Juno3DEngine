package luna.environ;

import org.joml.Vector3f;

import luna.gl.Obj;
import luna.physics.BoundingSphere;

public class GameObject implements Renderable {

	private Obj texturedObj;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float deltaX,deltaY,deltaZ;
	private float scale;
	private BoundingSphere boundingSphere;
	private Vector3f velocity;
	private Vector3f angularVel;
	private float kineticEnergy;
	private float mass;

	
	public GameObject(Obj texturedObj, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.texturedObj = texturedObj;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.velocity = new Vector3f(0,0,0);
		this.angularVel = new Vector3f(0,0,0);
		this.mass = 0;
		deltaX = 0;
		deltaY = 0;
		deltaZ = 0;
		boundingSphere = new BoundingSphere(position, scale);
		
	}
	
	public void update() {}
	
	public void update(float dt) {
		deltaX = velocity.x * dt;
		deltaY = velocity.y * dt;
		deltaZ = velocity.z * dt;
		float dRotX = angularVel.x * dt;
		float dRotY = angularVel.y * dt;
		float dRotZ = angularVel.z * dt;
		changePosition(deltaX, deltaY, deltaZ);
		changeRotation(dRotX,dRotY,dRotZ);
		
	}
	
	
	
	public void changePosition(float dx,float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
		
	}
	
	public void changeRotation(float rx, float ry, float rz) {
		rotX += rx;
		rotY += ry;
		rotZ += rz;
		
	}
	
	public void addVelocity(Vector3f vec) {
		this.velocity.add(vec);
	}
	
	public void addAngularVel(Vector3f vec) {
		angularVel.add(vec);
	}
	
	public void setAngularVel(Vector3f vec) {
		this.angularVel = vec;
	}
	public Vector3f getAngularVel() {
		return angularVel;
	}
	public void setVelocity(Vector3f vec) {
		this.velocity = vec;
	}
	public Vector3f getVelocity() {
		return velocity;
	}
	
	public void setMass(float m) {
		this.mass = m;
	}
	
	public float getMass() {
		return mass;
	}
	
		

	
	public Obj getTexturedObj() {
		return texturedObj;
	}
	public void setTexturedObj(Obj texturedObj) {
		this.texturedObj = texturedObj;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public float getRotX() {
		return rotX;
	}
	public void setRotX(float rotX) {
		this.rotX = rotX;
	}
	public float getRotY() {
		return rotY;
	}
	public void setRotY(float rotY) {
		this.rotY = rotY;
	}
	public float getRotZ() {
		return rotZ;
	}
	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}


	public float getRadius() {
		return scale*4.5f;
	}
	
	
	
	public BoundingSphere getBoundingSphere() {
		return boundingSphere;
	}
	
	
	
	
}
