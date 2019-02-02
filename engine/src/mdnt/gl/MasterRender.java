package mdnt.gl;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import mdnt.environ.GameObject;
import mdnt.environ.Light;
import mdnt.environ.Skybox;
import mdnt.util.MathUtils;

public class MasterRender {
	Display display;
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 30000f;
	
	protected Matrix4f projectionMatrix;
	protected StaticShader shader;
	private Map<Obj, List<GameObject>> allObjects = new HashMap<Obj, List<GameObject>>();
	private ArrayList<Light> lights;
	
	
	public MasterRender(Display display, StaticShader shader) {
		this.display = display;
		this.shader = shader;
		glEnable(GL_CULL_FACE);
		glEnable(GL_BACK);
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);

		createProjectionMatrix(); 
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		 shader.stop();
		 
	}
	
	public void clearDisplay() {
		display.update();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 0, 1);
	}
	
	public void renderAllObjects(ArrayList<Light> lights, Camera camera, ArrayList<GameObject> allObjects) {
		clearDisplay();
		shader.start();
		shader.loadLight(lights);
		shader.loadViewMatrix(camera); 	
		for(GameObject object : allObjects) {
			render(object);
		}
		shader.stop(); 
		
	}
	
	
	/*--------------------*/
	
	public void render(GameObject object) {
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		Obj texturedObj = object.getTexturedObj();
		RawObj obj = texturedObj.getRawObj();
		glBindVertexArray(obj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2); // <------
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedObj.getTexture().getID());
		glDrawElements(GL_TRIANGLES, obj.getNumVertices(), GL_UNSIGNED_INT, 0); 
		glDisableVertexAttribArray(2); // <------
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
		
	/* a batch rendering method. Use case would be if using a large number of models/textures that are the same. (particle effects) */
	public void batchRender(GameObject object) {
		Obj model = object.getTexturedObj();
		List<GameObject> batch = allObjects.get(model);
		if(batch != null) {
			batch.add(object);
		}else{
			List<GameObject> newBatch = new ArrayList<GameObject>();
			newBatch.add(object);
			allObjects.put(model, newBatch);
		}
	}
	
	
	/*batch method: */
	public void render(Map<Obj, List<GameObject>> objects) {
		for(Obj model : objects.keySet()) {
			prepTexturedModel(model);
			List<GameObject> renderBatch = objects.get(model);
			for(GameObject object : renderBatch) {
				prepInstance(object);
				glDrawElements(GL_TRIANGLES, model.getRawObj().getNumVertices(), GL_UNSIGNED_INT, 0); 

			}
			unbindTexturedModel();
		}
	}
	
	public void prepTexturedModel(Obj object) {
		RawObj obj = object.getRawObj();
		glBindVertexArray(obj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Texture texture = object.getTexture();
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, object.getTexture().getID());
		
	}
	
	public void unbindTexturedModel() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
	
	public void prepInstance(GameObject object) {
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	
	/* special case rendering for skybox */ 
	public void renderSky(Skybox object) {
		glDisable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		System.out.println("Depth disabled");
		Obj texturedObj = object.getTexturedObj();
		RawObj rawObj = texturedObj.getRawObj();
		glBindVertexArray(rawObj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		glDrawElements(GL_TRIANGLES, rawObj.getNumVertices(), GL_UNSIGNED_INT, 0); 

		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedObj.getTexture().getID());
		glDrawElements(GL_TRIANGLES, rawObj.getNumVertices(), GL_UNSIGNED_INT, 0); 
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);

	}
	
	//---------------------------------------------------------------------//
	
	public void render(GameObject object,StaticShader shader) {
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		Obj texturedObj = object.getTexturedObj();
		RawObj obj = texturedObj.getRawObj();
		glBindVertexArray(obj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2); 
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedObj.getTexture().getID());
		glDrawElements(GL_TRIANGLES, obj.getNumVertices(), GL_UNSIGNED_INT, 0); 
		glDisableVertexAttribArray(2); 
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
	


	private void createProjectionMatrix () {
        float aspectRatio = (float) display.getWIDTH() / (float) display.getHEIGHT();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV /2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f().perspective( (float) Math.toRadians(FOV), aspectRatio, NEAR_PLANE, FAR_PLANE);
   

	}



}
