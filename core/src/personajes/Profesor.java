package personajes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import Items.Cigarro;

public class Profesor extends Playable {

	public Profesor(Texture textura, int x, int y, int ancho, int alto, float vida, Body cuerpo, World mundo) {
		super(textura, x, y, ancho, alto, vida, cuerpo, mundo);
		// TODO Auto-generated constructor stub
		super.vidamax=130;
		super.factvelocidad=1;
	}
public Profesor( int x, int y, int ancho, int alto, float vida, Body cuerpo, World mundo) {
		
		super.vidamax=130;
		this.vida=150;
		super.factvelocidad=1;
		// TODO Auto-generated constructor stub
		this.textura = new Texture("SpritesAnimacion/Figueroa64.png");
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
		this.mundo = mundo;

	}
	public static Profesor devolverObjetoBody(Body cuerpo) {
		
		
		return (Profesor) cuerpo.getUserData();
	}
}
