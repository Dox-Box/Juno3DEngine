package environment;

import org.joml.Vector3f;
import graphicsEngine.Obj;

public class Planet extends PhysicsObject {
	static final float bigG =  6.674 * Math.exp(10,-11);

	public Planet(Obj texturedObj, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(texturedObj, position, rotX, rotY, rotZ, scale);

	}

	
	
	
	public void setPlanetRotation(Vector3f vec) {
		super.setAngularVel(vec);
	}

        public float getGravAccel(float distance){
                (bigG * this.mass)/ (distance*distance);
        }
}
