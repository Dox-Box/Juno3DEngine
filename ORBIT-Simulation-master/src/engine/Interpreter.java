package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.joml.Vector3f;

import environment.PhysObject;
import environment.Light;
import environment.Planet;
import environment.Focus;
import environment.Skybox;
import graphicsEngine.Camera;
import graphicsEngine.AssetLoader;
import graphicsEngine.Obj;
import graphicsEngine.ObjTexture;
import graphicsEngine.RawObj;


public class Interpreter {
	Scanner scan;
	ArrayList<String[]> physicsData = new ArrayList<String[]>();
	ArrayList<PhysObject> physicsObjects = new ArrayList<PhysObject>();
	ArrayList<Light> lights = new ArrayList<Light>();
	RawObj rawPlayerModel;
	ObjTexture playerTex;
	Focus player;
	ObjTexture skyboxTex;
	Skybox skybox;
	Skybox skybox2;
	Camera camera;
	
	private static int index = 0;
	
	
	public void loadMapData(String filePath, AssetLoader objLoader) {
		String line;
		try {
			scan = new Scanner(new File("res/tests/"+filePath+".juno"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Missing configuration file, failed to load : " + filePath);
			
		}

		while(scan.hasNextLine()) {
			line = scan.nextLine();
			if(line.length() == 0) {
				continue;
				}
			if(line.charAt(0) == '#') {
				continue;
			}
			
			String[] data = line.split(" ");
			if(data[0].equals("light")) {
				float x = Float.parseFloat(data[1]);
				float y = Float.parseFloat(data[2]);
				float z = Float.parseFloat(data[3]);
				float r = Float.parseFloat(data[4]);
				float g = Float.parseFloat(data[5]);
				float b = Float.parseFloat(data[6]);
				lights.add( new Light(new Vector3f(x,y,z),new Vector3f(r,g,b)));
			}
			if(data[0].equals("skybox")) {
				RawObj raw = objLoader.loadToVao(Skybox.getVertexData(), Skybox.getIndicesData(), Skybox.getNormalData(), Skybox.getTextureData());
				ObjTexture skyTex = new ObjTexture(objLoader.loadTexture(data[1]));
				float x = Float.parseFloat(data[2]);
				float y = Float.parseFloat(data[3]);
				float z = Float.parseFloat(data[4]);
				float rx = Float.parseFloat(data[5]);
				float ry = Float.parseFloat(data[6]);
				float rz = Float.parseFloat(data[7]);
				float sc = Float.parseFloat(data[8]);
				if(skybox == null) {
					skybox = new Skybox(new Obj(raw,skyTex),new Vector3f(x,y,z),rx,ry,rz,sc);
				}else {	
					skybox2 = new Skybox(new Obj(raw,skyTex),new Vector3f(x,y,z),rx,ry,rz,sc);
				}
			}
			
			if(data[0].equals("object")) {
				RawObj raw = objLoader.loadObjModel(data[1]);
				ObjTexture skyTex = new ObjTexture(objLoader.loadTexture(data[2]));
				float x = Float.parseFloat(data[3]);
				float y = Float.parseFloat(data[4]);
				float z = Float.parseFloat(data[5]);
				float rx = Float.parseFloat(data[6]);
				float ry = Float.parseFloat(data[7]);
				float rz = Float.parseFloat(data[8]);
				float sc = Float.parseFloat(data[9]);
				physicsObjects.add(new PhysObject(new Obj(raw,skyTex),new Vector3f(x,y,z),rx,ry,rz,sc));

			}
			
			if(data[0].equals("phy")) {
					float mass = Float.parseFloat(data[1]);
					float xVelo = Float.parseFloat(data[2]);
					float yVelo = Float.parseFloat(data[3]);
					float zVelo = Float.parseFloat(data[4]);
					/*angular speed*/
					float angRotX = Float.parseFloat(data[5]);
					float angRotY = Float.parseFloat(data[6]);
					float angRotZ = Float.parseFloat(data[7]);
					physicsData.add(data);
					
			}

					
		}
	}
	
	
	
	public Scanner getScan() {
		return scan;
	}


	public ArrayList<String[]> getPhysicsData() {
		return physicsData;
	}



	public ArrayList<PhysObject> getPhysicsObjects() {
		return physicsObjects;
	}



	public ArrayList<Light> getLights() {
		return lights;
	}




	public ObjTexture getSkyboxTex() {
		return skyboxTex;
	}



	public Skybox getSkybox() {
		return skybox;
	}



	public Skybox getSkybox2() {
		return skybox2;
	}



	public Camera getCamera() {
		return camera;
	}

	public static int getIndex() {
		return index;
	}



	public void link() {
		for(int i = 0; i< physicsObjects.size(); i++) {
			String[] data = physicsData.get(i);
			if(data.length < 8 || physicsObjects.get(i) == null) {
				System.err.println("mismatch physics data in file!");
			}
			float mass = Float.parseFloat(data[1]);
			float xVelo = Float.parseFloat(data[2]);
			float yVelo = Float.parseFloat(data[3]);
			float zVelo = Float.parseFloat(data[4]);
			/*angular speed*/
			float angRotX = Float.parseFloat(data[5]);
			float angRotY = Float.parseFloat(data[6]);
			float angRotZ = Float.parseFloat(data[7]);
		physicsObjects.get(i).setMass(mass);
		physicsObjects.get(i).setVelocity(new Vector3f(xVelo,yVelo,zVelo));
		physicsObjects.get(i).setAngularVel(new Vector3f(angRotX,angRotY,angRotZ));
		
		}
	}
	
	
}
			
	

	
	

