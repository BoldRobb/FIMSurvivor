package com.mygdx.fimsurvivor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pantallas.PantallaCarga;
import utiles.Config;
import utiles.Render;

public class fimsurvivor extends Game {


	public static Screen current;
	@Override
	public void create () {
		String rutaArchivo = "config.cfg"; // Cambia esto a la ruta deseada

        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                // El archivo no existe, así que lo creamos
                archivo.createNewFile();
                escribirEnArchivo(archivo, "Score:0");
                Config.highscore=0;
                escribirEnArchivo(archivo, "Dinero:0");
                Config.money=0;
                escribirEnArchivo(archivo, "Logro1:0");
                escribirEnArchivo(archivo, "Logro2:0");
                escribirEnArchivo(archivo, "Logro3:0");
                escribirEnArchivo(archivo, "Logro4:0");
                escribirEnArchivo(archivo, "Logro5:0");
                escribirEnArchivo(archivo, "Logro6:0");
                escribirEnArchivo(archivo, "Logro7:0");
                escribirEnArchivo(archivo, "Logro8:0");
                escribirEnArchivo(archivo, "Logro9:0");
                Config.logro1=false;
                Config.logro2=false;
                Config.logro3=false;
                Config.logro4=false;
                Config.logro5=false;
                Config.logro6=false;
                Config.logro7=false;
                Config.logro8=false;
                Config.logro9=false;
                
            }else {
            	List<String> lineas = Files.lines(Paths.get(rutaArchivo)).collect(Collectors.toList());
            	String score = BuscarEnArchivo(archivo, "Score");
            	Config.highscore = Integer.parseInt(score);
            	String dinero = BuscarEnArchivo(archivo, "Dinero");
            	Config.money = Integer.parseInt(dinero);
            	String did = BuscarEnArchivo(archivo, "Logro1");
            	int didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro1=false;
            	}else {
            		Config.logro1=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro2");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro2=false;
            	}else {
            		Config.logro2=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro3");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro3=false;
            	}else {
            		Config.logro3=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro4");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro4=false;
            	}else {
            		Config.logro4=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro5");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro5=false;
            	}else {
            		Config.logro5=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro6");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro6=false;
            	}else {
            		Config.logro6=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro7");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro7=false;
            	}else {
            		Config.logro7=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro8");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro8=false;
            	}else {
            		Config.logro8=true;
            	}
            	did = BuscarEnArchivo(archivo, "Logro9");
            	didnum = Integer.parseInt(did);
            	if(didnum==0) {
            		Config.logro9=false;
            	}else {
            		Config.logro9=true;
            	}
            }
           

        } catch (IOException e) {
            e.printStackTrace();
        }
		Render.batch = new SpriteBatch();

		Render.app = this;
		current= new PantallaCarga();
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
	public static void escribirEnArchivo(File archivo, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(contenido); // Escribe el contenido en el archivo
            bw.newLine(); // Agrega un salto de línea

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static String BuscarEnArchivo(File archivo, String contenido) {
		String line;
		String texto= "";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        	
            while ((line = br.readLine()) != null) {
                if (line.contains(contenido)) {
                    texto = line;
                    break;
                }
            }
            if (texto != null) {
                int colonIndex = texto.indexOf(':');
                if (colonIndex != -1) {
                    texto = texto.substring(colonIndex + 1).trim(); // Extrae y elimina espacios en blanco
                }
            }

            
        } catch (IOException e) {
            e.printStackTrace();
            texto="";
        }
        
        return texto;
    }
}
