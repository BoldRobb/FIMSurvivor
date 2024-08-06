package utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.fimsurvivor.fimsurvivor;

public class Render {

	public static SpriteBatch batch;
	public static SpriteBatch batch2;
	public static fimsurvivor app;
	public static int PPM = 64;
	public static void LimpiarPantalla(float r, float g, float b) {
		ScreenUtils.clear(r, g, b, 1);
	}
	public static Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Musica/menuMusic.mp3"));
	public static Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Musica/gameMusic.mp3"));
	public static Sound clickEffect= Gdx.audio.newSound(Gdx.files.internal("Musica/menuSelect.mp3"));
	public static Sound hitEffect= Gdx.audio.newSound(Gdx.files.internal("Musica/hitEffect.mp3"));
}
