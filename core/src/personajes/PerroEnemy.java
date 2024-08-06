package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Recursos;

public class PerroEnemy extends Enemigos {

	public PerroEnemy(Texture textura, float x, float y, int ancho, int alto, float vida, Body body, World mundo) {
		super(textura, x, y, ancho, alto, vida, body, mundo);
		// TODO Auto-generated constructor stub
		this.drop2="Rojo";
		this.drop1="Rojo";
	}
	public PerroEnemy(int x, int y, World mundo) {
		this.textura = new Texture("SpritesAnimacion/PerroCaminar48.png");
		TextureRegion [][] tmp = TextureRegion.split(textura, textura.getWidth()/6, textura.getHeight());
		this.regionMovimiento = new TextureRegion[6];
		for(int i=0; i<6;i++) {
			regionMovimiento[i]=tmp[0][i];
		}
		this.animacion= new Animation(0.4f,regionMovimiento);
		this.spr = new Sprite(regionMovimiento[0]);
		this.x = x;
		this.y = y;
		this.ancho= 100;
		this.alto = 64;
		this.spr.setBounds(x, y, ancho, alto);
		this.setPosition(new Vector2(x,y));
		this.vida = 200;
		this.vidamax=200;
		this.mundo = mundo;
		this.velocidad=1.5f;
		this.cuerpo = this.crearBox(100, 64, getPosition(), mundo);
		this.cuerpo.setUserData(this);
	}
	
	public static PerroEnemy devolverObjetoBody(Body body) {
		return (PerroEnemy) body.getUserData();
	}
}
