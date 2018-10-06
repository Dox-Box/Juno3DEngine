package physicsEngine;

import java.util.ArrayList;

import org.joml.Vector3f;

import environment.PhysicsObject;

public class CollisionDetector {

	ArrayList<PhysicsObject> objects;
	Collider collider;
	
	public CollisionDetector(ArrayList<PhysicsObject> basicObjects) {
		objects = basicObjects;
		collider = new Collider();
	}
	
	public void update() {
		for(int i = 0; i < objects.size(); i++) {
			for(int j = 0; j < objects.size()-1; j++) {
				if(i!=j) {
				
				if(isIntersecting(objects.get(i).getBoundingSphere(),objects.get(j).getBoundingSphere())) {
					collider.sphereCollision(objects.get(i),objects.get(j));
					}
				}
			}
		}
	}
	
	
	public void updateObjectList(ArrayList<PhysicsObject> objects) {
		this.objects = objects;
	}
	
	public boolean isIntersecting(BoundingSphere sphere1, BoundingSphere sphere2) {
		
		float radiusDistance = sphere1.getRadius() + sphere2.getRadius();
		float centerDistance = calcTotalDistance(sphere1.getCenter(), sphere2.getCenter());
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
	
	
	
	
}
