package luna.gl;

public class RawObj {
	private int vaoID; 
	private int numVertices;
	
	public RawObj(int id, int numVertices) {
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
