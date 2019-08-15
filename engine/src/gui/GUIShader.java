package gui;

import org.joml.Matrix4f;

import opengl.Shader;

public class GUIShader extends Shader{


	private static final String VERTEX_FILE = "res/shaders/gui.vert";
	private static final String FRAG_FILE = "res/shaders/gui.frag";
	private int location_transformationMatrix;



	public GUIShader() {
		super(VERTEX_FILE, FRAG_FILE);
	}


	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
