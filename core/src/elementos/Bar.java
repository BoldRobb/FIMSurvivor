package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import utiles.Render;

public class Bar {
	protected Sprite sr;
	protected Texture[] texturas;
	float x, y;
	
	int ancho, alto;
	Vector2 position;
	protected float valorMax, valorActual;
	
	
	public float getValorActual() {
		return valorActual;
	}
	public void setValorActual(float valorActual) {
		this.valorActual = valorActual;
	}
	public Bar(float x, float y, int ancho, int alto) {
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
		this.setPosition(new Vector2(x,y));
		this.texturas = new Texture[9];
	}
	public float getX() {
		return x;
	}
	public void dibujar() {
		sr.draw(Render.batch);
		
	}
	public void setX(float x) {
		this.x = x;
		this.setPosition(new Vector2(x,this.y));
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		this.setPosition(new Vector2(this.x,y));
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.x=position.x;
		this.y=position.y;
		this.position = position;
	}
	public float getValorMax() {
		return valorMax;
	}
	public void setValorMax(float valorMax) {
		this.valorMax = valorMax;
	}
	public void update(Vector2 position) {
		this.setPosition(position);
		
	}

}
