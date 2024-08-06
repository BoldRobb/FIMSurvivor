package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import elementos.Pickable;
import utiles.Recursos;

public class LEDexp extends Pickable {
	float exp;
	public float getExp() {
		return exp;
	}

	public void setExp(float exp) {
		this.exp = exp;
	}

	public LEDexp(float x, float y, World mundo, String color) {
		if(color == "Azul") {
			this.exp=10;
			this.textura = new Texture(Recursos.LEDazul);
		}else if (color == "Amarillo") {
			this.exp=5;
			this.textura = new Texture(Recursos.LEDamarillo);
		}else if(color == "Rojo") {
			this.exp=3;
			this.textura = new Texture(Recursos.LEDrojo);
		}else {
			this.exp=1;
			this.textura = new Texture(Recursos.LEDverde);
		}
		
		this.spr = new Sprite(textura);
		this.x=x;
		this.y=y;
		this.setPosition(x, y);
		this.ancho=26;
		this.alto=48;
		this.mundo=mundo;
		this.spr.setBounds(x, y, 26, 48);
		this.spr.setPosition(x-13, y-24);
		this.cuerpo = this.crearBox(this.ancho, this.alto, getPosition(), mundo);

		this.cuerpo.setUserData(this);
		
	}
	
	public static LEDexp devolverObjetoBody(Body cuerpo) {
		
		
		return (LEDexp) cuerpo.getUserData();
	}
	
}
