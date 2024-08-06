package pantallas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import elementos.Imagen;
import elementos.Texto;
import io.TecladoMouse;
import utiles.Config;
import utiles.Recursos;
import utiles.Render;

public class PantallaTuto implements Screen {
	Imagen fondo;
	OrthographicCamera camera;
	Texto volver;
	Texto puntuacion;
	Texto highscore;
	float score;
	SpriteBatch b;

    List<String> lines = new ArrayList<>();
    Imagen WASD;
    int money;
    Texto dinero;
    int randomNumber;
    int currentRandom=0;
    Random random = new Random();
    TecladoMouse entrada = new TecladoMouse();
    boolean scoreBien, dineroBien;
    boolean opcMouse=false;
    float delay;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		money=(int) (score/15*1.2);
		WASD = new Imagen("WASD.png", 135, 200);
		Gdx.input.setInputProcessor(entrada);
		
		dinero = new Texto(Recursos.FuenteMenu, 24, Color.WHITE, true);
		dinero.setTexto(""+money);
		WASD.setPosition(Config.ancho/2-200, Config.alto/2+100);
		dinero.setPosition(WASD.getX()+WASD.getAncho()+10, WASD.getY()+WASD.getAlto()/2+dinero.getAlto()/2);
		fondo= new Imagen("Fondos/CastilloTuto.png", Config.ancho, Config.alto);
		fondo.setPosition(0, 0);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.ancho,Config.alto);
		volver = new Texto(Recursos.FuenteMenu, 48, Color.GOLD, false);
		puntuacion = new Texto(Recursos.FuenteMenu, 48, Color.WHITE, false);
		highscore = new Texto(Recursos.FuenteMenu, 48, Color.GOLD, false);
		volver.setTexto("VOLVER");
		volver.setPosition(fondo.getEsqArribaDer().x-volver.getAncho()-20, fondo.getEsqArribaDer().y-15);
		puntuacion.setTexto("Puntuacion: "+score);
		puntuacion.setPosition(Config.ancho/2-puntuacion.getAncho()/2, Config.alto/2+230);
		highscore.setTexto("¡NEW HIGHSCORE!");
		highscore.setPosition(Config.ancho/2-highscore.getAncho()/2, Config.alto/2-150);
		b = Render.batch;
		dinero.setTexto("0");
		 // Leer el archivo y guardar las líneas en una lista

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		delay+=delta;
		camera.position.set(Config.ancho/2,Config.alto/2,0);
		camera.update();
		Render.LimpiarPantalla(1, 1, 1);
		// TODO Auto-generated method stub
		b.setProjectionMatrix(camera.combined);
		b.begin();

		
		
		fondo.dibujar();
		
		volver.dibujar();
		//puntuacion.dibujar();
		
		//WASD.dibujar();
		//dinero.dibujar();

		opcEnter();
		b.end();
	}
	public void opcEnter() {
		if (entrada.getMouseX()>volver.getEsqAbajoIzq().x && entrada.getMouseX()<volver.getEsqArribaDer().x) {
			if(entrada.getMouseY()>volver.getEsqAbajoIzq().y && entrada.getMouseY()<volver.getEsqArribaDer().y) {
				opcMouse=true;
			}
		}
		if (entrada.isEnter() || (entrada.isMouse1() && opcMouse)) {
				Render.app.setScreen(new PantallaSelec());
			
		}
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

}
