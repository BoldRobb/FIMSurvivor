package pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import elementos.Imagen;
import utiles.Config;
import utiles.Recursos;
import utiles.Render;

public class PantallaCarga implements Screen{

	Imagen fondo, fondo2;
	SpriteBatch b;
	float alpha = 0, alpha2 = 0;
	boolean alpha100 = false, alpha200=false, cambio = false, cambio2=false;
	float tiempototal;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		b = Render.batch;
		fondo= new Imagen(Recursos.FondoCarga, 1280, 720);
		fondo2 = new Imagen("Fondos/FondoWarning.jpg", Config.ancho, Config.alto);
		fondo2.setAlpha(0f);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.LimpiarPantalla(0,0,0);
		tiempototal+=delta;
		fade();
		b.begin();
			fondo.dibujar();
			fondo2.dibujar();
		b.end();
	}
	public void fade() {
		if (!alpha100) {
			carga();
		}
		if (tiempototal >3.5) {
			descarga();
			if (cambio) {
				if(!alpha200) {
					carga2();
				}
				
				
				
			}
		}
		if(alpha200 && tiempototal >9) {
			descarga2();
			if(cambio2) {
				Render.app.setScreen(new MenuPrincipal());
			}
		}

	}
	public void carga() {
		if (alpha < 1) {
			fondo.setAlpha(alpha);
			alpha+=0.01;
		}else {
			alpha100=true;
			
		}
		
	}
	public void carga2() {
		if (alpha2 < 1) {
			fondo2.setAlpha(alpha2);
			alpha2+=0.01;
		}else {
			alpha200=true;
			
		}
		
	}
	public void descarga2() {
		if (!cambio2) {
			
			fondo2.setAlpha(alpha2);
			alpha2-=0.01;
		}
			
		if (alpha2<0){
			cambio2 = true;
		}
	}
	public void descarga() {
		if (!cambio) {
			
			fondo.setAlpha(alpha);
			alpha-=0.01;
		}
			
		if (alpha<0){
			cambio = true;
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
