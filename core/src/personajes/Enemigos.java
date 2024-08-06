package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Items.LEDexp;
import elementos.Mob;
import utiles.Render;

public class Enemigos extends Mob {
	int ppm=Render.PPM;
	public float velocidad;
	boolean poisoned= false;
	float cooldownPoison=0;
	public String drop1, drop2;
	
	public float getCooldownPoison() {
		return cooldownPoison;
	}
	public Enemigos(Texture textura, float x, float y, int ancho, int alto, float vida, Body body, World mundo) {
		super(textura, x, y, ancho, alto, vida, body, mundo);
		// TODO Auto-generated constructor stub
	}
	public Enemigos() {
		
	}
	
	public void setVida(float vida) {
		this.vida=vida;
		if(this.vida<=0) {
			setVivo(false);
			
		} else if (this.vida>this.vidamax) {
			this.vida=this.vidamax;
		}
	}
	public void acercar(float irx, float iry) {
		int velx=0;
		int vely=0;
		
		if(super.cuerpo.getPosition().x<irx) {
			velx+=1;
		}else {
			velx-=1;
		}
		if(super.cuerpo.getPosition().y<iry) {
			vely+=1;
		}else {
			vely-=1;
		}
		super.cuerpo.setLinearVelocity(velx*ppm*1, vely*1*ppm);
		this.setPosition(super.cuerpo.getPosition().x-ppm/2,super.cuerpo.getPosition().y-ppm);
	}
	public static Enemigos devolverEnemigoPorBody(Body body) {
        return (Enemigos) body.getUserData();
    }
	public Body crearBox(int ancho, int alto, Vector2 pos, World mundo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		
		Body player2 = mundo.createBody(bodyDef);
		
		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho/2, alto/2);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		fixturedef.density=0;
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		
		return player2;
	}
	public boolean isPoisoned() {
		return poisoned;
	}
	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
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
					super.setVida(super.getVida()-30);
					return true;
				}
			}
		}
		
		return false;
	}
}
