package opengl;

import static org.lwjgl.opengl.GL11.GL_BACK;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;

import opengl.StaticShader;
import entity.Light;
import entity.Entity;
import entity.Skybox;
import utils.MathUtils;

public class ObjectRender {
	Display display;
	private static final float FOV = 40;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 30000f;

	protected Matrix4f projectionMatrix;
	protected StaticShader shader;
	private Map<StaticMesh, List<Entity>> allObjects = new HashMap<StaticMesh, List<Entity>>();
	private ArrayList<Light> lights;

	public ObjectRender(Display display, StaticShader shader) {
		this.display = display;
		this.shader = shader;
		glEnable(GL_CULL_FACE);
		glEnable(GL_BACK);
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();

	}

	public void clearDisplay() {
		display.update();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0, 0, 0, 1);
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
	}

	public void renderAllObjects(ArrayList<Light> lights, Camera camera, ArrayList<Entity> allObjects) {
		clearDisplay();
		shader.start();
		shader.loadLight(lights);
		shader.loadViewMatrix(camera);
		for(Entity object : allObjects) {
			render(object);
		}
		shader.stop();

	}


	/*--------------------*/

	public void render(Entity object) {
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		StaticMesh mesh = object.getMesh();
		MeshData objData = mesh.getRawObj();
		glBindVertexArray(objData.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2); // <------
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, mesh.getTexture().getID());
		glDrawElements(GL_TRIANGLES, objData.getNumVertices(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(2); // <------
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}

	/* a batch rendering method. Use case would be if using a large number of models/textures that are the same. */
	public void batchRender(Entity object) {
		StaticMesh model = object.getMesh();
		List<Entity> batch = allObjects.get(model);
		if(batch != null) {
			batch.add(object);
		}else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(object);
			allObjects.put(model, newBatch);
		}
	}


	/*batch method: */
	public void render(Map<StaticMesh, List<Entity>> objects) {
		for(StaticMesh model : objects.keySet()) {
			prepTexturedModel(model);
			List<Entity> renderBatch = objects.get(model);
			for(Entity object : renderBatch) {
				prepInstance(object);
				glDrawElements(GL_TRIANGLES, model.getRawObj().getNumVertices(), GL_UNSIGNED_INT, 0);

			}
			unbindTexturedModel();
		}
	}

	public void prepTexturedModel(StaticMesh mesh) {
		MeshData mData = mesh.getRawObj();
		glBindVertexArray(mData.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Texture texture = mesh.getTexture();
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, mesh.getTexture().getID());

	}

	public void unbindTexturedModel() {
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}

	public void prepInstance(Entity object) {
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
	}


	/* specific rendering for skybox */
	public void render(Skybox object,StaticShader shader) {
		//shader.start();
		glDisable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		StaticMesh texturedObj = object.getMesh();
		MeshData obj = texturedObj.getRawObj();
		glBindVertexArray(obj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());

		shader.loadTransformationMatrix(transformationMatrix);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturedObj.getTexture().getID());
		glDrawElements(GL_TRIANGLES, obj.getNumVertices(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}

	//---------------------------------------------------------------------//

	public void render(Entity object,StaticShader shader) {
		glEnable(GL_DEPTH_TEST | GL_DEPTH_BUFFER_BIT);
		StaticMesh texturedObj = object.getMesh();
		MeshData obj = texturedObj.getRawObj();
		glBindVertexArray(obj.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
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

		// projectionMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f().perspective( (float) Math.toRadians(FOV), aspectRatio, NEAR_PLANE, FAR_PLANE);


	}



}
