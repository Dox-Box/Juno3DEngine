package opengl;

public class StaticMesh {

	private MeshData data;
	private  Texture texture;

	public StaticMesh(MeshData data, Texture texture) {
		this.data = data;
		this.texture = texture;
	}

	public MeshData getRawObj() {
		return data;
	}

	public Texture getTexture() {
		return texture;
	}



}
