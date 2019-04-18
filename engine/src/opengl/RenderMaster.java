package opengl;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entity.Light;
import entity.Entity;
import entity.Skybox;
import utils.MathUtils;

public class RenderMaster {
	Display display;
	private static final float FOV = 60;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 30000f;

	protected Matrix4f projectionMatrix;
	protected BasicShader shader;
	private Map<StaticMesh, List<Entity>> allObjects = new HashMap<StaticMesh, List<Entity>>();
	private ArrayList<Light> lights;

	public RenderMaster(Display display, BasicShader shader) {
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
		GL11.glEnable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		StaticMesh mesh = object.getMesh();
		MeshData mData = mesh.getRawObj();
		GL30.glBindVertexArray(mData.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2); // <------
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, mData.getNumVertices(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(2); // <------
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
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
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawObj().getNumVertices(), GL11.GL_UNSIGNED_INT, 0);

			}
			unbindTexturedModel();
		}
	}

	public void prepTexturedModel(StaticMesh mesh) {
		MeshData obj = mesh.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Texture texture = mesh.getTexture();
		shader.loadSpecularVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getID());

	}

	public void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	public void prepInstance(Entity object) {
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
		shader.loadTransformationMatrix(transformationMatrix);
	}


	/* specific rendering for skybox */
	public void render(Skybox object,BasicShader shader) {
		//shader.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		StaticMesh mesh = object.getMesh();
		MeshData objData = mesh.getRawObj();
		GL30.glBindVertexArray(objData.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());

		shader.loadTransformationMatrix(transformationMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, objData.getNumVertices(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	//---------------------------------------------------------------------//

	public void render(Entity object,BasicShader shader) {
		GL11.glEnable(GL11.GL_DEPTH_TEST | GL11.GL_DEPTH_BUFFER_BIT);
		StaticMesh texturedObj = object.getMesh();
		MeshData obj = texturedObj.getRawObj();
		GL30.glBindVertexArray(obj.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(object.getPosition(), object.getRotX(),
				object.getRotY(),object.getRotZ(),object.getScale());
		Texture texture = object.getMesh().getTexture();
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
