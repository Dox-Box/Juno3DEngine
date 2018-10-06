package graphicsEngine;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import environment.Light;
import utils.MathUtils;

public class StaticShader extends Shader {

	private static String vertexShaderFile = "src/graphicsEngine/vertexShader.vert";	
	private static String FragShaderFile = "src/graphicsEngine/fragmentShader.frag";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_shineDamper;
	private int location_reflectivity;	
	private int NUM_LIGHTS = 2;

	
	public StaticShader() {
		super(vertexShaderFile, FragShaderFile);
	}
	protected void bindAttributes() {
		super.bindAttribute(0,"position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
		location_lightPosition = new int[NUM_LIGHTS];
		location_lightColor = new int[NUM_LIGHTS];
	}
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		
		location_lightPosition = new int[NUM_LIGHTS];
		location_lightColor = new int[NUM_LIGHTS];
		for(int i=0; i<NUM_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition["+i+"]"); 
			location_lightColor[i] = super.getUniformLocation("lightColor["+i+"]"); 

		}
				
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f view = MathUtils.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, view);
	}
	
	public void loadLight(ArrayList<Light> lights) {
			location_lightPosition = new int[NUM_LIGHTS];
			location_lightColor = new int[NUM_LIGHTS];
			super.loadVector(location_lightPosition[0], lights.get(0).getPosition());
			super.loadVector(location_lightColor[0], lights.get(0).getColor());
			
			super.loadVector(location_lightPosition[1], lights.get(1).getPosition());
			super.loadVector(location_lightColor[1], lights.get(1).getColor());

			
			
		}
	
	public void loadSpecularVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
}	
