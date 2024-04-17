package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import elementos.Imagen;
import elementos.Texto;
import io.TecladoMouse;
import utiles.Config;
import utiles.Recursos;
import utiles.Render;

public class MenuPrincipal implements Screen{

	Imagen fondo;
	Imagen marcos;
	SpriteBatch b;
	float alpha = 0, contTiempo=0;
	int cont;
	boolean alpha100=false, segundo= false;
	float medioY = Config.alto/2;
	Texto configuracion, jugar, logros, salir, mejoras, coords;
	Texto[] opcion;
	int maxopc=5;
	int opc=0, mouseX, mouseY;
	boolean opcmouse=false;
	TecladoMouse entrada = new TecladoMouse();
	Texto titulo;
	
	@Override
	public void show() {
		b = Render.batch;
		fondo= new Imagen(Recursos.FondoMenu, Config.ancho, Config.alto);
		Gdx.input.setInputProcessor(entrada);
		marcos = new Imagen(Recursos.MarcoDorado, 0, 0);
		
		opcion= new Texto[maxopc];	
		
		jugar= new Texto(Recursos.FuenteMenu, 48, Color.WHITE, true);
		jugar.setTexto("Iniciar partida");
		jugar.setPosition(((Config.ancho/2)-(jugar.getAncho()/2)), medioY-100);
		
		logros= new Texto(Recursos.FuenteMenu, 48, Color.WHITE, true);
        logros.setTexto("Logros");
        logros.setPosition(((Config.ancho/2)-jugar.getAncho()-(logros.getAncho()/2)), medioY-100);
		
        mejoras = new Texto(Recursos.FuenteMenu, 48, Color.WHITE, true);	
		mejoras.setTexto("Mejoras");
		mejoras.setPosition(((Config.ancho/2)+jugar.getAncho()-(mejoras.getAncho()/2)),  medioY-100);
		
		configuracion = new Texto(Recursos.FuenteMenu, 48, Color.WHITE, true);
		configuracion.setTexto("Configuración");
		configuracion.setPosition((Config.ancho/2)-configuracion.getAncho()-40, mejoras.getY()-100);
		
		
		salir= new Texto(Recursos.FuenteMenu, 48, Color.WHITE, true);		
		salir.setTexto("Abandonar");
		salir.setPosition((Config.ancho/2)+salir.getAncho()/2, mejoras.getY()-100);
		
		coords = new Texto(Recursos.FuenteMenu, 24, Color.WHITE, true);
		coords.setPosition(0, 600);
		opcion[0]=logros;
		opcion[1]=jugar;
		opcion[2]=mejoras;
		opcion[3]=configuracion;
		opcion[4]=salir;
		titulo = new Texto(Recursos.FuenteMenu, 80, Color.GOLDENROD, true);
		titulo.setTexto("FIM SURVIVOR");
		titulo.setPosition(Config.ancho/2-titulo.getAncho()/2, 600);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		mouseX=entrada.getMouseX();
		mouseY=entrada.getMouseY();
		Render.LimpiarPantalla(0,0,0);
		fade();
		b.begin();
			coords.setTexto("Coord x: "+mouseX+" Coord y: "+mouseY);
			fondo.dibujar();
			
			if (alpha100) {
				
				configuracion.dibujar();
				mejoras.dibujar();
				jugar.dibujar();
				salir.dibujar();
				logros.dibujar();
				titulo.dibujar();
				
				
				//coords.dibujar();
				
				

				opcChange(delta);
				opcEnter();
			}
			
		b.end();
	}

	public void dibujarMarcos() {
		for(int i=0; i< opcion.length;i++) {
			
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
	public void carga() {
		if (alpha < 1) {
			fondo.setAlpha(alpha);
			alpha+=0.025;
		}else {
			alpha100=true;
			
		}
		
	}
	public void fade() {
		if (!alpha100) {
			carga();
		}
		}
	public void opcChange(float delta) {
		cont=0;
		contTiempo+=delta;
		if(contTiempo>0.1) {
			contTiempo=0;
			segundo=true;
		}else {
			segundo=false;
		}
		
		if(entrada.isDer() && segundo) {
			opc++;
			if (opc >maxopc-1) {
				opc=0;
			}
		}
		if (entrada.isIzq() && segundo) {
			opc--;
			if (opc<0){
				opc=maxopc-1;
			}
		}
		if (entrada.isTab() && segundo) {
			opc++;
			if (opc >maxopc-1) {
				opc=0;
			}
		}
		for (int i = 0; i< opcion.length; i++) {
			if (entrada.getMouseX()>=opcion[i].getEsqAbajoIzq().x && entrada.getMouseX() <= opcion[i].getEsqArribaDer().x) {
				if(entrada.getMouseY()>=opcion[i].getEsqAbajoIzq().y && entrada.getMouseY() <= opcion[i].getEsqArribaDer().y) {
					opc=i;
					cont++;
				}
					
			}
		}
		if(cont>0) {
			opcmouse=true;
		}else{
			opcmouse=false;
		}
		for (int i = 0 ;i < opcion.length; i++) {
			if (i == opc) {
				opcion[i].setColor(Color.GOLD);
			}else {
				opcion[i].setColor(Color.WHITE);
			}
		}
	}
	
	public void opcEnter() {
		if (entrada.isEnter() || (entrada.isMouse1()&& opcmouse)) {
			switch (opc) {
			case 0:
				Render.app.setScreen(new PantallaLogro());
				break;
			case 1:
				Render.app.setScreen(new PantallaSelec());
				break;
			case 2:
				Render.app.setScreen(new PantallaMejoras());
				break;
			case 3:
				Render.app.setScreen(new PantallaConfig());
				break;
			case 4:
				Gdx.app.exit();
				break;
			}
		}
	}
}
