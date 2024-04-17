package utiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.fimsurvivor.fimsurvivor;

public class Render {

	public static SpriteBatch batch;
	public static fimsurvivor app;
	
	public static void LimpiarPantalla(float r, float g, float b) {
		ScreenUtils.clear(r, g, b, 1);
	}
}
