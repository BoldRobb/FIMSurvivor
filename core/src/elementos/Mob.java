package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import utiles.Render;

public class Mob {

	public Texture textura;
	public Sprite spr;
	private float x, y, ancho, alto;

	float vida;
	
	
	public Mob (Texture textura, float x, float y, float ancho, float alto, float vida) {
		this.textura=textura;
		this.vida=vida;
		this.spr = new Sprite(textura);
		this.spr.setBounds(x, y, ancho, alto);
		this.ancho=ancho;
		this.alto=alto;
		this.x=x;
		this.y=y;
		
	}
	public void dibujar() {
		spr.draw(Render.batch);
		
	}
	public void setX(float x) {
		this.x=x;
		spr.setX(x);
	}
	public void setY(float y) {
		this.y=y;
		spr.setY(y);
	}
	public void setAncho(float ancho) {
		this.ancho=ancho;
		spr.setSize(ancho, this.alto);
	}
	public void setAlto(float alto) {
		this.alto=alto;
		spr.setSize(this.ancho, alto);
	}
	public void setVida(float vida) {
		this.vida=vida;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getAncho() {
		return ancho;
	}
	public float getAlto() {
		return alto;
	}
	public float getVida() {
		return vida;
	}
}
