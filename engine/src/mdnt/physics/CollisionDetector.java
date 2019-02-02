package mdnt.physics;

import java.util.ArrayList;

import org.joml.Vector3f;

import mdnt.environ.GameObject;

public class CollisionDetector {

	ArrayList<GameObject> objects;
	Collider collider;
	
	public CollisionDetector(ArrayList<GameObject> basicObjects) {
		objects = basicObjects;
		collider = new Collider();
	}
	
	/*
	 * check all objects (excluding themselves) to see if the sphere boundary around them is intersecting any other object.
	 */
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
	
	
	public void updateObjectList(ArrayList<GameObject> objects) {
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
