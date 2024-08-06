package elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import utiles.Render;

public class Mob {

	public Texture textura;
	public Sprite spr;
	public float x, y, ancho, alto;
	public Animation animacion;
	protected Body cuerpo;
	public float vida;
	Vector2 position;
	public World mundo;
	public boolean vivo = true;
	protected float vidamax;
	public TextureRegion [] regionMovimiento;
	public TextureRegion regionActual;
	public float stateTime = 0f;
	
	public float getVidamax() {
		return vidamax;
	}
	public void setVidamax(float vidamax) {
		this.vidamax = vidamax;
	}
	public Mob (Texture textura, float x, float y, float ancho, float alto, float vida, Body body, World mundo) {
		this.textura=textura;
		this.vida=vida;
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
	public Mob() {
		
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
	public void setVida(float vida) {
		this.vida=vida;
		if(this.vida<=0) {
			setVivo(false);
		} else if (this.vida>this.vidamax) {
			this.vida=this.vidamax;
		}
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
	public float getVida() {
		return vida;
	}
	public Vector2 getEsqAbajoIzq() {
		return new Vector2(this.x,this.y);
	}
	public Vector2 getEsqArribaDer() {
		return new Vector2(x+getAncho(),y+getAlto());
	}
	public void desaparecer() {
		this.mundo.destroyBody(cuerpo);
		this.cuerpo=null;
	}
	public boolean isVivo() {
		return vivo;
	}
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	public Vector2 getCentro() {
		return new Vector2(this.getX()+this.getAncho()/2,this.getY()+this.getAlto()/2 );
	}
	public boolean update(float delta) {
		 stateTime += delta;      
	        // Obtener el frame actual de la animación
	    TextureRegion currentFrame =(TextureRegion) this.animacion.getKeyFrame(stateTime, true);
	    this.spr.setRegion(currentFrame);
		this.spr.setPosition(this.cuerpo.getPosition().x-this.getAncho()/2,this.cuerpo.getPosition().y-this.getAlto()/2);
		return false;
	}
}
