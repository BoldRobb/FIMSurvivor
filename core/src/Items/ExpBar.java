package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import elementos.Bar;

public class ExpBar extends Bar{

	public ExpBar(float x, float y, int ancho, int alto) {
		super(x, y, ancho, alto);
		super.texturas[0] = new Texture("Items/ExpBar/EXP1.png");
		super.texturas[1] = new Texture("Items/ExpBar/EXP2.png");
		super.texturas[2] = new Texture("Items/ExpBar/EXP3.png");
		super.texturas[3] = new Texture("Items/ExpBar/EXP4.png");
		super.texturas[4] = new Texture("Items/ExpBar/EXP5.png");
		super.texturas[5] = new Texture("Items/ExpBar/EXP6.png");
		super.texturas[6] = new Texture("Items/ExpBar/EXP7.png");
		super.texturas[7] = new Texture("Items/ExpBar/EXP8.png");
		super.texturas[8] = new Texture("Items/ExpBar/EXP9.png");
		this.sr = new Sprite(texturas[0]);
		this.sr.setBounds(x, y, ancho, alto);
		this.setValorActual(0);
		// TODO Auto-generated constructor stub
	}
	
	public void update(Vector2 position, float currentXP) {
		this.setValorActual(currentXP);
		this.setPosition(position);
		float max = this.getValorMax();
		
		if(currentXP<max/9) {
			this.sr = new Sprite(texturas[0]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP> max/9) && (currentXP<((max/9)*2))) {
			this.sr = new Sprite(texturas[1]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP> ((max/9)*2)) && (currentXP<((max/9)*3))) {
			this.sr = new Sprite(texturas[2]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP<((max/9)*3)) && (currentXP<((max/9)*4))) {
			this.sr = new Sprite(texturas[3]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP<((max/9)*4)) && (currentXP<((max/9)*5))) {
			this.sr = new Sprite(texturas[4]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP<((max/9)*5)) && (currentXP<((max/9)*6))) {
			this.sr = new Sprite(texturas[5]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP<((max/9)*6)) && (currentXP<((max/9)*7))) {
			this.sr = new Sprite(texturas[6]);
			this.sr.setPosition(position.x, position.y);
		}else if ((currentXP<((max/9)*7)) && (currentXP<((max/9)*8))) {
			this.sr = new Sprite(texturas[7]);
			this.sr.setPosition(position.x, position.y);
		}else {
			this.sr = new Sprite(texturas[8]);
			this.sr.setPosition(position.x, position.y);
		}
		
	}

}
