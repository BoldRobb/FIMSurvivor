package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import elementos.Bar;

public class LifeBar extends Bar {

	public LifeBar(float x, float y, int ancho, int alto) {
		super(x, y, ancho, alto);
		super.texturas[0] = new Texture("Items/LifeBar/LIFE1.png");
		super.texturas[1] = new Texture("Items/LifeBar/LIFE2.png");
		super.texturas[2] = new Texture("Items/LifeBar/LIFE3.png");
		super.texturas[3] = new Texture("Items/LifeBar/LIFE4.png");
		super.texturas[4] = new Texture("Items/LifeBar/LIFE5.png");
		super.texturas[5] = new Texture("Items/LifeBar/LIFE6.png");
		super.texturas[6] = new Texture("Items/LifeBar/LIFE7.png");
		super.texturas[7] = new Texture("Items/LifeBar/LIFE8.png");
		super.texturas[8] = new Texture("Items/LifeBar/LIFE9.png");
		this.sr = new Sprite(texturas[8]);
		this.sr.setBounds(x, y, ancho, alto);
		
		// TODO Auto-generated constructor stub
	}
	public void update(Vector2 position, float valor) {
		this.setValorActual(valor);
		this.setPosition(position);
		float max = this.getValorMax();
		
		if(valor<max/9) {
			this.sr = new Sprite(texturas[0]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor> max/9) && (valor<((max/9)*2))) {
			this.sr = new Sprite(texturas[1]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor> ((max/9)*2)) && (valor<((max/9)*3))) {
			this.sr = new Sprite(texturas[2]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor<((max/9)*3)) && (valor<((max/9)*4))) {
			this.sr = new Sprite(texturas[3]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor<((max/9)*4)) && (valor<((max/9)*5))) {
			this.sr = new Sprite(texturas[4]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor<((max/9)*5)) && (valor<((max/9)*6))) {
			this.sr = new Sprite(texturas[5]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor<((max/9)*6)) && (valor<((max/9)*7))) {
			this.sr = new Sprite(texturas[6]);
			this.sr.setPosition(position.x, position.y);
		}else if ((valor<((max/9)*7)) && (valor<((max/9)*8))) {
			this.sr = new Sprite(texturas[7]);
			this.sr.setPosition(position.x, position.y);
		}else {
			this.sr = new Sprite(texturas[8]);
			this.sr.setPosition(position.x, position.y);
		}
		
	}
	

}
