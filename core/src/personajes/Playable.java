package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import elementos.Mob;

public class Playable extends Mob {

	float factvelocidad;
	boolean boosted = false;
	float timerBoosted;
	
	
	public Playable(Texture textura, int x, int y, int ancho, int alto, float vida, Body cuerpo, World mundo) {
		super(textura, x, y, ancho, alto, vida, cuerpo, mundo);
		// TODO Auto-generated constructor stub
	}
	public Playable() {
		
	}
	public float getFactvelocidad() {
		return factvelocidad;
	}
	public void setFactvelocidad(float factvelocidad) {
		this.factvelocidad = factvelocidad;
	}
	public boolean isBoosted() {
		return boosted;
	}
	public void setBoosted(boolean boosted) {
		this.boosted = boosted;
	}
	public boolean update(float delta) {
		stateTime += delta;      
        // Obtener el frame actual de la animación
    TextureRegion currentFrame =(TextureRegion) this.animacion.getKeyFrame(stateTime, true);
    this.spr.setRegion(currentFrame);

		if (isBoosted()) {
			timerBoosted-=delta;
		}
		return false;
	}
	public float getTimerBoosted() {
		return timerBoosted;
	}
	public void setTimerBoosted(float timerBoosted) {
		this.timerBoosted = timerBoosted;
	}
	
	
}
