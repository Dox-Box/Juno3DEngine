package mdnt.gl;

import java.util.ArrayList;


import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

import mdnt.core.AssetLoader;
import mdnt.gui.GUIShader;
import mdnt.gui.HUD;

import static org.lwjgl.opengl.GL20.*;

public class RenderGUI {
	GUIShader shader;
	private RawObj GUIquad;
	
	public RenderGUI(AssetLoader loader) {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		GUIquad = loader.loadToVao(positions);
		shader = new GUIShader();
		
	}
	
	public void render(ArrayList<HUDTexture> hud) {
		shader.start();
		glBindVertexArray(GUIquad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(HUDTexture item : hud) {
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D,item.getTextureID());
			Matrix4f matrix = mdnt.util.MathUtils.createTransformationMatrix(item.getPosition(),item.getScale());
			shader.loadTransformation(matrix);
			glDrawArrays(GL_TRIANGLE_STRIP,0,GUIquad.getNumVertices());
		}
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shader.stop();
		
		
	}
	
	public void clearMemory() {
		shader.close();
	}
	
}
