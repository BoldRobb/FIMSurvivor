package elementos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Interactable {


	World mundo;
	Body cuerpo;
	float x, y;
	float ancho, alto;
	Vector2 position;
	String accion;
	
	public Interactable(float x, float y, float ancho, float alto, World mundo, String accion) {
		this.x=x;
		this.y=y;
		this.accion=accion;
		this.position=new Vector2(x,y);
		this.setAncho(ancho);
		this.setAlto(alto);
		this.cuerpo=crearBox(ancho, alto, this.position, mundo);
		this.cuerpo.setUserData(this);
	}
public static Interactable devolverObjetoBody(Body cuerpo) {
		
		
		return (Interactable) cuerpo.getUserData();
	}
	
	public Body crearBox(float ancho2, float alto2, Vector2 pos, World mundo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type=BodyType.StaticBody;
		bodyDef.position.set(pos);
		
		Body player2 = mundo.createBody(bodyDef);

		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho2/2, alto2/2);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		fixturedef.isSensor=true;
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		return player2;
	}

	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}


	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		this.setPosition(new Vector2(x, this.y));
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		this.setPosition(new Vector2(this.x, y));
	}

	public float getAncho() {
		return ancho;
	}

	public void setAncho(float ancho) {
		this.ancho = ancho;
	}

	public float getAlto() {
		return alto;
	}

	public void setAlto(float alto) {
		this.alto = alto;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		this.setX(position.x);
		this.setY(position.y);
	}
}
