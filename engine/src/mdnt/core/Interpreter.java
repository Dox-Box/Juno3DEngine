package mdnt.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.joml.Vector3f;

import mdnt.environ.GameObject;
import mdnt.environ.Light;
import mdnt.environ.Planet;
import mdnt.environ.Player;
import mdnt.environ.Skybox;
import mdnt.gl.Camera;
import mdnt.gl.Obj;
import mdnt.gl.RawObj;
import mdnt.gl.Texture;


public class Interpreter {
	Scanner scan;
	ArrayList<String[]> physicsData = new ArrayList<String[]>();
	ArrayList<GameObject> physicsObjects = new ArrayList<GameObject>();
	ArrayList<Light> lights = new ArrayList<Light>();
	RawObj rawPlayerModel;
	Texture playerTex;
	Player player;
	Texture skyboxTex;
	Skybox skybox;
	Skybox skybox2;
	Camera camera;
	
	private static int index = 0;
	private static int lineNum;
	
	
	public void loadMapData(String filePath, AssetLoader objLoader) {
		String line;
		lineNum = 0;
		try {
			scan = new Scanner(new File("res/script/"+filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Missing configuration file, failed to load : " + filePath);
			
		}

		while(scan.hasNextLine()) {
			lineNum++;
			line = scan.nextLine();
			if(line.length() == 0) {
				continue;
				}
			if(line.charAt(0) == '#') {
				continue;
			}
			
			String[] data = line.split(" ");
			if(data[0].equals("Light")) {
				float x = Float.parseFloat(data[1]);
				float y = Float.parseFloat(data[2]);
				float z = Float.parseFloat(data[3]);
				float r = Float.parseFloat(data[4]);
				float g = Float.parseFloat(data[5]);
				float b = Float.parseFloat(data[6]);
				lights.add( new Light(new Vector3f(x,y,z),new Vector3f(r,g,b)));
				continue;
			}
			if(data[0].equals("Skybox")) {
				RawObj raw = objLoader.loadToVao(Skybox.getVertexData(), Skybox.getIndicesData(), Skybox.getNormalData(), Skybox.getTextureData());
				Texture skyTex = new Texture(objLoader.loadTexture("textures/" + data[1]));
				float x = Float.parseFloat(data[2]);
				float y = Float.parseFloat(data[3]);
				float z = Float.parseFloat(data[4]);
				float rx = Float.parseFloat(data[5]);
				float ry = Float.parseFloat(data[6]);
				float rz = Float.parseFloat(data[7]);
				float sc = Float.parseFloat(data[8]);
				if(skybox == null) {
					skybox = new Skybox(new Obj(raw,skyTex),new Vector3f(x,y,z),rx,ry,rz,sc);
					continue;
				}else {	
					skybox2 = new Skybox(new Obj(raw,skyTex),new Vector3f(x,y,z),rx,ry,rz,sc);
					continue;
				}
			}
			
			if(data[0].equals("Object")) {
				RawObj raw = objLoader.loadObjModel("objs/" + data[1]);
				Texture tex = new Texture(objLoader.loadTexture("textures/" + data[2]));
				float x = Float.parseFloat(data[3]);
				float y = Float.parseFloat(data[4]);
				float z = Float.parseFloat(data[5]);
				float rx = Float.parseFloat(data[6]);
				float ry = Float.parseFloat(data[7]);
				float rz = Float.parseFloat(data[8]);
				float sc = Float.parseFloat(data[9]);
				physicsObjects.add(new GameObject(new Obj(raw, tex),new Vector3f(x,y,z),rx,ry,rz,sc));
				continue;
			}
			
			if(data[0].equals("Phy")) {
					float mass = Float.parseFloat(data[1]);
					float xVelo = Float.parseFloat(data[2]);
					float yVelo = Float.parseFloat(data[3]);
					float zVelo = Float.parseFloat(data[4]);
					/*angular speed*/
					float angRotX = Float.parseFloat(data[5]);
					float angRotY = Float.parseFloat(data[6]);
					float angRotZ = Float.parseFloat(data[7]);
					physicsData.add(data);
					continue;
			}
		
			
			System.err.println("Unidentified type : '" + data[0] + "' while loading file : " + filePath + ".luna (line " + lineNum + ")");
					
		}
	}
	

	public ArrayList<String[]> getGameObjectsData() {
		return physicsData;
	}



	public ArrayList<GameObject> getGameObjects() {
		return physicsObjects;
	}


	public ArrayList<Light> getLights() {
		return lights;
	}
	


	public Texture getSkyboxTex() {
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
				System.err.println("missing entry for physics data types in script file!");
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
		
	

