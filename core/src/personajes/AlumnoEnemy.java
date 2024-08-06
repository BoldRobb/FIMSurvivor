package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Recursos;

public class AlumnoEnemy extends Enemigos {

	public AlumnoEnemy(Texture textura, float x, float y, int ancho, int alto, float vida, Body body, World mundo) {
		super(textura, x, y, ancho, alto, vida, body, mundo);
		// TODO Auto-generated constructor stub
		
	}
	public AlumnoEnemy(int x, int y, World mundo) {
		this.drop2="Verde";
		this.drop1="Verde";
		this.textura = new Texture("SpritesAnimacion/Alumno64.png");
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
		this.vida = 100;
		this.vidamax=100;
		this.mundo = mundo;
		this.velocidad=1;
		this.cuerpo = this.crearBox(64, 128, getPosition(), mundo);
		this.cuerpo.setUserData(this);
	}
	
	public static AlumnoEnemy devolverObjetoBody(Body body) {
		return (AlumnoEnemy) body.getUserData();
	}
}
