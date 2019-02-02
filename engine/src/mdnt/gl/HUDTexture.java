package mdnt.gl;

import org.joml.Vector2f;

/*
 * This is a simple 2D texture class, more basic than Texture.java
 */

public class HUDTexture {

	private int textureID;
	private Vector2f position;
	private Vector2f scale;
	public HUDTexture(int textureID, Vector2f position, Vector2f scale) {
		super();
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
	}
	public int getTextureID() {
		return textureID;
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getScale() {
		return scale;
	}
	
	
	
	
}
