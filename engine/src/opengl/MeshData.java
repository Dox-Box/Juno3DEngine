package opengl;

public class MeshData {
	private int vaoID;
	private int numVertices;

	public MeshData(int id, int numVertices) {
		this.vaoID = id;
		this.numVertices = numVertices;

	}

	public int getVaoID() {
		return vaoID;
	}

	public int getNumVertices() {
		return numVertices;
	}

}
