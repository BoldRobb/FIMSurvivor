package com.mygdx.fimsurvivor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import elementos.*;
import pantallas.*;
import utiles.Render;

public class fimsurvivor extends Game {


	public static Screen current;
	@Override
	public void create () {
		Render.batch = new SpriteBatch();
		Render.app = this;
		current= new PantallaPartida();
	this.setScreen(current);
		
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();

	}
}
