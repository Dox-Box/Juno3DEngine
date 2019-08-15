package opengl;




public class Texture {

	private int textureID;
	private float shineDamper = 0.1f;
	private float reflectivity = 0.9f;

	public Texture(int id) {
		textureID = id;
	}


	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public int getID() {
		return textureID;
	}
	
	
	
}

