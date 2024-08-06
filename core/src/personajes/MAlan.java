package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Recursos;

public class MAlan extends Boss {
	
	public MAlan(Texture textura, int x, int y, int ancho, int alto, float vida, Body cuerpo, float armor, World mundo) {
		super(textura, x, y, ancho, alto, vida, cuerpo, armor, mundo);
		// TODO Auto-generated constructor stub
	}
	public MAlan(int x, int y, World mundo) {
		this.drop2="Azul";
		this.drop1="Azul";
		this.textura = new Texture("SpritesAnimacion/Alan64.png");
		TextureRegion [][] tmp = TextureRegion.split(textura, textura.getWidth()/8, textura.getHeight());
		this.regionMovimiento = new TextureRegion[8];
		for(int i=0; i<8;i++) {
			regionMovimiento[i]=tmp[0][i];
		}
		this.animacion= new Animation(0.4f,regionMovimiento);
		this.spr = new Sprite(regionMovimiento[0]);
		this.x = x;
		this.y = y;
		this.ancho= 64;
		this.alto = 128;
		this.spr.setBounds(x, y, ancho, alto);
		this.setPosition(new Vector2(x,y));
		this.vida = 600;
		this.vidamax=600;
		this.armor=.5f;
		this.mundo = mundo;
		this.velocidad=2;
		this.cuerpo = this.crearBox(83, 166, getPosition(), mundo);
		this.cuerpo.setUserData(this);
	}
	
	public static MAlan devolverObjetoBody(Body body) {
		return (MAlan) body.getUserData();
	}
	
	
	public boolean update(float delta) {
		stateTime += delta;      
    // Obtener el frame actual de la animación
TextureRegion currentFrame =(TextureRegion) this.animacion.getKeyFrame(stateTime, true);
this.spr.setRegion(currentFrame);
		this.spr.setPosition(this.cuerpo.getPosition().x-this.getAncho()/2,this.cuerpo.getPosition().y-this.getAlto()/2);
		
		if (isPoisoned()) {
			this.cooldownPoison+=delta;
			if(cooldownPoison>2) {
				cooldownPoison=0;
				super.setVida(super.getVida()-30*this.armor);
				return true;
			}
		}
		return false;
	}
}
