package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import elementos.Imagen;
import elementos.Texto;
import io.TecladoMouse;
import personajes.Alumno;
import personajes.Enemigos;
import utiles.Config;
import utiles.EnemyFactory;
import utiles.Recursos;
import utiles.Render;

public class PantallaPartida implements Screen {
	Imagen fondo;

	SpriteBatch b;
	float tiempoPartida, tiempoPasado, tresSec, posX, posY, angle= 0;
	int randomEsquina, puntuacion, randomX, randomY;
	int velocidad=3;
	OrthographicCamera camera;
	Alumno AlumnoPrueba1;
	Enemigos enemy;
	ShapeRenderer sr;
	Texto coordenadas;
	TecladoMouse entrada = new TecladoMouse();
	EnemyFactory factory = new EnemyFactory();
	@Override
	public void show() {
		sr= new ShapeRenderer();
		// TODO Auto-generated method stub
		fondo = new Imagen(Recursos.FondoPasto, 5000,5000);
		b = Render.batch;
		Gdx.input.setInputProcessor(entrada);
		AlumnoPrueba1 = new Alumno((int)fondo.getAncho()/2,(int)fondo.getAlto()/2, 100, 150, 500);
		coordenadas = new Texto(Recursos.FuenteMenu, 50, Color.WHITE, false);
		coordenadas.setPosition((int)fondo.getAncho()/2 - 500,(int)fondo.getAlto()/2 + 100);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.ancho,Config.alto);
		
	}

	@Override
	public void render(float delta) {
		tresSec+=delta;
		posX=AlumnoPrueba1.getX();
		posY=AlumnoPrueba1.getY();
		coordenadas.setTexto(posX+","+posY);
		Render.LimpiarPantalla(0,0,0);
		camera.position.set(AlumnoPrueba1.getX()+AlumnoPrueba1.getAncho()/2, AlumnoPrueba1.getY()+AlumnoPrueba1.getAlto()/2, 0);
        camera.update();
        
        b.setProjectionMatrix(camera.combined); 
		b.begin();
		fondo.dibujar();
		coordenadas.dibujar();
		factory.dibujarEnemigos();
		factory.acercarEnemigos((int)AlumnoPrueba1.getX(), (int)AlumnoPrueba1.getY());
		AlumnoPrueba1.dibujar();
		
		if(tresSec>3) {
			tresSec=0;
			int cantEnemigos= (int)(Math.random() *12 +3);
			factory.SpawnWave(cantEnemigos, (int)AlumnoPrueba1.getX(), (int)AlumnoPrueba1.getY());
			
		}
		b.end();
		
		checkEntrada();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	public void checkEntrada() {
		if(entrada.isAbajo()) {
			AlumnoPrueba1.setY(AlumnoPrueba1.getY()-velocidad);
			coordenadas.setY(coordenadas.getY()-velocidad);
			/*if(AlumnoPrueba1.getY()<=0) {
				AlumnoPrueba1.setY(0);
			}*/
		}
		if(entrada.isArriba()) {
			AlumnoPrueba1.setY(AlumnoPrueba1.getY()+velocidad);
			coordenadas.setY(coordenadas.getY()+velocidad);
			/*if(AlumnoPrueba1.getY()>=Config.alto-AlumnoPrueba1.getAlto()) {
				AlumnoPrueba1.setY(Config.alto-AlumnoPrueba1.getAlto());
			}*/
		}
		if(entrada.isDer()) {
			AlumnoPrueba1.setX(AlumnoPrueba1.getX()+velocidad);
			coordenadas.setX(coordenadas.getX()+velocidad);
			/*if(AlumnoPrueba1.getX()+AlumnoPrueba1.getAncho()>=Config.ancho) {
				AlumnoPrueba1.setX(Config.ancho-AlumnoPrueba1.getAncho());
			}*/
		}
		if(entrada.isIzq()) {
			AlumnoPrueba1.setX(AlumnoPrueba1.getX()-velocidad);
			coordenadas.setX(coordenadas.getX()-velocidad);
			/*if(AlumnoPrueba1.getX()<=0) {
				AlumnoPrueba1.setX(0);
			}*/
		}

	}

	public void spawnEnemigos(float delta) {
		
	}
}
