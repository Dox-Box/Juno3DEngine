package environment;

import org.joml.Vector3f;
import graphicsEngine.Obj;

public class Planet extends PhysicsObject {
	
	public Planet(Obj texturedObj, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(texturedObj, position, rotX, rotY, rotZ, scale);

	}

	
	
	
	public void setPlanetRotation(Vector3f vec) {
		super.setAngularVel(vec);
	}
}
