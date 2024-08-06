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

public class pantallaPuntuacion implements Screen {

	Imagen fondo;
	OrthographicCamera camera;
	Texto volver;
	Texto puntuacion;
	Texto highscore;
	float score;
	SpriteBatch b;String filePath = "config.cfg"; // Cambia esto por la ruta de tu archivo
    List<String> lines = new ArrayList<>();
    Imagen monedas;
    int money;
    Texto dinero;
    int randomNumber;
    int currentRandom=0;
    Random random = new Random();
    TecladoMouse entrada = new TecladoMouse();
    boolean scoreBien, dineroBien;
    boolean opcMouse=false;
    float delay;
	public pantallaPuntuacion(float score) {
		this.score=score;
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		money=(int) (score/15*1.2);
		monedas = new Imagen(Recursos.monedas, 50, 50);
		Gdx.input.setInputProcessor(entrada);
		
		dinero = new Texto(Recursos.FuenteMenu, 24, Color.WHITE, true);
		dinero.setTexto(""+money);
		monedas.setPosition(Config.ancho/2-dinero.getAncho()-monedas.getAncho()/2, Config.alto/2-monedas.getAlto());
		dinero.setPosition(monedas.getX()+monedas.getAncho()+10, monedas.getY()+monedas.getAlto()/2+dinero.getAlto()/2);
		fondo= new Imagen("Fondos/CastilloCuadro.png", Config.ancho, Config.alto);
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
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            lines.add(line);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    checkLogros();
	    try (FileWriter fw = new FileWriter(filePath)) {
            for (String line : lines) {
                fw.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
	}

	@Override
	public void render(float delta) {
		delay+=delta;
		camera.position.set(Config.ancho/2,Config.alto/2,0);
		camera.update();
		Render.LimpiarPantalla(1, 1, 1);
		// TODO Auto-generated method stub
		b.setProjectionMatrix(camera.combined);
		b.begin();
		if(!scoreBien) {
			randomScore();
		}
		if(scoreBien) {
			 randomDinero();
		}
		
		
		fondo.dibujar();
		
		volver.dibujar();
		puntuacion.dibujar();
		
		monedas.dibujar();
		dinero.dibujar();
		if(dineroBien) {
			if(Config.highscore==(int)score) {
				highscore.dibujar();
				if(delay>=delta*10) {
					delay=0;
					rainbowTexto();
				}
				
			}
			
		}
		opcEnter();
		b.end();
	}
	public void randomScore() {
		
		if(currentRandom<score) {
			currentRandom+=random.nextInt(100);
			puntuacion.setTexto("Puntuacion: "+currentRandom);
		}else {
			puntuacion.setTexto("Puntuacion: "+(int)score);
			scoreBien=true;
			currentRandom=0;
		}
		
	}
public void randomDinero() {
		
		if(currentRandom<money) {
			currentRandom+=random.nextInt(50);
			dinero.setTexto(""+currentRandom);
		}else {
			dinero.setTexto(""+money);
			dineroBien=true;
		}
		
	}
public void rainbowTexto() {
	float red = random.nextFloat();   // Genera un valor aleatorio entre 0.0 y 1.0
    float green = random.nextFloat(); // Genera un valor aleatorio entre 0.0 y 1.0
    float blue = random.nextFloat();  // Genera un valor aleatorio entre 0.0 y 1.0

    Color randomColor = new Color(red, green, blue, 1);
     highscore.setColor(randomColor);
}


public void opcEnter() {
	if (entrada.getMouseX()>volver.getEsqAbajoIzq().x && entrada.getMouseX()<volver.getEsqArribaDer().x) {
		if(entrada.getMouseY()>volver.getEsqAbajoIzq().y && entrada.getMouseY()<volver.getEsqArribaDer().y) {
			opcMouse=true;
		}
	}
	if (entrada.isEnter() || (entrada.isMouse1() && opcMouse)) {
			Render.app.setScreen(new MenuPrincipal());
		
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
	public void checkLogros() {
	    
	    if(Config.logro1) {
	            lines.set(2, "Logro1:1"); // Modifica la primera línea
	    }else {
	    	lines.set(2, "Logro1:0");
	    }
	    if(Config.logro2) {
            lines.set(3, "Logro2:1"); // Modifica la primera línea
    }else {
    	lines.set(3, "Logro2:0");
    }
	    if(Config.logro3) {
            lines.set(4, "Logro3:1"); // Modifica la primera línea
    }else {
    	lines.set(4, "Logro3:0");
    }
	    if(Config.logro4) {
            lines.set(5, "Logro4:1"); // Modifica la primera línea
    }else {
    	lines.set(5, "Logro4:0");
    }
	    if(Config.logro5) {
            lines.set(6, "Logro5:1"); // Modifica la primera línea
    }else {
    	lines.set(6, "Logro5:0");
    }
	    if(Config.logro6) {
            lines.set(7, "Logro6:1"); // Modifica la primera línea
    }else {
    	lines.set(7, "Logro6:0");
    }
	    if(Config.logro7) {
            lines.set(8, "Logro7:1"); // Modifica la primera línea
    }else {
    	lines.set(8, "Logro7:0");
    }
	    if(Config.logro8) {
            lines.set(9, "Logro8:1"); // Modifica la primera línea
    }else {
    	lines.set(9, "Logro8:0");
    }
	   
	    if(Config.highscore<score) {
	    	
	    	Config.highscore=(int) score;
	    	
	    	lines.set(0, "Score:"+(int)score);
	    }
	    
	    Config.money+=money;
	    lines.set(1, "Dinero:"+Config.money);
	}

}
