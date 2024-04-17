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

public class PantallaConfig implements Screen{

	Imagen fondo;
	Imagen rectArriba, rectMedio, rectNegro, barraVolumen, esferaVolumen, barraMusica, esferaMusica;
	Imagen[] checkBoxes;
	Imagen[] sliders;
	Texto[] opcion;

	Texto abrir1, abrir2;
	Texto musica, volumen, particulas, numeros, politicas, preguntas, guardar, salir, resoluciones;
	Imagen guardado;
	
	SpriteBatch b;
	float contTiempo=0, alphaGuardado=0;
	boolean segundo = false, dibujarGuardado = false;
	boolean isParticulas = true, isNumeros = true;
	int opc=0,opcChar=0, mouseX, mouseY, cont, cantopc=2, checkopc, contcheck;
	boolean opcmouse, opccheck;
	ShapeRenderer sr;
	int cantCheckBoxes=2;
	TecladoMouse entrada = new TecladoMouse();
	
	@Override
	public void show() {
		sr = new ShapeRenderer();
		b = Render.batch;
		Gdx.input.setInputProcessor(entrada);
		rectArriba = new Imagen(Recursos.RectanguloAzul, Config.ancho, 100);
		rectArriba.setPosition(0, 620);
		rectArriba.setAlpha(0.9f);
		
		
		
		rectMedio = new Imagen(Recursos.RectanguloAzul, Config.ancho-500, Config.alto-140);
		rectMedio.setPosition(250, 20);
		rectMedio.setAlpha(0.9f);
		
		guardado = new Imagen("Formas/configGuardada.PNG", rectMedio.getAncho()+100, 80);
		guardado.setPosition(200, 306);
		guardado.setAlpha(0f);
		musica = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		musica.setTexto("Volumen de musica");
		musica.setPosition(rectMedio.getX()+20, rectMedio.getEsqArribaDer().y-40);
		
		volumen = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		volumen.setTexto("Volumen general");
		volumen.setPosition(musica.getX(),musica.getY()-50);
		
		resoluciones = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		resoluciones.setTexto("Resolucion");
		resoluciones.setPosition(volumen.getX(),volumen.getY()-50);
		
		particulas = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		particulas.setTexto("Efectos de particulas");
		particulas.setPosition(resoluciones.getX(),resoluciones.getY()-50);
		
		numeros = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		numeros.setTexto("Numeros de daño");
		numeros.setPosition(particulas.getX(),particulas.getY()-50);
		
		politicas= new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		politicas.setTexto("Politicas de privacidad");
		politicas.setPosition(numeros.getX(),numeros.getY()-50);
		
		preguntas = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, false);
		preguntas.setTexto("Preguntas frecuentes");
		preguntas.setPosition(politicas.getX(),politicas.getY()-50);
		
		guardar = new Texto(Recursos.FuenteMedieval, 36,Color.WHITE, true);
		guardar.setTexto("Guardar");
		guardar.setPosition(rectMedio.getEsqArribaDer().x-guardar.getAncho()-20, rectMedio.getEsqAbajoIzq().y+guardar.getAlto()+20);
		
		
		
		fondo= new Imagen(Recursos.FondoMenu, Config.ancho, Config.alto);

		
		barraVolumen = new Imagen(Recursos.BarraAzul, 300, 16);
		barraVolumen.setPosition(538, volumen.getY()-volumen.getAlto());
		
		esferaVolumen = new Imagen(Recursos.EsferaAzul, 24, 24);
		esferaVolumen.setPosition(530, barraVolumen.getY()-4);
		
		barraMusica = new Imagen(Recursos.BarraAzul, 300, 16);
		barraMusica.setPosition(538, musica.getY()-musica.getAlto());
		
		esferaMusica = new Imagen(Recursos.EsferaAzul, 24, 24);
		esferaMusica.setPosition(530, barraMusica.getY()-4);
		
		checkBoxes = new Imagen[cantCheckBoxes];
		checkBoxes[0] = new Imagen(Recursos.MarcoRojoPaloma,30, 30);
		checkBoxes[0].setPosition(538, particulas.getY()- checkBoxes[0].getAlto()+5);

		checkBoxes[1] = new Imagen(Recursos.MarcoRojoPaloma, 30, 30);
		checkBoxes[1].setPosition(538, numeros.getY()- checkBoxes[0].getAlto()+5);

		
		abrir1 = new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, true);
		abrir1.setTexto("Abrir");
		abrir1.setPosition(politicas.getX()+politicas.getAncho()+20, politicas.getY()+6);
		
		abrir2 = new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, true);
		abrir2.setTexto("Abrir");
		abrir2.setPosition(abrir1.getX(), preguntas.getY()+6);
		
		salir = new Texto(Recursos.FuenteMenu, 40, Color.WHITE, true);
		salir.setTexto("Volver");
		salir.setPosition(Config.ancho - salir.getAncho()-20, Config.alto-30);
		

		
		sr= new ShapeRenderer();
		
		opcion = new Texto[cantopc];
		opcion[0]=salir;
		opcion[1]=guardar;
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
		barraVolumen.dibujar();
		esferaVolumen.dibujar();
		musica.dibujar();
		volumen.dibujar();
		particulas.dibujar();
		numeros.dibujar();
		politicas.dibujar();
		preguntas.dibujar();
		guardar.dibujar();
		resoluciones.dibujar();
		checkBoxes[0].dibujar();
		checkBoxes[1].dibujar();
		barraMusica.dibujar();
		esferaMusica.dibujar();
		salir.dibujar();
		
		abrir1.dibujar();
		abrir2.dibujar();
		guardado.dibujar();
		abrir1.dibujar();

		sr.begin(ShapeType.Line);
		sr.setColor(Color.GOLD);
		sr.rect(abrir1.getX()-5, abrir1.getY()-abrir1.getAlto()-10, abrir1.getAncho()+10, abrir1.getAlto()+15);
		sr.rect(abrir2.getX()-5, abrir2.getY()-abrir2.getAlto()-10, abrir2.getAncho()+10, abrir2.getAlto()+15);
		sr.end();
		
		if(dibujarGuardado) {
			fadeGuardar();
		}


		opcChange(delta);
		opcEnter();
		checkChange();
		b.end();
		
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
				alphaGuardado = 0.85f;
				dibujarGuardado=true;
				break;
			default:
				break;
			}
		}
	}
	public void checkChange() {
		if(!entrada.isMouse1()) {
			contcheck=0;
		}
		opccheck=false;
		
		for (int i = 0; i< checkBoxes.length; i++) {
			if (entrada.getMouseX()>=checkBoxes[i].getEsqAbajoIzq().x && entrada.getMouseX() <= checkBoxes[i].getEsqArribaDer().x) {
				if(entrada.getMouseY()>=checkBoxes[i].getEsqAbajoIzq().y && entrada.getMouseY() <= checkBoxes[i].getEsqArribaDer().y) {
					if(entrada.isMouse1()) {
						opcChar=i;
						contcheck++;



					}
					
				}
					
			}
		}
		if(contcheck==1 ) {
			opccheck=true;
		}else{
			opccheck=false;
		}

		if(opcChar==0 && opccheck) {

				if (isParticulas) {
					checkBoxes[0].setTextura(Recursos.MarcoRojo);
					isParticulas=false;
				}else {
					checkBoxes[0].setTextura(Recursos.MarcoRojoPaloma);
					isParticulas=true;
					
				}
			
			
		}
		if(opcChar==1 && opccheck) {
			if (isNumeros) {
				checkBoxes[1].setTextura(Recursos.MarcoRojo);
				isNumeros=false;
			}else {
				checkBoxes[1].setTextura(Recursos.MarcoRojoPaloma);
				isNumeros=true;
			}
		}


	}
	public void fadeGuardar() {
		if(alphaGuardado<0) {
			alphaGuardado=0;
			dibujarGuardado=false;
		}else {
			alphaGuardado-=0.01;
			
		}
		guardado.setAlpha(alphaGuardado);
	}
}

