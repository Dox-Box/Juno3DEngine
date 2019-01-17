package luna.physics;

import org.joml.Vector3f;

import luna.environ.GameObject;

/* handles a collision detection */
public class Collider {
	
	/* Collision Response  */
	public void sphereCollision(GameObject a, GameObject b){
		float dMomentum = a.getMass() * a.getVelocity().length() - b.getMass()*b.getVelocity().length();
		Vector3f vec1= a.getVelocity().mul(a.getMass());
		Vector3f vec2 = b.getVelocity().mul(b.getMass());
		Vector3f vec3 = vec1.add(vec2);
		vec1.add(vec3.div(a.getMass()+b.getMass())).mul(2);
		vec2.add(vec3.div(a.getMass()+b.getMass())).mul(-1);
		b.setVelocity(vec2);
		a.setVelocity(vec1);
		//a.addAngularVel(new Vector3f(-9,8,8));
		vec1 = a.getAngularVel().mul(a.getMass());
		vec2 = b.getAngularVel().mul(b.getMass());
		vec3 = vec1.add(vec2);
		vec1.add(vec3.mul(-1));
		a.setAngularVel(vec1);
		vec2.add(vec3.div(a.getMass()+b.getMass())).negate();
		b.setAngularVel(vec2);
	    
		
	}
	

}
