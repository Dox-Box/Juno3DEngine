package juno.gl;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import juno.environ.Light;
import juno.environ.PhysicsObject;
import juno.environ.Skybox;
import juno.util.MathUtils;

public class OBJRender {
	Display display;
	private static final float FOV = 40;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 30000f;
	
	protected Matrix4f projectionMatrix;
	protected StaticShader shader;
	private Map<Obj, List<PhysicsObject>> allObjects = new HashMap<Obj, List<PhysicsObject>>();
	private ArrayList<Light> lights;
	
	public OBJRender(Display display, StaticShader shader) {
		this.display = display;
		this.shader = shader;
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BACK);
		createProjectionMatrix(); 
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		 shader.stop();
		 
	}
	
	public void clearDisplay() {
		display.update();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glEnable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void renderAllObjects(ArrayList<Light> lights, Camera camera, ArrayList<PhysicsObject> allObjects) {
		clearDisplay();
		shader.start();
		shader.loadLight(lights);
		shader.loadViewMatrix(camera); 	
		for(PhysicsObject object : allObjects) {
			render(object);
		}
		shader.stop(); 
		
	}
	
	
	/*--------------------*/
	
	public void render(PhysicsObject object) {
		GL11.glEnable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		Obj texturedObj = object.getTexturedObj();
		RawObj obj = texturedObj.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2); // <------
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		ObjTexture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedObj.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getNumVertices(), GL11.GL_UNSIGNED_INT, 0); 
		GL20.glDisableVertexAttribArray(2); // <------
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
		
	/* a batch rendering method. Use case would be if using a large number of models/textures that are the same. */
	public void batchRender(PhysicsObject object) {
		Obj model = object.getTexturedObj();
		List<PhysicsObject> batch = allObjects.get(model);
		if(batch != null) {
			batch.add(object);
		}else {
			List<PhysicsObject> newBatch = new ArrayList<PhysicsObject>();
			newBatch.add(object);
			allObjects.put(model, newBatch);
		}
	}
	
	
	/*batch method: */
	public void render(Map<Obj, List<PhysicsObject>> objects) {
		for(Obj model : objects.keySet()) {
			prepTexturedModel(model);
			List<PhysicsObject> renderBatch = objects.get(model);
			for(PhysicsObject object : renderBatch) {
				prepInstance(object);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawObj().getNumVertices(), GL11.GL_UNSIGNED_INT, 0); 

			}
			unbindTexturedModel();
		}
	}
	
	public void prepTexturedModel(Obj texturedObj) {
		RawObj obj = texturedObj.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ObjTexture texture = texturedObj.getTexture();
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedObj.getTexture().getID());
		
	}
	
	public void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void prepInstance(PhysicsObject object) {
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		ObjTexture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	
	/* specific rendering for skybox */ 
	public void render(Skybox object,StaticShader shader) {
		//shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		Obj texturedObj = object.getTexturedObj();
		RawObj obj = texturedObj.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		
		shader.loadTransformationMatrix(transformationMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedObj.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getNumVertices(), GL11.GL_UNSIGNED_INT, 0); 
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	//---------------------------------------------------------------------//
	
	public void render(PhysicsObject object,StaticShader shader) {
		GL11.glEnable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		Obj texturedObj = object.getTexturedObj();
		RawObj obj = texturedObj.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2); 
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		ObjTexture texture = object.getTexturedObj().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedObj.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getNumVertices(), GL11.GL_UNSIGNED_INT, 0); 
		GL20.glDisableVertexAttribArray(2); 
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	


	private void createProjectionMatrix () {
        float aspectRatio = (float) display.getWIDTH() / (float) display.getHEIGHT();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV /2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
       // projectionMatrix = new Matrix4f();
        projectionMatrix = new Matrix4f().perspective( (float) Math.toRadians(FOV), aspectRatio, NEAR_PLANE, FAR_PLANE);
   

	}



}
