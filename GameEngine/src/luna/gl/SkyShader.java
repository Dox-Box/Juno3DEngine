package luna.gl;

public class SkyShader extends Shader{

	private static String vertexShaderFile = "res/shaders/skyShader.vert";	
	private static String FragShaderFile = "res/shaders/skyShader.frag";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	
	public SkyShader(String vertexShaderFile, String fragShaderFile) {
		super(vertexShaderFile, fragShaderFile);

	}


	@Override
	protected void bindAttributes() {
		
	}


	@Override
	protected void getAllUniformLocations() {
		
	}

}
