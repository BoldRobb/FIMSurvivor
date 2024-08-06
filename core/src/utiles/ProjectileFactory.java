package utiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import armas.Lapiz;

public class ProjectileFactory {

	int ppm=Render.PPM;
	private SpriteBatch b;
	public List<Lapiz> ListaLapices;
	public ProjectileFactory() {
		b = Render.batch;
		ListaLapices = new ArrayList<Lapiz>();
	}
	
	public void spawnLapiz(float iniX, float iniY, float finX, float finY, World mundo, float ampl) {
		float difX=iniX-finX, difY=iniY-finY;
		if(difX>0  && difY>0) {
			//Lapiz lapicito =new Lapiz(iniX+24, iniY,ampl, mundo, finX, finY);
			//lapicito.spr.rotate(0);
		}else if (difX<0 && difY<0 ) {
			//Lapiz lapicito =new Lapiz(iniX-24, iniY,ampl, mundo, finX, finY);
			//lapicito.spr.rotate(180);
		}
		
		
	}
	public void acercarLapices() {
		if (!ListaLapices.isEmpty()) {
			ListaLapices.forEach((Lapiz)->{
				
			});
		}
	}
	public void dibujarLapices() {
		if(!ListaLapices.isEmpty()) {
			ListaLapices.forEach((lapiz)-> lapiz.dibujar());
			
		}
	}
	
	public Body crearBox(int ancho, int alto, Vector2 pos, World mundo) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		
		Body player2 = mundo.createBody(bodyDef);

		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho/2, alto/2);
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		return player2;
	}
}
