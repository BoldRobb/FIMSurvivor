package armas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import elementos.Bullets;

public class Lapiz extends Bullets {

	public Lapiz(float x, float y, float amplif, World mundo, Vector2 target, float angulo) {
		this.spr = new Sprite(new Texture("Proyectiles/Lapiz.png"));
		this.x=x;
		this.y=y;
		spr.setPosition(x, y);
		this.ancho=24;
		this.alto=12;
		spr.setSize(this.ancho,this.alto);
		this.damage=10*amplif;
		this.body = crearBox(24, 12, new Vector2(x+12,y+6), mundo, angulo);
		this.body.setBullet(true);
		this.target=target;
		this.position= new Vector2(x,y);
		this.angulo = angulo;
		this.mundo=mundo;
		
		Vector2 direction = new Vector2(target).sub(body.getPosition()).nor();
        body.applyLinearImpulse(direction.scl(120), direction.scl(120), true);
        this.body.setUserData(this);
	}

	public static Lapiz devolverLapizPorBody(Body body) {
        return (Lapiz) body.getUserData();
    }
	
	
}
