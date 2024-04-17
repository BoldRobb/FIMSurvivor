package com.mygdx.fimsurvivor;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.fimsurvivor.fimsurvivor;

import utiles.Config;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("FIM Survivor");
		config.setResizable(false);
		config.setWindowedMode(Config.ancho, Config.alto);
		config.setWindowIcon("UAS.png");
		new Lwjgl3Application(new fimsurvivor(), config);
	}
}
