package personajes;

import com.badlogic.gdx.graphics.Texture;

public class Boss extends Enemigos {

	public float armor;
	public Boss(Texture textura, int x, int y, int ancho, int alto, float vida, float armor) {
		super(textura, x, y, ancho, alto, vida);
		this.armor = armor;
		// TODO Auto-generated constructor stub
	}

}
