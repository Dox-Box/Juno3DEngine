package mdnt.environ;

import org.joml.Vector3f;

import mdnt.gl.Obj;
import mdnt.physics.BoundingSphere;

public interface Renderable {


	public void update();
	
	public Obj getTexturedObj();
	
	public void setTexturedObj(Obj texturedObj);
	
	public Vector3f getPosition();
	
	public void setPosition(Vector3f position);
	
	public float getRotX();
	
	public void setRotX(float rotX);
	
	public float getRotY();
	
	public void setRotY(float rotY);
	
	public float getRotZ();
	
	public void setRotZ(float rotZ);
	
	public float getScale();
	
	public void setScale(float scale);
	
}
