package mdnt.physics;

import org.joml.Vector3f;

public class BoundingSphere {

	
	private Vector3f velocity;
	private Vector3f center;
	private float radius;
	private float deltaDistance;
	public BoundingSphere(Vector3f center, float radius) {
		this.center = center;
		this.radius = radius*4.5f;
	}
	
	
	
	
	public boolean isIntersectingSphere(BoundingSphere sphere2) {
		
		float radiusDistance = this.radius + sphere2.getRadius();
		float centerDistance = calcTotalDistance(center, sphere2.getCenter());
		deltaDistance = centerDistance - radiusDistance;
		if(centerDistance < radiusDistance) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	
	private float calcTotalDistance(Vector3f a, Vector3f b){
		float dx = b.x - a.x;
		float dy = b.y - a.y;
		float dz = b.z - a.z;
		return (float) Math.sqrt((dx*dx) + (dy*dy) + dz*dz);
	
	}
	
	public float deltaDistance() {
		return deltaDistance;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Vector3f getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public Vector3f getCenter() {
		return center;
	}
	public void setCenter(Vector3f center) {
		this.center = center;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}




