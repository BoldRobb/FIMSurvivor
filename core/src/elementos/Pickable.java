package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import utiles.Render;

public class Pickable {

	public Texture textura;
	public Sprite spr;
	public float x, y, ancho, alto;
	protected Body cuerpo;
	Vector2 position;
	public World mundo;
	Boolean pickeado = false;

	

	public Pickable (Texture textura, float x, float y, float ancho, float alto, float vida, Body body, World mundo) {
		this.textura=textura;
		this.spr = new Sprite(textura);
		this.spr.setBounds(x, y, ancho, alto);
		this.ancho=ancho;
		this.alto=alto;
		this.x=x;
		this.y=y;
		this.position= new Vector2(x,y);
		this.cuerpo = body;
		this.mundo=mundo;
		
	}
	
	public Pickable() {
		
	}
	public void dibujar() {
		spr.draw(Render.batch);
		
	}
	public void setX(float x) {
		this.position=new Vector2(x,this.y);
		this.x=x;
		spr.setX(x);
	}
	public void setY(float y) {
		this.position=new Vector2(this.x,y);
		this.y=y;
		spr.setY(y);
	}
	public void setPosition(float x, float y) {
		this.y=y;
		this.x=x;
		this.position=new Vector2(x,y);
		spr.setPosition(x, y);
	}
	public void setPosition(Vector2 pos) {
		this.y=pos.y;
		this.x=pos.x;
		this.position=pos;
		spr.setPosition(pos.x,pos.y);
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setAncho(float ancho) {
		this.ancho=ancho;
		spr.setSize(ancho, this.alto);
	}
	public void setAlto(float alto) {
		this.alto=alto;
		spr.setSize(this.ancho, alto);
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getAncho() {
		return ancho;
	}
	public float getAlto() {
		return alto;
	}
	public void desaparecer() {
		this.mundo.destroyBody(cuerpo);
		this.cuerpo=null;
	}
	public Vector2 getCentro() {
		return new Vector2(this.getX()+this.getAncho()/2,this.getY()+this.getAlto()/2 );
	}
	public void update() {
		this.spr.setPosition(this.cuerpo.getPosition().x-this.getAncho()/2,this.cuerpo.getPosition().y-this.getAlto()/2);;
	}
	public Boolean getPickeado() {
		return pickeado;
	}

	public void setPickeado(Boolean pickeado) {
		this.pickeado = pickeado;
	}
	public void accion() {
		
	}
	public Body crearBox(float ancho2, float alto2, Vector2 pos, World mundo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
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
}
