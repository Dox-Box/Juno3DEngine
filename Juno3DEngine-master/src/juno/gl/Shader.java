package juno.gl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragShaderID;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); 
	
	public Shader(String vertexShaderFile, String fragShaderFile) {
		vertexShaderID = loadShader(vertexShaderFile,GL20.GL_VERTEX_SHADER);
		fragShaderID = loadShader(fragShaderFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	 
	protected abstract void bindAttributes();
	
	protected abstract void getAllUniformLocations();

			
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	private static int loadShader(String filePath, int type) {
		StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);	
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
	}

	protected void bindAttribute(int attribute, String varName) {
		 GL20.glBindAttribLocation(programID, attribute, varName);
		 
	}
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID,uniformName);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location,value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.get(matrixBuffer);
		//matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
		
	}
	
	protected void loadBoolean(int location, boolean value) {
		float toLoad = 0;
		if(value) {
			toLoad = 1;
		}
		GL20.glUniform1f(location, toLoad);
	}
	
	
	public void close() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragShaderID);
		GL20.glDeleteProgram(programID);
		
	}
	

}
