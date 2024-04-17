package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import elementos.Imagen;
import elementos.Texto;
import io.TecladoMouse;
import utiles.Config;
import utiles.Recursos;
import utiles.Render;

public class PantallaMejoras  implements Screen{

	Imagen fondo;
	Imagen rectArriba, rectMedio;
	Imagen maestro, alumno, dado;
	Imagen[] marcos;
	Texto[] subir;
	Texto[] opcion;
	SpriteBatch b;
	Texto salir;
	float contTiempo=0;
	boolean segundo = false;
	int opc=0,opcChar=0, mouseX, mouseY, cont, cantopc=1;
	boolean opcmouse=false;
	ShapeRenderer sr;
	int cantMarcos=8, cantSubir=8;
	String obj, salud, velocidad, velocidadAtq;
	TecladoMouse entrada = new TecladoMouse();
	@Override
	public void show() {
		// TODO Auto-generated method stub
		b = Render.batch;
		Gdx.input.setInputProcessor(entrada);
		fondo= new Imagen(Recursos.FondoMenu, Config.ancho, Config.alto);
		rectArriba = new Imagen(Recursos.RectanguloAzul, Config.ancho, 100);
		rectArriba.setPosition(0, 620);
		rectArriba.setAlpha(0.9f);
		
		rectMedio = new Imagen(Recursos.RectanguloAzul, Config.ancho-500, Config.alto-140);
		rectMedio.setPosition(250, 20);
		rectMedio.setAlpha(0.9f);
		


		
		marcos= new Imagen[cantMarcos];
		subir= new Texto[cantSubir];

		crearMejoras();
		
		


		
		
		salir = new Texto(Recursos.FuenteMenu, 40, Color.WHITE, true);
		salir.setTexto("Volver");
		salir.setPosition(Config.ancho - salir.getAncho()-20, Config.alto-30);
		
		
		sr= new ShapeRenderer();
		
		opcion = new Texto[cantopc];
		opcion[0]=salir;

	}

	@Override
	public void render(float delta) {
		Render.LimpiarPantalla(1, 1, 1);
		mouseX=entrada.getMouseX();
		mouseY=entrada.getMouseY();
		
		
		b.begin();
		fondo.dibujar();
		rectArriba.dibujar();
		rectMedio.dibujar();


		salir.dibujar();
		

		for (int i=0;i<marcos.length;i++) {
			marcos[i].dibujar();
		}
		for (int i=0;i<subir.length;i++) {
			subir[i].dibujar();
		}

		
		opcChange(delta);
		opcEnter();

		b.end();
		// TODO Auto-generated method stub
		
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
	public void opcChange(float delta) {
		cont = 0;
		contTiempo+=delta;
		if(contTiempo>0.1) {
			contTiempo=0;
			segundo=true;
		}else {
			segundo=false;
		}
		
		if(entrada.isDer() && segundo) {
			opc++;
			if (opc >cantopc-1) {
				opc=0;
			}
		}
		if (entrada.isIzq() && segundo) {
			opc--;
			if (opc<0){
				opc=cantopc-1;
			}
		}
		if (entrada.isTab() && segundo) {
			opc++;
			if (opc >cantopc-1) {
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
				opcion[i].setColor(Color.YELLOW);
			}else {
				opcion[i].setColor(Color.WHITE);
			}
		}
	}
	
	public void opcEnter() {
		if (entrada.isEnter() || (entrada.isMouse1() && opcmouse)) {
			switch (opc) {
			case 0:
				Render.app.setScreen(new MenuPrincipal());
				break;

			default:
				break;
			}
		}
	}

	public void crearMejoras() {
		
		float dif= (rectMedio.getAncho()-600)/5;
		
		marcos[0]= new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[0].setPosition(rectMedio.getX()+dif, rectMedio.getY()+rectMedio.getAlto()-20-marcos[0].getAlto());
		marcos[0].setAlpha(0.9f);
		
		marcos[1] = new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[1].setPosition(marcos[0].getX()+dif+marcos[0].getAncho(), rectMedio.getY()+rectMedio.getAlto()-20-marcos[0].getAlto());
		marcos[1].setAlpha(0.9f);
		
		marcos[2] = new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[2].setPosition(marcos[1].getX()+dif+marcos[1].getAncho(), rectMedio.getY()+rectMedio.getAlto()-20-marcos[1].getAlto());
		marcos[2].setAlpha(0.9f);
		
		marcos[3]= new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[3].setPosition(marcos[2].getX()+dif+marcos[2].getAncho(), rectMedio.getY()+rectMedio.getAlto()-20-marcos[2].getAlto());
		marcos[3].setAlpha(0.9f);
		
		marcos[4] = new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[4].setPosition(rectMedio.getX()+dif, marcos[0].getY()-marcos[0].getAlto()-60);
		marcos[4].setAlpha(0.9f);
		
		marcos[5] = new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[5].setPosition(marcos[4].getEsqArribaDer().x+dif, marcos[1].getY()-marcos[1].getAlto()-60);
		marcos[5].setAlpha(0.9f);
		
		marcos[6]= new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[6].setPosition(marcos[5].getEsqArribaDer().x+dif, marcos[2].getY()-marcos[2].getAlto()-60);
		marcos[6].setAlpha(0.9f);
		
		marcos[7] = new Imagen(Recursos.MarcoAzul, 150, 200);
		marcos[7].setPosition(marcos[6].getEsqArribaDer().x+dif, marcos[3].getY()-marcos[3].getAlto()-60);
		marcos[7].setAlpha(0.9f);
		
		for(int i=0; i<cantSubir;i++) {
			subir[i] = new Texto(Recursos.FuenteMenu, 25, Color.WHITE, false);
			subir[i].setTexto("Mejorar");
			subir[i].setPosition(marcos[i].getX()+(-subir[i].getAncho()+marcos[i].getAncho())/2, marcos[i].getY()-10);
		}
		
	}


}
