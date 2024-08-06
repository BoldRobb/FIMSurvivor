package pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import elementos.Imagen;
import elementos.Texto;
import io.TecladoMouse;
import utiles.Config;
import utiles.Recursos;
import utiles.Render;

public class PantallaSelec implements Screen{

	Imagen fondo;
	Imagen rectArriba, rectMedio, rectNegro;
	Imagen maestro, alumno, dado;
	Imagen[] marcos;
	Imagen[] checkBoxes;
	Texto[] opcion;
	SpriteBatch b;
	Texto salir, row1, row2, iniciar, tutorial, char1, char2, char3;
	float contTiempo=0;
	boolean segundo = false;
	int opc=0,opcChar=0, mouseX, mouseY, cont, cantopc=3;
	boolean opcmouse=false;
	ShapeRenderer sr;
	int cantMarcos=3, cantCheckBoxes=3;
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
		
		rectNegro = new Imagen(Recursos.RectanguloNegro, Config.ancho-500, 125);
		rectNegro.setPosition(rectMedio.getX(), rectMedio.getY());
		rectNegro.setAlpha(0.7f);
		float dif= (rectMedio.getAncho()-600)/4;
		
		marcos= new Imagen[cantMarcos];
		checkBoxes = new Imagen[cantCheckBoxes];
		
		
		
		marcos[0]= new Imagen(Recursos.MarcoAzul, 200, 300);
		marcos[0].setPosition(rectMedio.getX()+dif, rectMedio.getY()+rectMedio.getAlto()-20-marcos[0].getAlto());
		marcos[0].setAlpha(0.9f);
		
		marcos[1] = new Imagen(Recursos.MarcoAzul, 200, 300);
		marcos[1].setPosition(marcos[0].getX()+dif+marcos[0].getAncho(), rectMedio.getY()+rectMedio.getAlto()-20-marcos[0].getAlto());
		marcos[1].setAlpha(0.9f);
		
		marcos[2] = new Imagen(Recursos.MarcoAzul, 200, 300);
		marcos[2].setPosition(marcos[1].getX()+dif+marcos[1].getAncho(), rectMedio.getY()+rectMedio.getAlto()-20-marcos[1].getAlto());
		marcos[2].setAlpha(0.9f);
		
		alumno = new Imagen("Sprites/EstudiantePrueba.png", 200, 250);
		alumno.setPosition(marcos[0].getX()+10, marcos[0].getY()+10);
		
		maestro = new Imagen("Sprites/MaestroPrueba.png", 200, 250);
		maestro.setPosition(marcos[1].getX()+10, marcos[1].getY()+10);
		
		dado = new Imagen("Sprites/Dado.png", 200, 250);
		dado.setPosition(marcos[2].getX(), marcos[2].getY()+10);
		
		checkBoxes[0] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		checkBoxes[0].setPosition(marcos[0].getX() + marcos[0].getAncho()/2 -20, marcos[0].getY()-50);
		
		checkBoxes[1] = new Imagen(Recursos.MarcoRojo, 40, 40);
		checkBoxes[1].setPosition(marcos[1].getX() + marcos[1].getAncho()/2 -20, marcos[1].getY()-50);

		checkBoxes[2] = new Imagen(Recursos.MarcoRojo, 40, 40);
		checkBoxes[2].setPosition(marcos[2].getX() + marcos[2].getAncho()/2 -20, marcos[2].getY()-50);
		
		
		salir = new Texto(Recursos.FuenteMenu, 40, Color.WHITE, true);
		salir.setTexto("Volver");
		salir.setPosition(Config.ancho - salir.getAncho()-20, Config.alto-30);
		
		row1= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		row1.setTexto("Objeto inicial: "+ obj +" Salud: "+salud);
		row1.setPosition(rectNegro.getX()+15,rectNegro.getY()+rectNegro.getAlto()-30);
		
		row2= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		row2.setTexto("Velocidad: "+ velocidad +" Velocidad de ataque: "+velocidadAtq);
		row2.setPosition(rectNegro.getX()+15,rectNegro.getY()+rectNegro.getAlto()-80);
		
		iniciar = new Texto(Recursos.FuenteMenu, 40, Color.WHITE, true);
		iniciar.setTexto("Iniciar");
		iniciar.setPosition((-rectMedio.getX()-rectMedio.getAncho()-iniciar.getAncho()+Config.ancho)/2+rectMedio.getX()+rectMedio.getAncho(), rectMedio.getY()+iniciar.getAlto()+10);
		
		tutorial = new Texto(Recursos.FuenteMenu, 40, Color.WHITE, true);
		tutorial.setTexto("Tutorial");
		tutorial.setPosition((rectMedio.getX()-tutorial.getAncho())/2, rectMedio.getY()+tutorial.getAlto()+10);
		
		char1 = new Texto(Recursos.FuenteMedieval, 26, Color.WHITE, false);
		char2 = new Texto(Recursos.FuenteMedieval, 26, Color.WHITE, false);
		char3 = new Texto(Recursos.FuenteMedieval, 26, Color.WHITE, false);
		
		char1.setTexto("Alumno");
		char2.setTexto("Maestro");
		char3.setTexto("Aleatorio");
		
		char1.setPosition(marcos[0].getX()+10, marcos[0].getY()+marcos[0].getAlto()-10);
		char2.setPosition(marcos[1].getX()+10, marcos[1].getY()+marcos[1].getAlto()-10);
		char3.setPosition(marcos[2].getX()+10, marcos[2].getY()+marcos[2].getAlto()-10);
		
		sr= new ShapeRenderer();
		
		opcion = new Texto[cantopc];
		opcion[0]=salir;
		opcion[1] = tutorial;
		opcion[2] = iniciar;
		
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
		rectNegro.dibujar();
		row1.setTexto("Objeto inicial: "+ obj +" - Salud: "+salud);
		row2.setTexto("Velocidad: "+ velocidad +" - Velocidad de ataque: "+velocidadAtq);
		row1.dibujar();
		row2.dibujar();
		iniciar.dibujar();
		tutorial.dibujar();
		salir.dibujar();
		

		for (int i=0;i<marcos.length;i++) {
			marcos[i].dibujar();
		}
		
		for (int i=0;i<checkBoxes.length;i++) {
			checkBoxes[i].dibujar();
		}
		char1.dibujar();
		char2.dibujar();
		char3.dibujar();
		maestro.dibujar();
		alumno.dibujar();
		dado.dibujar();
		
		opcChange(delta);
		opcEnter();
		checkChange();
		setStats();
		b.end();
		// TODO Auto-generated method stub
		
	}
	public void setStats() {
		switch(opcChar) {
		case 0:
			obj="Lapiz";
			salud="100";
			velocidad="10";
			velocidadAtq="15";
			break;
		case 1:
			obj="Marcador";
			salud="150";
			velocidad="5";
			velocidadAtq="10";
			break;
		case 2:
			obj="?";
			salud="?";
			velocidad="?";
			velocidadAtq="?";
			break;
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
			case 1:
				Render.app.setScreen(new PantallaTuto());
				break;
			case 2:
				if(opcChar==2) {
					opcChar=(int)Math.round( Math.random() );
				}
				Render.app.setScreen(new PantallaPartida(opcChar));
				break;

			default:
				break;
			}
		}
	}

	public void checkChange() {
		cont=0;
		for (int i = 0; i< checkBoxes.length; i++) {
			if (entrada.getMouseX()>=checkBoxes[i].getEsqAbajoIzq().x && entrada.getMouseX() <= checkBoxes[i].getEsqArribaDer().x) {
				if(entrada.getMouseY()>=checkBoxes[i].getEsqAbajoIzq().y && entrada.getMouseY() <= checkBoxes[i].getEsqArribaDer().y) {
					if(entrada.isMouse1()) {
						opcChar=i;
						cont++;
					}
					
				}
					
			}
		}
		if(cont>0) {
			opcmouse=true;
		}else{
			opcmouse=false;
		}
		if(entrada.isMouse1() && opcmouse) {
			for (int i = 0 ;i < checkBoxes.length; i++) {
				
				if (i == opcChar) {
					checkBoxes[i].setTextura(Recursos.MarcoRojoPaloma);;
				}else {
					checkBoxes[i].setTextura(Recursos.MarcoRojo);
				}
			}
		}

	}
}
