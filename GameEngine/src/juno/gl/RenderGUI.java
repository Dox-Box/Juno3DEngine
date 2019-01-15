package juno.gl;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import juno.core.AssetLoader;
import juno.gui.GUIShader;
import juno.gui.HUD;

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
		GL30.glBindVertexArray(GUIquad.getVaoID());
		glEnableVertexAttribArray(0);
		glEnable(GL11.GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for(HUDTexture item : hud) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,item.getTextureID());
			Matrix4f matrix = juno.util.MathUtils.createTransformationMatrix(item.getPosition(),item.getScale());
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP,0,GUIquad.getNumVertices());
		}
		GL11.glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
		
		
	}
	
	public void clearMemory() {
		shader.close();
	}
	
}
