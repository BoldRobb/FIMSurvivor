package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import elementos.Pickable;
import personajes.Alumno;
import personajes.Profesor;
import utiles.Recursos;

public class Cigarro extends Pickable {

	
	public Cigarro(float x, float y, World mundo) {
		this.textura = new Texture(Recursos.Cigarro);
		this.spr = new Sprite(textura);
		this.x=x;
		this.y=y;
		this.setPosition(x, y);
		this.ancho=32;
		this.alto=22;
		this.mundo=mundo;
		this.spr.setBounds(x, y, 32, 22);
		this.spr.setPosition(x-16, y-11);
		this.cuerpo = this.crearBox(this.ancho, this.alto, getPosition(), mundo);

		this.cuerpo.setUserData(this);
		
	}
	
	public static Cigarro devolverObjetoBody(Body cuerpo) {
		
		
		return (Cigarro) cuerpo.getUserData();
	}
	
	public void accion(Body body) {
		if (body.getUserData() instanceof Alumno) {
			Alumno alumno = Alumno.devolverObjetoBody(body);
			alumno.setVida(alumno.getVida()+20);
		}else if (body.getUserData() instanceof Profesor) {
			Profesor alumno = Profesor.devolverObjetoBody(body);
			alumno.setVida(alumno.getVida()+20);
		}
	}
}
