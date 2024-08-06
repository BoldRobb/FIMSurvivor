package armas;

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
import com.badlogic.gdx.physics.box2d.CircleShape;

import Items.Cigarro;
import utiles.Recursos;
import utiles.Render;

public class Desodorante {

	public Texture textura;
	public Sprite spr;
	public float x, y, ancho, alto;
	protected Body cuerpo;
	public float damage;
	
	Vector2 position;
	public World mundo;
	
	public Desodorante(float x, float y, World mundo) {
		this.textura= new Texture(Recursos.circuloDesodorante);
		this.spr = new Sprite(textura);
		this.spr.setAlpha(0.2f);
		this.setPosition(new Vector2(x-150,y-150));
		this.ancho=300;
		this.alto=300;
		this.spr.setBounds(x, y, 300, 300);
		this.cuerpo=crearCirculo(ancho, this.getPosition(), mundo);
		this.cuerpo.setUserData(this);
		this.damage=50;
	}
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
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
	public Vector2 getCentro() {
		return new Vector2(this.getX()+this.getAncho()/2,this.getY()+this.getAlto()/2 );
	}
	public void update(float x, float y) {
		this.cuerpo.setTransform(x-32, y-64, 0);
		this.spr.setPosition(this.cuerpo.getPosition().x-this.getAncho()/2,this.cuerpo.getPosition().y-this.getAlto()/2);;
	}
	
	public static Desodorante devolverObjetoBody(Body cuerpo) {		
		return (Desodorante) cuerpo.getUserData();
	}
	
	public Body crearCirculo(float ancho2, Vector2 pos, World mundo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		
		Body player2 = mundo.createBody(bodyDef);
		
		CircleShape circleShape = new CircleShape();
        circleShape.setRadius(150);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = circleShape;
		fixturedef.isSensor=true;
		
		Fixture fixture = player2.createFixture(fixturedef);
		circleShape.dispose();
		
		return player2;
	}
}
