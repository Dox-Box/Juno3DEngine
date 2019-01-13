package juno.environ;

import org.joml.Vector3f;

import juno.gl.Obj;

public class Terrain implements Renderable {

	private Obj texturedObj;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;

	
	public void update() {
		
	}
	
	public void loadTerrainMap(String filePath) {
		
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
}
