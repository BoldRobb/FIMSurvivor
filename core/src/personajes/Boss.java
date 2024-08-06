package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Recursos;

public class Boss extends Enemigos {

	public float armor;
	public Boss(Texture textura, int x, int y, int ancho, int alto, float vida, Body body,float armor, World mundo) {
		super(textura, x, y, ancho, alto, vida, body, mundo);
		this.armor = armor;
		// TODO Auto-generated constructor stub
	}
	public Boss() {
		
	}
	public boolean update(float delta, boolean paused) {
		stateTime += delta;      
        // Obtener el frame actual de la animación
    TextureRegion currentFrame =(TextureRegion) this.animacion.getKeyFrame(stateTime, true);
    this.spr.setRegion(currentFrame);
		this.spr.setPosition(this.cuerpo.getPosition().x-this.getAncho()/2,this.cuerpo.getPosition().y-this.getAlto()/2);
		if(!paused) {
			if (isPoisoned()) {
				this.cooldownPoison+=delta;
				if(cooldownPoison>2) {
					cooldownPoison=0;
					super.setVida(super.getVida()-30*this.armor);
					return true;
				}
			}
		}
		
		return false;
	}
}
