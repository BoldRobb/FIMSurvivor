package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import elementos.Pickable;
import personajes.Alumno;
import personajes.Profesor;
import utiles.Recursos;

public class Cafe extends Pickable {
	public Cafe(float x, float y, World mundo) {
		this.textura = new Texture(Recursos.Cafe);
		this.spr = new Sprite(textura);
		this.x=x;
		this.y=y;
		this.setPosition(x, y);
		this.ancho=32;
		this.alto=32;
		this.mundo=mundo;
		this.spr.setBounds(x, y, 32, 32);
		this.spr.setPosition(x-16, y-16);
		this.cuerpo = this.crearBox(this.ancho, this.alto, getPosition(), mundo);

		this.cuerpo.setUserData(this);
		
	}
	
	public static Cafe devolverObjetoBody(Body cuerpo) {
		
		
		return (Cafe) cuerpo.getUserData();
	}
	
	public void accion(Body body) {
		if (body.getUserData() instanceof Alumno) {
			Alumno alumno = Alumno.devolverObjetoBody(body);
			alumno.setVida(alumno.getVida()+20);
			alumno.setFactvelocidad(alumno.getFactvelocidad()+1f);
			alumno.setBoosted(true);
			alumno.setTimerBoosted(5);
		}else if (body.getUserData() instanceof Profesor) {
			Profesor alumno = Profesor.devolverObjetoBody(body);
			alumno.setVida(alumno.getVida()+20);
			alumno.setFactvelocidad(alumno.getFactvelocidad()+1f);
			alumno.setBoosted(true);
			alumno.setTimerBoosted(5);
		}
	}
}
