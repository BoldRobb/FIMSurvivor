package personajes;

import com.badlogic.gdx.graphics.Texture;

import elementos.Mob;

public class Enemigos extends Mob {
	private int velocidad=1;
	public Enemigos(Texture textura, float x, float y, int ancho, int alto, float vida) {
		super(textura, x, y, ancho, alto, vida);
		// TODO Auto-generated constructor stub
	}
	
	public void acercar(int irx, int iry) {
		if(super.getX()<irx) {
			super.setX(super.getX()+velocidad);
		}else {
			super.setX(super.getX()-velocidad);
		}
		if(super.getY()<iry) {
			super.setY(super.getY()+velocidad);
		}else {
			super.setY(super.getY()-velocidad);
		}
	}

}
