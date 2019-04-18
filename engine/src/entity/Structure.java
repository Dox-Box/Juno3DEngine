package entity;

import org.joml.Vector3f;

import opengl.StaticMesh;

public class Structure extends Entity{

	public Structure(StaticMesh texturedObj, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(texturedObj, position, new Vector3f(rotX, rotY, rotZ), scale);

	}

}
