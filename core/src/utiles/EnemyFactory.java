package utiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import personajes.Enemigos;

public class EnemyFactory {
	
	private SpriteBatch b;
	private List<Enemigos> currentEnemies;
	public EnemyFactory() {
		b = Render.batch;
		currentEnemies = new ArrayList<Enemigos>();
	}
	
	
	public void SpawnWave(int cant, int currentX, int currentY) {
		 double anguloIncremento = 2 * Math.PI / cant;
		for (int i=0;i<cant; i++) {
			 double angulo = i * anguloIncremento;
	            double x = currentX + 900 * Math.cos(angulo);
	            double y = currentY + 600 * Math.sin(angulo);
			currentEnemies.add(new Enemigos (new Texture(Recursos.SpriteMaestro), (int)x, (int)y, 100, 150, 100f));
			
		}
	}
	
	public void dibujarEnemigos() {
		if(!currentEnemies.isEmpty()) {
			currentEnemies.forEach((enemy)-> enemy.dibujar());
			
		}
		
	}
	public void acercarEnemigos(int x,int y ) {
		if(!currentEnemies.isEmpty()) {
			currentEnemies.forEach((enemy)-> enemy.acercar(x,y));
			
		}
	}
}
