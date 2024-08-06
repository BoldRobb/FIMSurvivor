package io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import utiles.Config;

public class TecladoMouse implements InputProcessor{
	
	private boolean abajo=false, arriba=false, izq=false, der=false, tab = false, enter = false, escape = false;
	private boolean control=false;
	
	private boolean n1=false, n2=false ,n3=false, n4=false, n5=false, n6=false, n7=false, n8=false, n9=false, n0=false;


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
		if(keycode == Keys.ESCAPE) {
			escape = true;
		}
		if(keycode == Keys.CONTROL_LEFT) {
			control = true;
		}
		if(keycode == Keys.NUM_0) {
			n0= true;
		}
		if(keycode == Keys.NUM_1) {
			n1= true;
		}

		if(keycode == Keys.NUM_2) {
			n2= true;
		}

		if(keycode == Keys.NUM_3) {
			n3= true;
		}

		if(keycode == Keys.NUM_4) {
			n4= true;
		}

		if(keycode == Keys.NUM_5) {
			n5= true;
		}

		if(keycode == Keys.NUM_6) {
			n6= true;
		}
		if(keycode == Keys.NUM_7) {
			n7= true;
		}

		if(keycode == Keys.NUM_8) {
			n8= true;
		}

		if(keycode == Keys.NUM_9) {
			n9= true;
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
		if(keycode == Keys.ESCAPE) {
			escape = false;
		}
		if(keycode == Keys.CONTROL_LEFT) {
			control = false;
		}
		if(keycode == Keys.NUM_0) {
			n0= false;
		}
		if(keycode == Keys.NUM_1) {
			n1= false;
		}

		if(keycode == Keys.NUM_2) {
			n2= false;
		}

		if(keycode == Keys.NUM_3) {
			n3= false;
		}

		if(keycode == Keys.NUM_4) {
			n4= false;
		}

		if(keycode == Keys.NUM_5) {
			n5= false;
		}

		if(keycode == Keys.NUM_6) {
			n6= false;
		}
		if(keycode == Keys.NUM_7) {
			n7= false;
		}

		if(keycode == Keys.NUM_8) {
			n8= false;
		}

		if(keycode == Keys.NUM_9) {
			n9= false;
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

	public boolean isEscape() {
		return escape;
	}
	public boolean isControl() {
		return control;
	}

	public boolean isN1() {
		return n1;
	}

	public boolean isN2() {
		return n2;
	}

	public boolean isN3() {
		return n3;
	}

	public boolean isN4() {
		return n4;
	}

	public boolean isN5() {
		return n5;
	}

	public boolean isN6() {
		return n6;
	}

	public boolean isN7() {
		return n7;
	}

	public boolean isN8() {
		return n8;
	}

	public boolean isN9() {
		return n9;
	}

	public boolean isN0() {
		return n0;
	}

}
