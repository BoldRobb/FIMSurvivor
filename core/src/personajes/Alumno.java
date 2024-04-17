package personajes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import utiles.Recursos;

public class Alumno extends Playable {
	ShapeRenderer sr;
	public Alumno(int x, int y, int ancho, int alto, float vida) {
		super(new Texture(Recursos.SpriteAlumno),x, y, ancho, alto, vida);
		sr= new ShapeRenderer();
		// TODO Auto-generated constructor stub
	}


}
