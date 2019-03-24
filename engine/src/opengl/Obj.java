package opengl;

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
