package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import utiles.Render;

public class Imagen {

	private Texture t;
	private Sprite s;
	private float x,y;
	private float alto, ancho;
	private Vector2 esqAbajoIzq, esqArribaDer;
	private boolean clicked = false;


	public Imagen(String ruta) {
		t = new Texture (ruta);
		s = new Sprite(t);
	}
	public Imagen(String ruta, float ancho, float alto) {
		t = new Texture (ruta);
		s = new Sprite(t);
		s.setSize(ancho, alto);
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public void setTextura(String Ruta) {
		Texture nt = new Texture(Ruta);
		this.s.setTexture(nt);
	}
	
	public void setSize(float ancho, float alto) {
		this.alto= alto;
		this.ancho = ancho;
		s.setSize(ancho, alto);
	}
	public void dibujar() {
		s.draw(Render.batch);
	}
	public void setAlpha(float a) {
		s.setAlpha(a);
	}
	public void setPosition(float x, float y) {
		s.setPosition(x, y);
		this.x=x;
		this.y=y;
	}
	public Vector2 getEsqAbajoIzq() {
		return new Vector2(x,y);
	}
	public Vector2 getEsqArribaDer() {
		return new Vector2(x+getAncho(),y+getAlto());
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		s.setPosition(x, this.y);
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		s.setPosition(this.x, y);
		this.y = y;
	}
	public float getAlto() {
		return alto;
	}
	public void setAlto(float alto) {
		s.setSize(this.ancho, alto);
		this.alto = alto;
	}
	public float getAncho() {
		return ancho;
	}
	public void setAncho(float ancho) {
		s.setSize(ancho, this.alto);
		this.ancho = ancho;
	}
}
