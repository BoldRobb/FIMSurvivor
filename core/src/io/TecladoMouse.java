package io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import utiles.Config;

public class TecladoMouse implements InputProcessor{
	
	private boolean abajo=false, arriba=false, izq=false, der=false, tab = false, enter = false, escape = false;
	

	private int mouseX , mouseY;
	private boolean mouse1=false, mouse2=false, mouse3=false;



	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub

		 if (keycode == Keys.LEFT || keycode == Keys.A) {
			izq=true;
		}
		 if (keycode == Keys.RIGHT || keycode == Keys.D) {
			 der= true;
		 }
		 if (keycode == Keys.UP || keycode == Keys.W) {
			arriba=true;
		}
		 if (keycode == Keys.DOWN || keycode == Keys.S) {
			 abajo= true;
		 }
		if (keycode == Keys.TAB) {
			tab= true;
		}
		if (keycode == Keys.ENTER) {
			enter= true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (keycode == Keys.LEFT || keycode == Keys.A) {

				izq=false;
			}
		if (keycode == Keys.RIGHT || keycode == Keys.D) {
				 der= false;
		}
		 if (keycode == Keys.UP || keycode == Keys.W) {
			arriba=false;
		}
		 if (keycode == Keys.DOWN || keycode == Keys.S) {
			 abajo= false;
		 }
		if (keycode == Keys.TAB) {
					tab= false;
		}
		if (keycode == Keys.ENTER) {
					enter= false;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		mouse1=false;
		mouse2=false;
		mouse3=false;
		if(button==0) {
			mouse1=true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		mouse1=false;
		mouse2=false;
		mouse3=false;
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		
		mouseX=screenX;
		mouseY=Config.alto-screenY;
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isAbajo() {
		return abajo;
	}

	public boolean isArriba() {
		return arriba;
	}

	public boolean isIzq() {
		return izq;
	}

	public boolean isDer() {
		return der;
	}
	public boolean isTab() {
		return tab;
	}
	public boolean isEnter() {
		return enter;
	}
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
	public boolean isMouse1() {
		return mouse1;
	}

	public boolean isMouse2() {
		return mouse2;
	}

	public boolean isMouse3() {
		return mouse3;
	}

}
