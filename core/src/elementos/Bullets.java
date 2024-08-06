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

public class Bullets {

	public Sprite spr;
	public float damage, x, y, ancho, alto;
	public Vector2 target;
	public Vector2 position;
	public float angulo;
	public World mundo;
	public boolean vivo=true;
	
	public Body body;
	public Bullets(Texture textura, float x, float y, float ancho, float alto, float damage,Body body) {
		this.spr = new Sprite(textura);
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		spr.setSize(ancho, alto);
		spr.setPosition(x, y);
		this.body = body;
	}
	public Bullets() {
		
	};
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.position=new Vector2(x,this.y);
		spr.setX(x);
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.position=new Vector2(this.x,y);
		spr.setY(y);
		this.y = y;
	}
	public float getAncho() {
		
		return ancho;
	}
	public void setAncho(float ancho) {
		spr.setSize(ancho, this.alto);
		this.ancho = ancho;
	}
	public float getAlto() {
		return alto;
	}
	public void setAlto(float alto) {
		spr.setSize(this.ancho, alto);
		this.alto = alto;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		spr.setPosition(position.x,position.y);
		this.position = position;
	}
	public void acercarAlEnemigo() {
		
	}
	public Body crearBox(int ancho, int alto, Vector2 pos, World mundo, float angulo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type=BodyType.DynamicBody;
		bodyDef.bullet = true;
		bodyDef.position.set(pos);
		bodyDef.angle = angulo;
		
		Body player2 = mundo.createBody(bodyDef);

		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho/2, alto/2);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		fixturedef.isSensor=true;
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		return player2;
	}
	public void dibujar() {
		spr.draw(Render.batch);
	}
	public void desaparecer() {
		this.mundo.destroyBody(this.body);
		this.body=null;
		
	}
	public boolean isVivo() {
		return vivo;
	}
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	public void update() {
		this.spr.setPosition(this.body.getPosition().x-this.getAncho()/2,this.body.getPosition().y-this.getAlto()/2);;
	}
	public void rotarSprite(float angulo) {
		this.spr.rotate(angulo);
	}
}
