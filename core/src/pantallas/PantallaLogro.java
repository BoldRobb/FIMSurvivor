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

public class PantallaLogro implements Screen{

	Imagen fondo;
	Imagen rectArriba, rectMedio, rectNegro;
	Imagen[] checkBoxes;
	Texto[] opcion;
	Texto[] logros;
	SpriteBatch b;
	Texto salir;
	float contTiempo=0;
	boolean segundo = false;
	int opc=0,opcChar=0, mouseX, mouseY, cont, cantopc=1;
	boolean opcmouse=false;
	ShapeRenderer sr;
	int cantCheckBoxes=8;
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
		
		logros= new Texto[8];
		
		checkBoxes = new Imagen[cantCheckBoxes];
		

		
		System.out.println(Config.logro1);
		if(Config.logro1) {
			checkBoxes[0] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[0] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro2) {
			checkBoxes[1] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[1] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro3) {
			checkBoxes[2] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[2] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro4) {
			checkBoxes[3] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[3] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro5) {
			checkBoxes[4] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[4] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro6) {
			checkBoxes[5] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[5] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro7) {
			checkBoxes[6] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[6] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}
		if(Config.logro8) {
			checkBoxes[7] = new Imagen(Recursos.MarcoRojoPaloma, 40, 40);
		}else {
			checkBoxes[7] = new Imagen(Recursos.MarcoRojo, 40, 40);
		}

		
		checkBoxes[0].setPosition(rectMedio.getX()+20, rectMedio.getEsqArribaDer().y-20-checkBoxes[0].getAlto());
		
		logros[0]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[0].setTexto("Sobrevivir 5 minutos");
		logros[0].setPosition(checkBoxes[0].getX()+checkBoxes[0].getAncho()+10,checkBoxes[0].getY()+checkBoxes[0].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[1].setPosition(rectMedio.getX()+20, checkBoxes[0].getY()-30-checkBoxes[0].getAlto());
		
		logros[1]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[1].setTexto("Sobrevivir 10 minutos");
		logros[1].setPosition(checkBoxes[1].getX()+checkBoxes[1].getAncho()+10,checkBoxes[1].getY()+checkBoxes[1].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[2].setPosition(rectMedio.getX()+20, checkBoxes[1].getY()-30-checkBoxes[1].getAlto());
		
		logros[2]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[2].setTexto("Sobrevivir 15 minutos");
		logros[2].setPosition(checkBoxes[2].getX()+checkBoxes[2].getAncho()+10,checkBoxes[2].getY()+checkBoxes[2].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[3].setPosition(rectMedio.getX()+20, checkBoxes[2].getY()-30-checkBoxes[2].getAlto());
		
		logros[3]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[3].setTexto("Sobrevivir 20 minutos");
		logros[3].setPosition(checkBoxes[3].getX()+checkBoxes[3].getAncho()+10,checkBoxes[3].getY()+checkBoxes[3].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[4].setPosition(rectMedio.getX()+20, checkBoxes[3].getY()-30-checkBoxes[3].getAlto());
		
		logros[4]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[4].setTexto("Vencer al Maestro Taker");
		logros[4].setPosition(checkBoxes[4].getX()+checkBoxes[4].getAncho()+10,checkBoxes[4].getY()+checkBoxes[4].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[5].setPosition(rectMedio.getX()+20, checkBoxes[4].getY()-30-checkBoxes[4].getAlto());
		
		logros[5]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[5].setTexto("Vencer al Maestro Yobani");
		logros[5].setPosition(checkBoxes[5].getX()+checkBoxes[5].getAncho()+10,checkBoxes[5].getY()+checkBoxes[5].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[6].setPosition(rectMedio.getX()+20, checkBoxes[5].getY()-30-checkBoxes[5].getAlto());
		
		logros[6]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[6].setTexto("Vencer a la Maestra Karla");
		logros[6].setPosition(checkBoxes[6].getX()+checkBoxes[6].getAncho()+10,checkBoxes[6].getY()+checkBoxes[6].getAlto()/2+logros[0].getAlto()/2);
		
		
		checkBoxes[7].setPosition(rectMedio.getX()+20, checkBoxes[6].getY()-30-checkBoxes[6].getAlto());
		
		logros[7]= new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, false);
		logros[7].setTexto("Vencer al Maestro Alan");
		logros[7].setPosition(checkBoxes[7].getX()+checkBoxes[7].getAncho()+10,checkBoxes[7].getY()+checkBoxes[7].getAlto()/2+logros[0].getAlto()/2);

		
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
		
		for (int i=0;i<checkBoxes.length;i++) {
			checkBoxes[i].dibujar();
		}
		for (int i=0;i<logros.length;i++) {
			logros[i].dibujar();
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




	
}
