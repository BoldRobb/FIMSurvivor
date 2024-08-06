package elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TextoFlotante extends Texto {

	float duracion=0.5f;
	float timer=0;
	boolean terminado=false;
	
	public TextoFlotante(String fuente, int size, Color color, Vector2 pos, float duracion) {
		super(fuente, size, color, true);
		// TODO Auto-generated constructor stub
		super.setPosition(pos.x, pos.y);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fuente));
        FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = size;
        parameter.color = color;
        parameter.gamma = 0.5f;
          parameter.shadowColor = Color.BLACK;
            parameter.shadowOffsetX = 2;
            parameter.shadowOffsetY = 2;

        this.duracion=duracion;
        layout = new GlyphLayout();
        font = generator.generateFont(parameter);
        sr= new ShapeRenderer();
	}
	public void update(float delta) {
		timer+=delta;
		if(timer<duracion) {
			this.setPosition(this.getPosicion().x, this.getPosicion().y+2);
			
		}else {
			setTerminado(true);
		}
	}
	public boolean isTerminado() {
		return terminado;
	}
	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
}
