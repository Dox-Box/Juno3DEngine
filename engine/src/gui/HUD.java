package gui;

public class HUD {


	public HUD() {
		//HUDTexture hudTexture = new HUDTexture(loader.loadTexture("hud1"), new Vector2f(1,1), new Vector2f(1,1));

	}











	public static float[] getHUDVertices() {
		float[] vertices = {
				-0.5f,  0.5f, 0,
				-0.5f, -0.5f, 0,
				0.5f, -0.5f, 0,
				0.5f,  0.5f, 0
		};
		return vertices;
	}
	public static int[] getHUDIndices() {
		int[] indices = {
				0,1,3,
				3,1,2
		};
		return indices;
	}

	public static float[] getHUDTexCoords() {
		float[] textureCoords = {
				0,0, //vertex 0
				0,1, //v1
				1,1, //v2
				1,0  //v3

		};
		return textureCoords;
	}



}
