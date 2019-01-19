package luna.gl;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.*;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;


public class OBJLoader {
	
	
	private ArrayList<Integer> vaoIDs = new ArrayList<Integer>(); 
	private ArrayList<Integer> vboIDs = new ArrayList<Integer>(); /* mem. management */
	private ArrayList<Integer> textureIDs = new ArrayList<Integer>();
	
        /* level of detail, negative is better */	
	private int LOD = -1;
	
	
	/*
	 * VAO: vertex array object, VBO: vertex buffer object
	 */
	public RawObj loadToVao(float[] positions, int[] indices,float[] normals, float[] textureCoords ) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttribList(0,positions,3);
		storeDataInAttribList(1,textureCoords,2);
		storeDataInAttribList(2,normals,3);
		unbindVAO();
		return new RawObj(vaoID, indices.length); /* each vertex has 3 floats. */
	}
	
	public RawObj loadToVao(float[] positions) {
		int vaoID = createVAO();
		this.storeDataInAttribList(0, positions, 2);
		unbindVAO();
		return new RawObj(vaoID,positions.length/2); /* using GL_DRAW_ARRAYS */
		
	}
	private int createVAO() {
		int vaoID = glGenVertexArrays();
		vaoIDs.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void storeDataInAttribList(int attribNumber, float[] data, int dimensions) {
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		FloatBuffer  buffer = storeDataInFloatBuffer(data);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW); 
		glVertexAttribPointer(attribNumber, dimensions, GL_FLOAT, false,0,0);
		glBindBuffer(GL_ARRAY_BUFFER,  0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip(); /* buffer needs to have order of floats reversed to be read from */
		return buffer;
	}
	
        /* indices to draw from vertex positions, counterclockwise */
	private void bindIndicesBuffer(int[] indices) {
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer,GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer; 
	}
	
	
	private void unbindVAO() {
		glBindVertexArray(0); /* 0 un-binds the current VAO */
	}
	
	public int loadTexture(String filePath) {
		try {
		 File file = new File("res/"+filePath+".png");
		  InputStream in = new FileInputStream(file);
		  PNGDecoder decoder = new PNGDecoder(in);
		  ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		  decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
		  buffer.flip();
		  int textureID = glGenTextures();
		  glBindTexture(GL_TEXTURE_2D, textureID); 
		  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		  /* lowers texture resolution at distance from camera */
		  glGenerateMipmap(textureID);
		  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		  glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		  glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS,LOD);
		 
		  glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		  
		  glBindTexture(GL_TEXTURE_2D, 0);
		  textureIDs.add(textureID);
		  return textureID;
		}catch(Exception e) {
			System.err.println("Failed to load texture : " + filePath);
			clearData();
			System.exit(-1);
			
		}
		return 1;
	}
	
	
	
	public RawObj loadObjModel(String filePath) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+filePath+".obj"));
			
		} catch (FileNotFoundException e) {
			System.err.println("failed to load 3D model! (.obj) : " + filePath);
			e.printStackTrace();
			System.exit(-1);
		}
		BufferedReader reader = new BufferedReader(fr);
		String line; 
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] texturesArray = null;
		int[] indicesArray = null;
		try {
			
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), 
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}else if(line.startsWith("f ")) {
					texturesArray = new float[vertices.size()*2];
					normalsArray = new float[vertices.size()*3];
					break;
				} 
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				processVertex(vertex1,indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex2,indices, textures, normals, texturesArray, normalsArray);
				processVertex(vertex3,indices, textures, normals, texturesArray, normalsArray);
				
				line = reader.readLine();
			}
			
			reader.close();
			
		}catch(Exception e) {
			System.err.println("Invalid file format for .obj texture : "+filePath);
			e.printStackTrace();
		}
		
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		int vertexPointer = 0;
		
		for(Vector3f vertex : vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i<indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		return loadToVao(verticesArray, indicesArray, normalsArray,	 texturesArray);
		
	}
	
	private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> textures, ArrayList<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTexture.x;
		textureArray[currentVertexPointer*2 + 1] = 1 - currentTexture.y;

		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;


	
	}

	
	/* called when the program shuts down to free up memory */
	public void clearData() {
		for(int vao : vaoIDs) {
			glDeleteVertexArrays(vao);
		}
		for(int vbo : vboIDs) {
			glDeleteBuffers(vbo);
		}
		for(int texture : textureIDs) {
			glDeleteTextures(texture);
		}
		
	}
	
}
