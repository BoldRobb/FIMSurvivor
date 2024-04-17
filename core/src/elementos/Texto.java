package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import utiles.Render;

public class Texto {
	BitmapFont font;
	private float x = 0, y = 0;
	private String texto = "";
	GlyphLayout layout;
	ShapeRenderer sr;
	private Vector2 esqAbajoIzq, esqArribaDer;
	public Texto(String fuente, int size, Color color, boolean sombra) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fuente));
        FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;
        parameter.gamma = 0.5f;
        if(sombra) {
            parameter.shadowColor = Color.BLACK;
            parameter.shadowOffsetX = 2;
            parameter.shadowOffsetY = 2;

        }
        layout = new GlyphLayout();
        font = generator.generateFont(parameter);
        sr= new ShapeRenderer();
	}
	
	public void dibujar() {
		font.draw(Render.batch, texto, x, y);
	}

	public void debug() {
		sr.begin(ShapeType.Line);
			sr.setColor(Color.GOLD);
			sr.rect(x, y-getAlto(), getAncho(), getAlto());
		sr.end();
		
	}
	public Vector2 getEsqAbajoIzq() {
		return new Vector2(x,y-getAlto());
	}
	public Vector2 getEsqArribaDer() {
		return new Vector2(x+getAncho(),y);
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
		layout.setText(font, texto);
	}
	public void setColor(Color color) {
		font.setColor(color);
		
	}
	public float getAncho() {
		return layout.width;
	}
	public float getAlto() {
		return layout.height;
	}
	public Vector2 getDimension() {
		return new Vector2(layout.width, layout.height);
	}
	public Vector2 getPosicion() {
		return new Vector2(this.x, this.y);
	}
}
