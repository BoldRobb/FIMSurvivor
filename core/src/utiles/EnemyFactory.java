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

import personajes.AguilaEnemy;
import personajes.AlumnoEnemy;
import personajes.AlumnoEnemy2;
import personajes.Enemigos;
import personajes.PerroEnemy;
import personajes.ProfesorEnemy;
import personajes.ProfesorEnemy2;
import personajes.Recursador;

public class EnemyFactory {
	int ppm=Render.PPM;
	private SpriteBatch b;
	public List<Enemigos> currentEnemies;
	public List<PerroEnemy> currentPerros;
	public List<AguilaEnemy> currentAguilas;
	public List<ProfesorEnemy> currentProfesores;
	public List<AlumnoEnemy> currentAlumnos;
	public List<AlumnoEnemy2> currentAlumnos2;
	public List<Recursador> currentRecursadores;
	
	
	public EnemyFactory() {
		b = Render.batch;
		currentEnemies = new ArrayList<Enemigos>();
		currentPerros = new ArrayList<PerroEnemy>();
		currentAguilas = new ArrayList<AguilaEnemy>();
		currentProfesores= new ArrayList<ProfesorEnemy>();
		currentAlumnos = new ArrayList<AlumnoEnemy>();
		currentAlumnos2= new ArrayList<AlumnoEnemy2>();
		currentRecursadores = new ArrayList<Recursador>();

	}
	
	
	public void SpawnWave(float currentX, float currentY, World mundo, float tiempo) {
		Double random1=Math.random();
		int cant;
		if (tiempo>0 && tiempo<150) {
			
			cant = (int)(random1*3+1);
		}else if(tiempo>150 && tiempo<300){
			cant = (int)(random1*3+3);
		}else if(tiempo>300 && tiempo<450){
			cant = (int)(random1*4+5);
		}else if(tiempo>450 && tiempo<600){
			cant = (int)(random1*5+6);
		}else if(tiempo>600 && tiempo<750){
			cant = (int)(random1*5+3);
		}else if(tiempo>750 && tiempo<900){
			cant = (int)(random1*5+5);
		}else if(tiempo>900 && tiempo<1050){
			cant = (int)(random1*8+5);
		}else if(tiempo>1050 && tiempo<1200){
			cant = (int)(random1*10+5);
		}else if(tiempo>1200 && tiempo<1350){
			cant = (int)(random1*12+6);
		}else if(tiempo>1350 && tiempo<1500){
			cant = (int)(random1*14+6);
		}else {
			random1=Math.random();
			cant = (int)(random1*4+1);
		}
		 
			 double anguloIncremento = 2 * Math.PI / cant;
		for (int i=0;i<cant; i++) {
			double random = Math.random();
			 double angulo = i * anguloIncremento;
	            double x = currentX + 900 * Math.cos(angulo);
	            double y = currentY + 600 * Math.sin(angulo);
			if(tiempo > 0 && tiempo < 300) {
				if(random<0.45) {
		            AlumnoEnemy enemigo = new AlumnoEnemy((int)x,(int) y, mundo);
				
				}else if (random>0.45 && random<0.75) {
					ProfesorEnemy enemigo = new ProfesorEnemy((int)x,(int) y, mundo);
				
				}else if (random>0.75 && random<0.95) {
					Recursador enemigo = new Recursador((int)x,(int) y, mundo);
				}else if (random>0.95 && random<0.99) {
					AguilaEnemy enemigo = new AguilaEnemy((int)x,(int) y, mundo);
				}else {
					PerroEnemy enemigo = new PerroEnemy((int)x,(int) y, mundo);
				}
			}else if(tiempo > 300 && tiempo < 600) {
				if(random<0.10) {
		            AlumnoEnemy enemigo = new AlumnoEnemy((int)x,(int) y, mundo);
				
				}else if (random>0.10 && random<0.45) {
					ProfesorEnemy enemigo = new ProfesorEnemy((int)x,(int) y, mundo);
				
				}else if (random>0.45 && random<0.75) {
					Recursador enemigo = new Recursador((int)x,(int) y, mundo);
				}else if (random>0.75 && random<0.79) {
					AguilaEnemy enemigo = new AguilaEnemy((int)x,(int) y, mundo);
				}else if(random>0.79 && random<0.80){
					PerroEnemy enemigo = new PerroEnemy((int)x,(int) y, mundo);
				}else {
					AlumnoEnemy2 enemigo = new AlumnoEnemy2((int)x,(int)y, mundo);
				}
			}else if(tiempo > 600 && tiempo < 900) {
				if(random<0.40) {
		            AlumnoEnemy2 enemigo = new AlumnoEnemy2((int)x,(int) y, mundo);
				
				}else if (random>0.40 && random<0.80) {
					ProfesorEnemy2 enemigo = new ProfesorEnemy2((int)x,(int) y, mundo);
				
				}else if (random>0.80 && random<0.95) {
					Recursador enemigo = new Recursador((int)x,(int) y, mundo);
				}else if (random>0.95 && random<0.99) {
					AguilaEnemy enemigo = new AguilaEnemy((int)x,(int) y, mundo);
				}else {
					PerroEnemy enemigo = new PerroEnemy((int)x,(int) y, mundo);
				}
			}else if(tiempo > 900 && tiempo < 1200) {
				if(random<0.40) {
		            AlumnoEnemy2 enemigo = new AlumnoEnemy2((int)x,(int) y, mundo);
				
				}else if (random>0.40 && random<0.80) {
					ProfesorEnemy2 enemigo = new ProfesorEnemy2((int)x,(int) y, mundo);
				
				}else if (random>0.80 && random<0.95) {
					Recursador enemigo = new Recursador((int)x,(int) y, mundo);
				}else if (random>0.95 && random<0.99) {
					AguilaEnemy enemigo = new AguilaEnemy((int)x,(int) y, mundo);
				}else {
					PerroEnemy enemigo = new PerroEnemy((int)x,(int) y, mundo);
				}
			}
			else {
				
			}
			
	            
			
		}
		//return currentEnemies;
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
	public void dibujarEnemigos() {
		if(!currentEnemies.isEmpty()) {
			currentEnemies.forEach((enemy)-> {
				if(enemy.isVivo()) {
					enemy.dibujar();
				}
			});
			
		}
		
	}
	public void acercarEnemigos(float x,float y ) {
		if(!currentEnemies.isEmpty()) {
			
			currentEnemies.forEach((enemy)-> {
				if(enemy.isVivo()) {
					enemy.acercar(x,y);
				}
			});
			
		}
	}
}
