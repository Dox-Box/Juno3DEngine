package luna.gl;

/*
 *  Obj: responsible for storing the mesh data for .obj filetype objects,
 *  Since rendering can in theory support multiple file types, but each loaded in differently. 
 */
public class Obj {

	private RawObj raw;
	private  ObjTexture texture;
	
	public Obj(RawObj raw, ObjTexture texture) {
		this.raw = raw;
		this.texture = texture;
	}

	public RawObj getRawObj() {
		return raw;
	}

	public ObjTexture getTexture() {
		return texture;
	}
	
	
	
}
