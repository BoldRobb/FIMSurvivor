package pantallas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import Items.Cafe;
import Items.Cigarro;
import Items.ExpBar;
import Items.LEDexp;
import Items.LifeBar;
import armas.Desodorante;
import armas.Lapiz;
import armas.Marcador;
import elementos.Imagen;
import elementos.Interactable;
import elementos.Texto;
import elementos.TextoFlotante;
import io.Contacto;
import io.TecladoMouse;
import personajes.AguilaEnemy;
import personajes.Alumno;
import personajes.AlumnoEnemy;
import personajes.AlumnoEnemy2;
import personajes.Enemigos;
import personajes.MAlan;
import personajes.MFigueroa;
import personajes.MKarla;
import personajes.MTaker;
import personajes.MYobani;
import personajes.PerroEnemy;
import personajes.Profesor;
import personajes.ProfesorEnemy;
import personajes.Recursador;
import utiles.Config;
import utiles.EnemyFactory;
import utiles.Recursos;
import utiles.Render;

public class PantallaPartida implements Screen {
	
	Skin skin = new Skin(Gdx.files.internal("skinmedievo.json")) {
        //Override json loader to process FreeType fonts from skin JSON
        @Override
        protected Json getJsonLoader(final FileHandle skinFile) {
            Json json = super.getJsonLoader(skinFile);
            final Skin skin = this;
            
            
            json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
                @Override
                public FreeTypeFontGenerator read(Json json,
                        JsonValue jsonData, Class type) {
                    String path = json.readValue("font", String.class, jsonData);
                    jsonData.remove("font");

                    Hinting hinting = Hinting.valueOf(json.readValue("hinting", 
                            String.class, "AutoMedium", jsonData));
                    jsonData.remove("hinting");

                    TextureFilter minFilter = TextureFilter.valueOf(
                            json.readValue("minFilter", String.class, "Nearest", jsonData));
                    jsonData.remove("minFilter");

                    TextureFilter magFilter = TextureFilter.valueOf(
                            json.readValue("magFilter", String.class, "Nearest", jsonData));
                    jsonData.remove("magFilter");

                    FreeTypeFontParameter parameter = json.readValue(FreeTypeFontParameter.class, jsonData);
                    parameter.hinting = hinting;
                    parameter.minFilter = minFilter;
                    parameter.magFilter = magFilter;
                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                    BitmapFont font = generator.generateFont(parameter);
                    skin.add(jsonData.name, font);
                    if (parameter.incremental) {
                        generator.dispose();
                        return null;
                    } else {
                        return generator;
                    }
                }
            });

            return json;
        }
    };
    Imagen centroDebug;
    boolean opcmouse, opccheck, isNumeros, isParticulas, isAlive=true;
	int contcheck;
	float alphaGuardado=0;
	boolean dibujarGuardado=false;
	int opcChar;
	Imagen hasMuerto;
	Slider musicaSlide, volumenSlide;
    ExpBar barraXP;
    LifeBar barraVida;
	Imagen fondo;
	Imagen fondoAdentro, hojaTexto, pizarron;
	int ppm = Render.PPM;
	Texto coords;
	SpriteBatch b;
	float tiempoPartida, tiempoPasado, tresSec, posX, posY, angle= 0;
	int randomEsquina, randomX, randomY;
	int velocidad=3;
	float contactTimer=0, puntuacionTimer=0;
	OrthographicCamera camera;
	World mundo;
	Alumno AlumnoPlayer;
	Profesor MaestroPlayer;
	float playerVel=2;
	Enemigos enemy;
	ShapeRenderer sr;
	Texto puntuacion, timer, vida;
	int puntuacionint=0;
	float tiempoIRL=0;
	float cooldownLapiz=0, cooldownMarcador=0, cooldownDesodorante=0;
	int opc=0, mouseX, mouseY;
	int opcPersonaje;
    Body player;
    String localPJ;
    Vector2 centroPJ, posPJ;
    float anchoPJ, altoPJ;
    float consumibleTimer=0;
    float cooldownHoja=1, alphaHoja=0;

	TecladoMouse entrada = new TecladoMouse();
	EnemyFactory factory = new EnemyFactory();
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	List<Enemigos> currentEnemies;
    List<Body> arrBodies;
    List<TextoFlotante> danos;
    List<Body> terreno;
    List<Interactable> interactables;
    List<Imagen> enters;
    Interactable currentInteractable;
    float multiplicadorLapiz=2, multiplicadorMarcador=2;
    float cdLapiz=1, cdMarcador=1.2f;
    int currentVida;
    boolean hojaTextoFaded=false;
    float cooldownPizarron=1.5f, alphaPizarron=0;
    float cooldownMuerto=3f, alphaMuerto=0;
    boolean pizarronFaded=false, eventoPizarron = false, muertoFaded=false;
    boolean Started=false;
    
    boolean isKarla=false, isYobani=false, isAlan=false, isTaker=false, isFigue=false;
    
    boolean debugMode=false;
//Armas
    
boolean haveLapiz = false, haveMarcador = false, haveDesodorante = false;    
    
//

   // float velocidadMAX=700;
    Vector2 velocidadActual= new Vector2(0,0);
    Contacto contact;
    Lapiz lapiz;
    Marcador marcador;
    float currentXP=0, nextlvlXP=20;
    Boolean pause = false;
    Boolean eventoTextoPapel=false;
    InputMultiplexer inputMultiplexer;
    int currentLVL=1;
    Stage stage, stage2;
	//Skin skin = new Skin(Gdx.files.internal("nuevaskin.json"));
    Imagen rectArriba, rectMedio, rectNegro, marcoTriangulo;
	Imagen[] checkBoxes;
	Texto[] opcion;
	Preferences preferencias;
	Texto abrir1, reanudar;
	Texto musica, volumen, particulas, numeros, politicas, preguntas, guardar, salir, resoluciones;
	Imagen guardado;
	boolean isWalking=false;
	boolean aparicionKarla = false, aparicionYobani = false, aparicionAlan=false, aparicionTaker=false, aparicionFigue=false;
    public PantallaPartida(int personaje) {
    	this.opcPersonaje=personaje;
    }
   
	@Override
	public void show() {
		sr= new ShapeRenderer();
		mundo = new World(new Vector2(0,0), false);
		arrBodies = new ArrayList<Body>();
		currentEnemies = new ArrayList<Enemigos>();
		danos = new ArrayList<TextoFlotante>();
		terreno = new ArrayList<Body>();
		interactables = new ArrayList<Interactable>();
		enters = new ArrayList<Imagen>();
		
		fondo = new Imagen(Recursos.MapaPrincipal, 9600,9600);
		//fondo.setPosition(0, 0);
		fondoAdentro= new Imagen(Recursos.MapaAdentro, 1536, 960);
		fondoAdentro.setPosition(-1596, -1500);
		pizarron = new Imagen(Recursos.Pizarron, 888, 500);
		crearMapa();
		b = Render.batch;
		stage = new Stage();
		stage2 = new Stage();
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(stage2);
        inputMultiplexer.addProcessor(entrada);
		Gdx.input.setInputProcessor(inputMultiplexer);
		//1440*960
		centroDebug= new Imagen("Formas/CruzDebug.png", 1280, 720);
		hasMuerto = new Imagen("Fondos/youded.png", Config.ancho, 220);
		hasMuerto.setAlpha(0);
		player = createBox(ppm,ppm*2, new Vector2 (-1320,-1320), BodyType.DynamicBody);
		coords = new Texto(Recursos.FuenteMenu, 24, Color.WHITE, true);
		barraXP = new ExpBar(player.getPosition().x+50,player.getPosition().y+100, 60, 20);
		barraXP.setValorMax(nextlvlXP);
		barraVida = new LifeBar(player.getPosition().x+50,player.getPosition().y+100, 60, 20);
		coords.setPosition(0, 600);
		if(opcPersonaje==0) {
			AlumnoPlayer = new Alumno((int)player.getPosition().x, (int)player.getPosition().y, ppm, ppm*2, 500, player, mundo);
			AlumnoPlayer.setVida(100);
			currentVida=100;
			localPJ="alumno";
			centroPJ=AlumnoPlayer.getCentro();
			posPJ = AlumnoPlayer.getPosition();
			anchoPJ =AlumnoPlayer.getAncho();
			altoPJ=AlumnoPlayer.getAlto();
			haveLapiz=true;
			player.setUserData(AlumnoPlayer);
			barraVida.setValorMax(100);
			barraVida.setValorActual(100);
		}else {
			MaestroPlayer = new Profesor((int)player.getPosition().x, (int)player.getPosition().y, ppm, ppm*2, 500, player, mundo);
			MaestroPlayer.setVida(150);
			currentVida=150;
			localPJ="maestro";
			centroPJ=MaestroPlayer.getCentro();
			posPJ = MaestroPlayer.getPosition();
			anchoPJ =MaestroPlayer.getAncho();
			altoPJ=MaestroPlayer.getAlto();
			haveMarcador=true;
			player.setUserData(MaestroPlayer);
			barraVida.setValorMax(150);
			barraVida.setValorActual(150);
		}
		
		
		
		contact = new Contacto();
		puntuacion = new Texto(Recursos.FuenteMedieval, 32, Color.WHITE, true);
		timer= new Texto(Recursos.FuenteMedieval, 32, Color.WHITE, true);
		
		vida= new Texto(Recursos.FuenteMedieval, 32, Color.WHITE, true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.ancho,Config.alto);
		hojaTexto = new Imagen(Recursos.PapelTexto, 1200, 200 );
		crearContactListener();
		preferencias = Gdx.app.getPreferences("config");
		
		//Menu de pausa
		crearMenuPausa();
		
		Render.menuMusic.stop();
		Render.gameMusic.setLooping(true);
		Render.gameMusic.play();
		Render.gameMusic.setVolume(Config.musica);
		
	}

	@Override
	public void render(float delta) {
		pauseStatus();
		mouseX=entrada.getMouseX();
		mouseY=entrada.getMouseY();
		Render.LimpiarPantalla(0,0,0);
		
		int minutos=0, segundos;
		centroDebug.setAlpha(0);
		b.setProjectionMatrix(camera.combined); 
		b.begin();
		fondo.dibujar();
		fondoAdentro.dibujar();
		factory.dibujarEnemigos();
		dibujarBullets(new Vector2(player.getPosition().x+ppm/2,player.getPosition().y+ppm));
		if(localPJ=="alumno") {
			AlumnoPlayer.dibujar();
			if(isWalking) {
				AlumnoPlayer.update(delta);
			}
			
		}else {
			MaestroPlayer.dibujar();
			if(isWalking) {
				MaestroPlayer.update(delta);
			}
			
		}
		isWalking=false;
		//Logica para enemigos y balas
		//factory.acercarEnemigos(player.getPosition().x, player.getPosition().y);	
		
		if(tiempoIRL>60) {
			minutos=(int) (tiempoIRL/60);
			segundos=(int) (tiempoIRL-(60*minutos));
		}else {
			segundos=(int)(tiempoIRL);
		}
		
		if(tiempoIRL>300) {
			if(Config.logro1==false) {
				Config.logro1=true;
			}
		}
		if(tiempoIRL>600) {
			if(Config.logro2==false) {
				Config.logro2=true;
			}
		}
		if(tiempoIRL>900) {
			if(Config.logro3==false) {
				Config.logro3=true;
			}
		}
		if(tiempoIRL>1200) {
			if(Config.logro4==false) {
				Config.logro4=true;
			}
		}
		
		if(tiempoIRL>=300 && aparicionKarla==false) {
			aparicionKarla=true;
			tiempoIRL+=delta;
			isKarla=true;
			MKarla maestra = new MKarla((int)posPJ.x+400, (int)posPJ.y, mundo);
			
		}
		if(tiempoIRL>=480 && aparicionYobani==false) {
			aparicionYobani=true;
			tiempoIRL+=delta;
			isYobani=true;
			MYobani maestra = new MYobani((int)posPJ.x+400, (int)posPJ.y, mundo);
		}
		if(tiempoIRL>=660 && aparicionAlan==false) {
			aparicionAlan=true;
			tiempoIRL+=delta;
			isAlan=true;
			MAlan maestra = new MAlan((int)posPJ.x+400, (int)posPJ.y, mundo);
		}
		if(tiempoIRL>=900 && aparicionTaker==false) {
			aparicionTaker=true;
			tiempoIRL+=delta;
			isTaker=true;
			MTaker maestra = new MTaker((int)posPJ.x+400, (int)posPJ.y, mundo);
		}
		if(tiempoIRL>=1200 && aparicionFigue==false) {
			aparicionFigue=true;
			tiempoIRL+=delta;
			isFigue=true;
			MFigueroa maestra = new MFigueroa((int)posPJ.x+400, (int)posPJ.y, mundo);
		}
		timer.setTexto(minutos+":"+segundos);
		//timer.setTexto("Velocidad: "+player.getLinearVelocity());
		if(localPJ=="alumno") {
			currentVida=(int)AlumnoPlayer.getVida();
			vida.setTexto("VIDA: "+ AlumnoPlayer.getVida());
		}else {
			currentVida=(int)MaestroPlayer.getVida();
			vida.setTexto("VIDA: "+ MaestroPlayer.getVida());
		}
		dibujarEnemigos(delta);
		
		dibujarConsumibles();
		dibujardanos(delta);
		dibujarEnter(delta);
		fadeHoja(delta);
		fadePizarron(delta);
		
		
		
		if(currentXP>nextlvlXP) {
			currentXP-=nextlvlXP;
			nextlvlXP*=1.2;
			System.out.println(nextlvlXP);
			barraXP.setValorMax(nextlvlXP);
			if(localPJ=="alumno") {
				AlumnoPlayer.setVidamax(AlumnoPlayer.getVidamax()+10);
				
			}else {
				MaestroPlayer.setVidamax(MaestroPlayer.getVidamax()+10);
			}
			TextoFlotante danio3 = new TextoFlotante(Recursos.FuenteMedieval, 24, Color.GREEN, new Vector2(posPJ.x,posPJ.y+40), 1.5f);
			danio3.setTexto("LVL UP+");
			TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 24, Color.GOLD, new Vector2(posPJ.x,posPJ.y+20), 1.5f);
			danio.setTexto("Salud max+");
			
			TextoFlotante danio2 = new TextoFlotante(Recursos.FuenteMedieval, 24, Color.GOLD, new Vector2(posPJ.x,posPJ.y), 1.5f);
			danio2.setTexto("Velocidad ataque+");
			TextoFlotante danio4 = new TextoFlotante(Recursos.FuenteMedieval, 24, Color.GOLD, new Vector2(posPJ.x+60,posPJ.y+60), 1.5f);
			danio4.setTexto("Daño+");
			danos.add(danio3);
			danos.add(danio);
			danos.add(danio2);
			danos.add(danio4);
			multiplicadorMarcador+=.5;
			multiplicadorLapiz+=.5;
			currentLVL+=1;
			cdLapiz-=.05;
			cdMarcador-=.05;
		}
		stage.act(delta);
		barraVida.update(posPJ, currentVida);
		barraXP.update(new Vector2(posPJ.x, posPJ.y-13), currentXP);
		barraXP.dibujar();
		barraVida.dibujar();
		puntuacion.dibujar();
		timer.dibujar();
		
		coords.dibujar();
		vida.dibujar();
		coords.dibujar();
		
		hasMuerto.setPosition(camera.position.x - Config.ancho/2, camera.position.y-hasMuerto.getAlto()/2);
		hasMuerto.dibujar();
		hojaTexto.setPosition(camera.position.x - Config.ancho/2+60, camera.position.y- Config.alto/2);
		hojaTexto.dibujar();
		pizarron.setPosition(camera.position.x-pizarron.getAncho()/2, camera.position.y-pizarron.getAlto()/2);
		pizarron.dibujar();
		if(!eventoPizarron) {
			alphaPizarron=0;
			pizarron.setAlpha(alphaPizarron);
		}
		if(!eventoTextoPapel) {
			alphaHoja=0;
			hojaTexto.setAlpha(0);
		}
		//stage.draw();
		
		
		
		
		puntuacion.setTexto("PUNTOS: "+puntuacionint );
		//proyeccion de items
		coords.setTexto("X: "+camera.position.x+" Y: "+camera.position.y);
		coords.setPosition(puntuacion.getX(), puntuacion.getY()-100);
	       //proyeccion de mundo y dibujo de personajes
		puntuacion.setPosition(camera.position.x-Config.ancho/2+10, camera.position.y+Config.alto/2-puntuacion.getAlto()/2);
	     timer.setPosition(camera.position.x-timer.getAncho()/2,camera.position.y+Config.alto/2-timer.getAlto()/2);
	     vida.setPosition(camera.position.x-vida.getAncho()+Config.ancho/2-10,camera.position.y+Config.alto/2-vida.getAlto()/2);
        
		if(pause) {
			
		       menuPausa();
		       if(dibujarGuardado) {
					fadeGuardar();
				}
		       opcChange(delta);
				opcEnter();
				checkChange();
		}else if(!isAlive) {
			fadeMuerto(delta);
			deadScreen();
			checkEnter();
		}else if(isKarla) {
			
			aparicionKarla();
			checkEnter();
		}else if (isYobani){
			
			aparicionYobani();
			checkEnter();
			
		}else if (isAlan){
			
			aparicionAlan();
			checkEnter();
			
		}else if (isTaker){
			
			aparicionTaker();
			checkEnter();
			
		}else if (isFigue){
			
			aparicionFigue();
			checkEnter();
			
		}else if (debugMode) {

			
			moverDebug();
			debugRenderer.render(mundo, camera.combined);
			
		}else {
			camera.position.set(player.getPosition().x,player.getPosition().y,0);
			
			if (Started) {
				puntuacionTimer+=delta;
				spawnEnemigos();
				generarConsumibles();
				tiempoIRL+=delta;
			}
			//Cooldowns y timer
			musica.dibujar();
			cooldownMarcador+=delta;
			cooldownLapiz+=delta;
			tresSec+=delta;
			contactTimer+=delta;
			consumibleTimer+=delta;
			cooldownDesodorante+=delta;

			
			if(puntuacionTimer>1) {
				puntuacionint+=10;
				puntuacionTimer=0;
			}

			acercarEnemigos();
			detectarEnemigos();

			//Deteccion de movimiento
			checkEntrada();
			//Debug y pasos del mundo
			
			mundo.step(1/60f, 6, 2);
			
		}
		camera.update();
		
		stage.draw();

		b.end();
		
		
		
		checkCodes();
		checkVida();
	}
	
	private void moverDebug() {
		float currentX = camera.position.x;
		float currentY = camera.position.y;
		if(entrada.isArriba()) {
			currentY+=10;
			
		}
		if(entrada.isAbajo()) {
			currentY-=10;
			
		}
		if(entrada.isIzq()) {
			currentX-=10;
			
		}
		if(entrada.isDer()) {
			currentX+=10;
			
		}
		camera.position.set(currentX,currentY,0);
		
	}
	
	public void menuPausa() {
		
		rectMedio.setPosition(camera.position.x-Config.ancho/2 + 150, camera.position.y-Config.alto/2 + 25);
		musica.setPosition(rectMedio.getX()+30,rectMedio.getEsqArribaDer().y-30);
		volumen.setPosition(musica.getX(), musica.getY()-60);
		particulas.setPosition(volumen.getX(), volumen.getY()-60);
		numeros.setPosition(particulas.getX(), particulas.getY()-60);
		guardar.setPosition(rectMedio.getEsqArribaDer().x-guardar.getAncho()-20, rectMedio.getY()+20+guardar.getAlto());
		resoluciones.setPosition(numeros.getX(), numeros.getY()-60);
		guardado.setPosition(posPJ.x-Config.ancho/2+150, posPJ.y);
		checkBoxes[0].setPosition(particulas.getX()+particulas.getAncho()+10, particulas.getY()-checkBoxes[0].getAlto());
		checkBoxes[1].setPosition(numeros.getX()+numeros.getAncho()+10, numeros.getY()-checkBoxes[0].getAlto());
		marcoTriangulo.setPosition(resoluciones.getX()+resoluciones.getAncho()+10, resoluciones.getY()-resoluciones.getAlto()-10);
		salir.setPosition(rectMedio.getEsqArribaDer().x-salir.getAncho()-10, rectMedio.getEsqArribaDer().y-10);
		reanudar.setPosition(rectMedio.getX()+10, rectMedio.getY()+reanudar.getAlto()+10);
		rectMedio.dibujar();
		musicaSlide.setPosition(420, 640);
		volumenSlide.setPosition(420, 580);
		musica.dibujar();
		volumen.dibujar();
		particulas.dibujar();
		numeros.dibujar();
		guardado.dibujar();
		guardar.dibujar();
		resoluciones.dibujar();
		checkBoxes[0].dibujar();
		checkBoxes[1].dibujar();
		salir.dibujar();
		marcoTriangulo.dibujar();
		
		//abrir1.dibujar();
		reanudar.dibujar();
		guardado.dibujar();
		//abrir1.dibujar();
		stage2.draw();
	}
	
	public void deadScreen() {
		
		
		TypingLabel label = new TypingLabel("Presiona enter para continuar", skin);

		label.setPosition(Config.ancho/2-label.getWidth()/2, 110);

		stage.addActor(label);
	}
	public void fadeMuerto(float delta) {
		
			alphaMuerto+=delta/2;
			if(alphaMuerto<0.9) {
				
				hasMuerto.setAlpha(alphaMuerto);
			}else {
				hasMuerto.setAlpha(0.9f);
				
			}
		
		
	}
	public void aparicionYobani() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("Como dice el autor, ponganse a chambear", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
	}
	public void aparicionKarla() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("No se trabaja con dos unidades de memoria al mismo tiempo", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
		
		
	}
	public void aparicionAlan() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("Una pizzita de piña compañeros", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
		
		
	}
	public void aparicionTaker() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("Rest in peace...", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
		
		
	}
	public void aparicionFigue() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("Votaste por Xotchil, estas reprobado", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
		
		
	}
	public void checkEnter() {
	
		if(entrada.isEnter()) {
			isKarla=false;
			isYobani=false;
			isAlan=false;
			isTaker=false;
			isFigue=false;
			stage.clear();
			eventoTextoPapel=false;
			if(!isAlive) {
				
				Render.app.setScreen(new pantallaPuntuacion(puntuacionint));
			}
		}
	}
	private void checkCodes() {
		if (entrada.isControl()) {
			if (entrada.isN0()) {
				tiempoIRL=0;
			}else if (entrada.isN0()) {
				tiempoIRL=0;
			}else if (entrada.isN1()) {
				tiempoIRL=180;
			}else if (entrada.isN2()) {
				tiempoIRL=300;
			}else if (entrada.isN3()) {
				tiempoIRL=450;
			}else if (entrada.isN4()) {
				tiempoIRL=600;
			}else if (entrada.isN5()) {
				tiempoIRL=900;
			}
			
			if(entrada.isTab()) {
				if(debugMode) {
					debugMode=false;
				}else {
					debugMode=true;
				}
			}
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
		stage.dispose();
		stage2.dispose();
		mundo.dispose();
	}
	public void dibujarEnter(float delta) {
		if(!enters.isEmpty()) {	
			 Iterator<Imagen> iterador = enters.iterator();
		        while (iterador.hasNext()) {
		            Imagen imagen = iterador.next();
		            imagen.update(delta, player.getPosition());
		            imagen.dibujar();		            
		        }
		        
		}
	}
	public void crearMapa() {
		//SALON DE ADENTRO
		terreno.add(createPared( 1440, 10, new Vector2(-780, -1505)));
		terreno.add(createPared( 1440, 10, new Vector2(-780, -535)));
		terreno.add(createPared( 10, 980, new Vector2(-1505, -1020)));
		terreno.add(createPared( 10, 980, new Vector2(-55, -1020)));
		terreno.add(createPared( 48, 140, new Vector2(-1270, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-1056, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-856, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-656, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-471, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-241, -1400)));
		terreno.add(createPared( 48, 60, new Vector2(-1056, -1180)));
		terreno.add(createPared( 48, 60, new Vector2(-856, -1180)));
		terreno.add(createPared( 48, 60, new Vector2(-656, -1180)));
		terreno.add(createPared( 48, 60, new Vector2(-471, -1180)));
		terreno.add(createPared( 48, 60, new Vector2(-241, -1180)));
		terreno.add(createPared( 48, 60, new Vector2(-1056, -982)));
		terreno.add(createPared( 48, 60, new Vector2(-856, -982)));
		terreno.add(createPared( 48, 60, new Vector2(-656, -982)));
		terreno.add(createPared( 48, 60, new Vector2(-471, -982)));
		terreno.add(createPared( 48, 60, new Vector2(-241, -982)));
		terreno.add(createPared( 48, 45, new Vector2(-1056, -800)));
		terreno.add(createPared( 48, 45, new Vector2(-856, -800)));
		terreno.add(createPared( 48, 45, new Vector2(-656, -800)));
		terreno.add(createPared( 48, 45, new Vector2(-471, -800)));
		terreno.add(createPared( 48, 45, new Vector2(-241, -800)));
		terreno.add(createPared( 48, 45, new Vector2(-1056, -600)));
		terreno.add(createPared( 48, 45, new Vector2(-856, -600)));
		terreno.add(createPared( 48, 45, new Vector2(-656, -600)));
		terreno.add(createPared( 48, 45, new Vector2(-471, -600)));
		terreno.add(createPared( 48, 45, new Vector2(-241, -600)));
		
		//DELIMITACION AFUERA
		
		//EDIFICIOS AFUERA
		terreno.add(createPared(1728, 624, new Vector2(6336, 312)));
		terreno.add(createPared(1583, 624, new Vector2(8376, 312)));
		terreno.add(createPared(2447, 1344, new Vector2(4438, 3168)));
		terreno.add(createPared(2061, 530, new Vector2(1893, 4062)));
		terreno.add(createPared(864, 526, new Vector2(1295, 3337)));
		terreno.add(createPared(858, 527, new Vector2(2495, 3337)));
		terreno.add(createPared(858, 479, new Vector2(2494, 2639)));
		terreno.add(createPared(861, 479, new Vector2(1296, 2639)));
		terreno.add(createPared(861, 577, new Vector2(1296, 1918)));
		terreno.add(createPared(861, 577, new Vector2(2494, 1918)));
		
		
		//ARBOLES Y DEMAS
		
		terreno.add(createPared( 80, 80, new Vector2(-540, -910)));
		
		interactables.add(new Interactable(-540, -910, 180, 180, mundo, "Agujero"));
		interactables.add(new Interactable(-1360, -590, 180, 90, mundo, "Salir"));
		interactables.add(new Interactable(-1490, -1060, 60, 300, mundo, "Pizarron"));		
	}
	public void dibujardanos(float delta) {
		if(!danos.isEmpty()) {	
			 Iterator<TextoFlotante> iterador = danos.iterator();
		        while (iterador.hasNext()) {
		            TextoFlotante texto = iterador.next();
		            if(!texto.isTerminado()) {
		            	texto.update(delta);
		            	texto.dibujar();
		            }else {
		            	iterador.remove();
		            }
		        }
		}
	}
	public void spawnEnemigos() {
		
		if(!aparicionKarla) {
			if(tresSec>10) {
				tresSec=0;
				
				factory.SpawnWave( player.getPosition().x+32, player.getPosition().y+64, mundo, tiempoIRL);	
				
			}
		}else {
			if(tresSec>6) {
				tresSec=0;
				
				factory.SpawnWave( player.getPosition().x+32, player.getPosition().y+64, mundo, tiempoIRL);	
				
			}
		}
		

	}
	public void detectarEnemigos() {
		
		Array<Body> bodies = new Array<Body>();
	mundo.getBodies(bodies);

		for (Body body : bodies) {
			if(body!=null) {
				if (body.getUserData() instanceof AlumnoEnemy ) {
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					
				}else if (body.getUserData() instanceof AlumnoEnemy2 ) {
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof PerroEnemy ) {
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof AguilaEnemy ) {
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof ProfesorEnemy ) {
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof Recursador ) {
					Recursador enemigo = Recursador.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof MKarla ) {
					MKarla enemigo = MKarla.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof MYobani ) {
					MYobani enemigo = MYobani.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof MAlan ) {
					MAlan enemigo = MAlan.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof MTaker ) {
					MTaker enemigo = MTaker.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}else if (body.getUserData() instanceof MFigueroa ) {
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(body);
					float distanciaEnemigo = centroPJ.dst(enemigo.getCentro());
					Vector2 enemigoPos = new Vector2(enemigo.getCentro());
					boolean enemigoVivo = enemigo.isVivo();
					//Hacer logica para armas disponibles del jugador
					if(haveLapiz) {
						disparoLapiz(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
					if(haveMarcador) {
						disparoMarcador(distanciaEnemigo, enemigoVivo,enemigoPos);
					}
				}
				
				
			}
		}

	}
	public void disparoLapiz(float distanciaEnemigo, boolean enemigoVivo, Vector2 enemigoPos) {
		if(distanciaEnemigo<350 && cooldownLapiz>cdLapiz && enemigoVivo) {
			cooldownLapiz=0;
			
			//Si existe el lapiz
			Lapiz lapicito;

			Vector2 recta = new Vector2(enemigoPos.x,enemigoPos.y);
			Vector2 relativePos = recta.sub(centroPJ);

			// Calcular el ángulo de dirección relativo en radianes
			float angle = relativePos.angleRad();

			// Convertir el ángulo en grados y ajustar para que esté en el rango [0, 360)

			float degrees = MathUtils.radiansToDegrees * angle;
			degrees = (degrees + 360) % 360;

			// Determinar la dirección cardinal
			String direction = "";
			if (degrees >= 337.5 || degrees < 22.5) {
				lapicito = new Lapiz(posPJ.x+anchoPJ+5, centroPJ.y,multiplicadorLapiz,mundo,enemigoPos,0);
			    direction = "Este";
			    lapicito.rotarSprite(0);
			    
			} else if (degrees >= 22.5 && degrees < 67.5) {
				lapicito = new Lapiz(posPJ.x+anchoPJ+5, posPJ.y+altoPJ+5,multiplicadorLapiz,mundo,enemigoPos,0);
			    direction = "Noreste";
			    lapicito.rotarSprite(45);
			} else if (degrees >= 67.5 && degrees < 112.5) {
				lapicito = new Lapiz(centroPJ.x, posPJ.y+altoPJ+5,multiplicadorLapiz,mundo,enemigoPos,0);
				lapicito.rotarSprite(90);
				direction = "Norte";
			} else if (degrees >= 112.5 && degrees < 157.5) {
				lapicito = new Lapiz(posPJ.x-5, posPJ.y+altoPJ+5,multiplicadorLapiz,mundo,enemigoPos,0);
				lapicito.rotarSprite(135);
			    direction = "Noroeste";
			} else if (degrees >= 157.5 && degrees < 202.5) {
				lapicito = new Lapiz(posPJ.x-5, centroPJ.y,multiplicadorLapiz,mundo,enemigoPos,0);
			    direction = "Oeste";
			    lapicito.rotarSprite(180);
			} else if (degrees >= 202.5 && degrees < 247.5) {
				lapicito = new Lapiz(posPJ.x-5, posPJ.y-5,multiplicadorLapiz,mundo,enemigoPos,0);
				lapicito.rotarSprite(225);
			    direction = "Suroeste";
			} else if (degrees >= 247.5 && degrees < 292.5) {
				lapicito = new Lapiz(centroPJ.x, posPJ.y-5,multiplicadorLapiz,mundo,enemigoPos,0);
				lapicito.rotarSprite(270);
			    direction = "Sur";
			} else if (degrees >= 292.5 && degrees < 337.5) {
				lapicito = new Lapiz(posPJ.x+anchoPJ+5, posPJ.y-5,multiplicadorLapiz,mundo,enemigoPos,0);
				lapicito.rotarSprite(315);
			    direction = "Sureste";
			}
			
		}
	}
	public void disparoMarcador(float distanciaEnemigo, boolean enemigoVivo, Vector2 enemigoPos) {
		if(distanciaEnemigo<350 && cooldownMarcador>cdMarcador && enemigoVivo) {
			cooldownMarcador=0;
			
			//Si existe el lapiz
			Marcador marcadorcito;

			Vector2 recta = new Vector2(enemigoPos.x,enemigoPos.y);
			Vector2 relativePos = recta.sub(centroPJ);

			// Calcular el ángulo de dirección relativo en radianes
			float angle = relativePos.angleRad();

			// Convertir el ángulo en grados y ajustar para que esté en el rango [0, 360)

			float degrees = MathUtils.radiansToDegrees * angle;
			degrees = (degrees + 360) % 360;

			// Determinar la dirección cardinal
			String direction = "";
			if (degrees >= 337.5 || degrees < 22.5) {
				marcadorcito = new Marcador(posPJ.x+anchoPJ+5, centroPJ.y,multiplicadorMarcador,mundo,enemigoPos,0);
			    direction = "Este";
			    marcadorcito.rotarSprite(0);
			    
			} else if (degrees >= 22.5 && degrees < 67.5) {
				marcadorcito = new Marcador(posPJ.x+anchoPJ+5, posPJ.y+altoPJ+5,multiplicadorMarcador,mundo,enemigoPos,0);
			    direction = "Noreste";
			    marcadorcito.rotarSprite(45);
			} else if (degrees >= 67.5 && degrees < 112.5) {
				marcadorcito = new Marcador(centroPJ.x, posPJ.y+altoPJ+5,multiplicadorMarcador,mundo,enemigoPos,0);
				marcadorcito.rotarSprite(90);
				direction = "Norte";
			} else if (degrees >= 112.5 && degrees < 157.5) {
				marcadorcito = new Marcador(posPJ.x-5, posPJ.y+altoPJ+5,multiplicadorMarcador,mundo,enemigoPos,0);
				marcadorcito.rotarSprite(135);
			    direction = "Noroeste";
			} else if (degrees >= 157.5 && degrees < 202.5) {
				marcadorcito = new Marcador(posPJ.x-5, centroPJ.y,multiplicadorMarcador,mundo,enemigoPos,0);
			    direction = "Oeste";
			    marcadorcito.rotarSprite(180);
			} else if (degrees >= 202.5 && degrees < 247.5) {
				marcadorcito = new Marcador(posPJ.x-5, posPJ.y-5,multiplicadorMarcador,mundo,enemigoPos,0);
				marcadorcito.rotarSprite(225);
			    direction = "Suroeste";
			} else if (degrees >= 247.5 && degrees < 292.5) {
				marcadorcito = new Marcador(centroPJ.x, posPJ.y-5,multiplicadorMarcador,mundo,enemigoPos,0);
				marcadorcito.rotarSprite(270);
			    direction = "Sur";
			} else if (degrees >= 292.5 && degrees < 337.5) {
				marcadorcito = new Marcador(posPJ.x+anchoPJ+5, posPJ.y-5,multiplicadorMarcador,mundo,enemigoPos,0);
				marcadorcito.rotarSprite(315);
			    direction = "Sureste";
			}

			
		}
	}
	public void dibujarBullets(Vector2 pos) {
		Array<Body> bodies = new Array<Body>();
		mundo.getBodies(bodies);

			for (Body body : bodies) {
				if(body!=null) {
					if (body.getUserData() instanceof Lapiz ) {
						Lapiz lapicito = Lapiz.devolverLapizPorBody(body);
						if(lapicito.isVivo()) {
							lapicito.update();
							lapicito.dibujar();
						}
					}else if (body.getUserData() instanceof Marcador ) {
						Marcador lapicito = Marcador.devolverObjetoBody(body);
						if(lapicito.isVivo()) {
							lapicito.update();
							lapicito.dibujar();
						}
					}else if (body.getUserData() instanceof Desodorante ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(body);
							lapicito.update(pos.x, pos.y);
							lapicito.dibujar();
						
					}
				}
			}
	}
	public void dibujarEnemigos(float delta) {
		Array<Body> bodies = new Array<Body>();
		mundo.getBodies(bodies);

			for (Body body : bodies) {
				if(body!=null) {
					if (body.getUserData() instanceof AlumnoEnemy ) {
						AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition() ,.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
							
							
					} else if (body.getUserData() instanceof AlumnoEnemy2 ) {
						AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof ProfesorEnemy) {
						ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof PerroEnemy ) {
						PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof AguilaEnemy ) {
						AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof Recursador ) {
						Recursador enemigo = Recursador.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof MKarla ) {
						MKarla enemigo = MKarla.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof MYobani ) {
						MYobani enemigo = MYobani.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof MAlan ) {
						MAlan enemigo = MAlan.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof MTaker ) {
						MTaker enemigo = MTaker.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof MFigueroa ) {
						MFigueroa enemigo = MFigueroa.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							if(enemigo.update(delta, pause)) {
								TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
								danio.setTexto(""+30);
								danos.add(danio);
							}
							enemigo.dibujar();
						}
					}else if (body.getUserData() instanceof Alumno ) {
						Alumno enemigo = Alumno.devolverObjetoBody(body);
						enemigo.update(delta);
						}else if (body.getUserData() instanceof Profesor) {
						Profesor enemigo = Profesor.devolverObjetoBody(body);
						enemigo.update(delta);
					}
				}
			}
	}
	public void acercarEnemigos() {
		Array<Body> bodies = new Array<Body>();
		mundo.getBodies(bodies);

			for (Body body : bodies) {
				if(body!=null) {
					if (body.getUserData() instanceof AlumnoEnemy ) {
						AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					} else if (body.getUserData() instanceof AlumnoEnemy2 ) {
						AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof ProfesorEnemy) {
						ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof PerroEnemy ) {
						PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof AguilaEnemy ) {
						AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof Recursador ) {
						Recursador enemigo = Recursador.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof MKarla ) {
						MKarla enemigo = MKarla.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof MYobani ) {
						MYobani enemigo = MYobani.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof MAlan ) {
						MAlan enemigo = MAlan.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof MTaker ) {
						MTaker enemigo = MTaker.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}else if (body.getUserData() instanceof MFigueroa ) {
						MFigueroa enemigo = MFigueroa.devolverObjetoBody(body);
						if(enemigo.isVivo()) {
							enemigo.acercar(posPJ.x, posPJ.y);
						}
					}
				}
			}
	}
	public void checkEntrada() {
		int velx=0;
		int vely=0;
		if(entrada.isAbajo()) {
				isWalking=true;
				vely-=1;
		}
		if(entrada.isArriba()) {
			isWalking=true;
			vely+=1;
		}
		if(entrada.isDer()) {
			isWalking=true;
			velx+=1;
		}else {
			
		}
		if(entrada.isIzq()) {
			isWalking=true;
			velx-=1;
		}
		if(entrada.isEscape()) {
			pause=true;
		}
		float vel;
		if(localPJ=="alumno") {
			
			AlumnoPlayer.setPosition(player.getPosition().x-ppm/2,player.getPosition().y-ppm);
			centroPJ=AlumnoPlayer.getCentro();
			posPJ = AlumnoPlayer.getPosition();
			vel= AlumnoPlayer.getFactvelocidad();
		}else {
			MaestroPlayer.setPosition(player.getPosition().x-ppm/2,player.getPosition().y-ppm);
			centroPJ=MaestroPlayer.getCentro();
			posPJ = MaestroPlayer.getPosition();
			vel= MaestroPlayer.getFactvelocidad();

		}
		player.setLinearVelocity(velx*ppm*playerVel*vel, vely*playerVel*ppm*vel);
		if(currentInteractable!=null && entrada.isEnter()) {
			String accion = currentInteractable.getAccion();
			
			switch (accion) {
			case "Agujero":
				textoAgujero();
				break;
			case "Salir":
				salirSalon();
				break;
			case "Pizarron":
				eventoPizarron();
				break;
			default:
				break;
			}
		}

	}
	private Body createPared(int ancho, int alto, Vector2 pos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos);
		bodyDef.type = BodyType.StaticBody;
		Body player2 = mundo.createBody(bodyDef);
		
		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho/2, alto/2);
		
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		fixturedef.friction = 1.0f; // Alta fricción
		fixturedef.restitution = 0.0f;
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		player2.setUserData("Terreno");
		return player2;
	}
	private Body createBox(int ancho, int alto, Vector2 pos, BodyType tipo) {
		BodyDef bodyDef = new BodyDef();
		if(tipo == BodyType.KinematicBody) {
			
			bodyDef.type = BodyType.KinematicBody;
			bodyDef.position.set(pos);
		}else if (tipo == BodyType.DynamicBody){
			
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(pos);
		}else {
			
			bodyDef.type = BodyType.StaticBody;
			bodyDef.position.set(pos);
		}

		
		Body player2 = mundo.createBody(bodyDef);
		
		PolygonShape main = new PolygonShape();
		main.setAsBox(ancho/2, alto/2);
		
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = main;
		
		
		Fixture fixture = player2.createFixture(fixturedef);
		main.dispose();
		return player2;
	}

	private void crearContactListener() {
		mundo.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				//System.out.println("Colision entre: "+ fixtureA.getBody().getUserData() + " y "+ fixtureB.getBody().getUserData());
				//Detectar colision entre 
				if(fixtureA.getBody().getUserData() instanceof Alumno || fixtureB.getBody().getUserData() instanceof Alumno || fixtureA.getBody().getUserData() instanceof Profesor || fixtureB.getBody().getUserData() instanceof Profesor) {
					boolean contactoArma;
					boolean contactoInteract;
					boolean contactoPared;
					boolean contactoItem=false;
					if(fixtureA.getBody().getUserData() instanceof Lapiz || fixtureB.getBody().getUserData() instanceof Lapiz) {
						contactoArma=true;
					}else if(fixtureA.getBody().getUserData() instanceof Marcador || fixtureB.getBody().getUserData() instanceof Marcador) {
						contactoArma=true;
					}else {
						contactoArma=false;
					}
					if(fixtureA.getBody().getUserData() instanceof Interactable || fixtureB.getBody().getUserData() instanceof Interactable) {
						contactoInteract=true;
						aparecerEnter(new Vector2(player.getPosition().x+30, player.getPosition().y+50));
						if (fixtureA.getBody().getUserData() instanceof Interactable) {
							currentInteractable = Interactable.devolverObjetoBody(fixtureA.getBody());
						}else {
							currentInteractable = Interactable.devolverObjetoBody(fixtureB.getBody());
						}
					}else {
						contactoInteract=false;
					}
					if(fixtureA.getBody().getUserData()=="Terreno"|| fixtureB.getBody().getUserData()=="Terreno") {
						contactoPared=true;
						}else {
							contactoPared=false;
						}
					if(fixtureA.getBody().getUserData() instanceof Cigarro) {
						Cigarro cigarrito = Cigarro.devolverObjetoBody(fixtureA.getBody());
						cigarrito.setPickeado(true);
						cigarrito.accion(fixtureB.getBody());
						contactoItem=true;
					}else if (fixtureB.getBody().getUserData() instanceof Cigarro) {
						Cigarro cigarrito = Cigarro.devolverObjetoBody(fixtureB.getBody());
						cigarrito.setPickeado(true);
						cigarrito.accion(fixtureA.getBody());
						contactoItem=true;
					}
					if(fixtureA.getBody().getUserData() instanceof Cafe) {
						Cafe cigarrito = Cafe.devolverObjetoBody(fixtureA.getBody());
						cigarrito.setPickeado(true);
						cigarrito.accion(fixtureB.getBody());
						contactoItem=true;
					}else if (fixtureB.getBody().getUserData() instanceof Cafe) {
						Cafe cigarrito = Cafe.devolverObjetoBody(fixtureB.getBody());
						cigarrito.setPickeado(true);
						cigarrito.accion(fixtureA.getBody());
						contactoItem=true;
					}if(fixtureA.getBody().getUserData() instanceof LEDexp) {
						LEDexp cigarrito = LEDexp.devolverObjetoBody(fixtureA.getBody());
						cigarrito.setPickeado(true);
						//cigarrito.accion(fixtureB.getBody());
						contactoItem=true;
					}else if (fixtureB.getBody().getUserData() instanceof LEDexp) {
						LEDexp cigarrito = LEDexp.devolverObjetoBody(fixtureB.getBody());
						cigarrito.setPickeado(true);
						//cigarrito.accion(fixtureA.getBody());
						contactoItem=true;
					}else if(contactTimer>0.5 && !contactoArma && !contactoInteract && !contactoPared && !contactoItem) {
						//System.out.println("Me pegaron");
						Render.hitEffect.play(Config.efectos/100);
						contactTimer=0;
						if(localPJ=="alumno") {
							AlumnoPlayer.setVida(AlumnoPlayer.getVida()-8);
							if(AlumnoPlayer.getVida()<=0) {
								isAlive=false;
							}
						}else {
							MaestroPlayer.setVida(MaestroPlayer.getVida()-8);
							if(MaestroPlayer.getVida()<=0) {
								isAlive=false;
							}
						}
						
					}
				}
				/*
				 * Lista de enemigos: 
				 * AlumnoEnemy
				 * AlumnoEnemy2
				 * PerroEnemy
				 * AguilaEnemy
				 * ProfesorEnemy
				 * Recursador
				 * Lista de bosses:
				 * MKarla
				 * MYobani
				 * MTaker
				 * MAlan
				 * */
				//Impacto de Lapiz con enemigos
				if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof AlumnoEnemy)  ) {
					Render.hitEffect.play(Config.efectos/100);
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof AlumnoEnemy)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureA.getBody());
					Render.hitEffect.play(Config.efectos/100);
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof AlumnoEnemy2)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof AlumnoEnemy2)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof ProfesorEnemy)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof ProfesorEnemy)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof PerroEnemy)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof PerroEnemy)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof AguilaEnemy)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof AguilaEnemy)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof Recursador)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					Recursador enemigo = Recursador.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof Recursador)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					Recursador enemigo = Recursador.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof MKarla)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MKarla enemigo = MKarla.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof MKarla)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MKarla enemigo = MKarla.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}
				else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof MYobani)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MYobani enemigo = MYobani.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof MYobani)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MYobani enemigo = MYobani.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof MAlan)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MAlan enemigo = MAlan.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof MAlan)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MAlan enemigo = MAlan.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}
				else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof MTaker)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MTaker enemigo = MTaker.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof MTaker)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MTaker enemigo = MTaker.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}
				else if((fixtureA.getBody().getUserData() instanceof Lapiz && fixtureB.getBody().getUserData() instanceof MFigueroa)  ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Lapiz && fixtureA.getBody().getUserData() instanceof MFigueroa)) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}
				
				//Impacto de marcador con enemigos
				if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof AlumnoEnemy)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof AlumnoEnemy)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureA.getBody());
					
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof AlumnoEnemy2)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof AlumnoEnemy2)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof ProfesorEnemy)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof ProfesorEnemy)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof PerroEnemy)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof PerroEnemy)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof AguilaEnemy)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof AguilaEnemy)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof Recursador)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					Recursador enemigo = Recursador.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);	
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof Recursador)) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					Recursador enemigo = Recursador.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-lapicito.damage);
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)lapicito.damage);
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof MKarla)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MKarla enemigo = MKarla.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof MKarla)) {
					Marcador lapicito =Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MKarla enemigo = MKarla.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof MYobani)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MYobani enemigo = MYobani.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof MYobani)) {
					Marcador lapicito =Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MYobani enemigo = MYobani.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof MAlan)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MAlan enemigo = MAlan.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof MAlan)) {
					Marcador lapicito =Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MAlan enemigo = MAlan.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof MTaker)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MTaker enemigo = MTaker.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof MTaker)) {
					Marcador lapicito =Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MTaker enemigo = MTaker.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}else if((fixtureA.getBody().getUserData() instanceof Marcador && fixtureB.getBody().getUserData() instanceof MFigueroa)  ) {
					Marcador lapicito = Marcador.devolverObjetoBody(fixtureA.getBody());
					lapicito.setVivo(false);
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureB.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
					
				}else if((fixtureB.getBody().getUserData() instanceof Marcador && fixtureA.getBody().getUserData() instanceof MFigueroa)) {
					Marcador lapicito =Marcador.devolverObjetoBody(fixtureB.getBody());
					lapicito.setVivo(false);
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureA.getBody());
					enemigo.setVida(enemigo.getVida()-(lapicito.damage*enemigo.armor));
					Render.hitEffect.play(Config.efectos/100);
					TextoFlotante danio = new TextoFlotante(Recursos.FuenteMedieval, 32, Color.RED, enemigo.getPosition(),.5f);
					danio.setTexto(""+(int)(lapicito.damage*enemigo.armor));
					danos.add(danio);
				}
				
				
				//Impacto de desodorante con enemigos
				
					
					if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof AlumnoEnemy)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof AlumnoEnemy)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof AlumnoEnemy2)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof AlumnoEnemy2)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof ProfesorEnemy)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);	
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof ProfesorEnemy)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof PerroEnemy)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof PerroEnemy)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof AguilaEnemy)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof AguilaEnemy)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof Recursador)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						Recursador enemigo = Recursador.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof Recursador)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						Recursador enemigo = Recursador.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof MKarla)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						MKarla enemigo = MKarla.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof MKarla)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						MKarla enemigo = MKarla.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof MYobani)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						MYobani enemigo = MYobani.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof MYobani)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						MYobani enemigo = MYobani.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof MAlan)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						MAlan enemigo = MAlan.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof MAlan)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						MAlan enemigo = MAlan.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof MTaker)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						MTaker enemigo = MTaker.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof MTaker)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						MTaker enemigo = MTaker.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}else if((fixtureA.getBody().getUserData() instanceof Desodorante && fixtureB.getBody().getUserData() instanceof MFigueroa)  ) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureA.getBody());
						MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureB.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
						
					}else if((fixtureB.getBody().getUserData() instanceof Desodorante && fixtureA.getBody().getUserData() instanceof MFigueroa)) {
						Desodorante lapicito = Desodorante.devolverObjetoBody(fixtureB.getBody());
						MFigueroa enemigo = MFigueroa.devolverObjetoBody(fixtureA.getBody());
						enemigo.setPoisoned(true);
						cooldownDesodorante=0;
					}
				
				
				
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				if(fixtureA.getBody().getUserData() instanceof Alumno || fixtureB.getBody().getUserData() instanceof Alumno || fixtureA.getBody().getUserData() instanceof Profesor || fixtureB.getBody().getUserData() instanceof Profesor) {
					if(fixtureA.getBody().getUserData() instanceof Interactable || fixtureB.getBody().getUserData() instanceof Interactable) {
						enters.clear();
						currentInteractable=null;
						stage.clear();
						eventoTextoPapel=false;
						eventoPizarron=false;
					}
				}
				
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
	
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void checkVida() {

		Array<Body> bodies = new Array<Body>();
	mundo.getBodies(bodies);

		for (Body body : bodies) {
			if(body!=null) {
				if (body.getUserData() instanceof Lapiz ) {
					Lapiz lapicito = Lapiz.devolverLapizPorBody(body);
					if(lapicito.isVivo()==false) {
						lapicito.desaparecer();
					}
				}else if (body.getUserData() instanceof Marcador ) {
					Marcador lapicito = Marcador.devolverObjetoBody(body);
					if(lapicito.isVivo()==false) {
						lapicito.desaparecer();
					}
				}else if (body.getUserData() instanceof AlumnoEnemy ) {
					AlumnoEnemy enemigo = AlumnoEnemy.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
						
					}
				} else if (body.getUserData() instanceof AlumnoEnemy2 ) {
					AlumnoEnemy2 enemigo = AlumnoEnemy2.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof ProfesorEnemy) {
					ProfesorEnemy enemigo = ProfesorEnemy.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof PerroEnemy ) {
					PerroEnemy enemigo = PerroEnemy.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof AguilaEnemy ) {
					AguilaEnemy enemigo = AguilaEnemy.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof Recursador ) {
					Recursador enemigo = Recursador.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof MKarla ) {
					MKarla enemigo = MKarla.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						Desodorante desodorante = new Desodorante(player.getPosition().x, player.getPosition().y, mundo);
						
						
							if(Config.logro7==false) {
								Config.logro7=true;
							}
						
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof MYobani ) {
					MYobani enemigo = MYobani.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						if(Config.logro6==false) {
							Config.logro6=true;
						}
						enemigo.desaparecer();
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof MAlan ) {
					MAlan enemigo = MAlan.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						double random = Math.random();
						if(Config.logro8==false) {
							Config.logro8=true;
						}
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof MTaker ) {
					MTaker enemigo = MTaker.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						if(Config.logro5==false) {
							Config.logro5=true;
						}
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof MFigueroa ) {
					MFigueroa enemigo = MFigueroa.devolverObjetoBody(body);
					if(!enemigo.isVivo()) {
						enemigo.desaparecer();
						
						double random = Math.random();
						if(random<.75) {
							
								LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop1);
						}else {
							LEDexp led = new LEDexp(enemigo.getPosition().x, enemigo.getPosition().y, enemigo.mundo, enemigo.drop2);
						}
					}
				}else if (body.getUserData() instanceof Cigarro ) {
					Cigarro cigarrito = Cigarro.devolverObjetoBody(body);
					if (cigarrito.getPickeado()) {
						cigarrito.desaparecer();
					}
				}else if (body.getUserData() instanceof Cafe ) {
					Cafe cigarrito = Cafe.devolverObjetoBody(body);
					if (cigarrito.getPickeado()) {
						cigarrito.desaparecer();
					}
			
				}else if (body.getUserData() instanceof LEDexp ) {
					LEDexp cigarrito = LEDexp.devolverObjetoBody(body);
					if (cigarrito.getPickeado()) {
						currentXP+= cigarrito.getExp();
						cigarrito.desaparecer();
					}
			
				}
				
			}
		
		}
		
		
	}
	
	public void pauseStatus() {
		
		if(entrada.isEscape()) {
			if(puntuacionTimer>1) {
				System.out.println(puntuacionTimer);
				System.out.println(pause);
				pause=!pause;
			}
		}
	}
	public void generarConsumibles() {
		//Trabajar en logica para invocarlos aleatoriamente y que suban las stats
		if(consumibleTimer>1.5) {
			consumibleTimer=0;
			Random random = new Random();
	        double angle = random.nextDouble() * 2 * Math.PI;
	        int item = random.nextInt(2);
	        // Calcular las coordenadas cartesianas en la orilla del círculo
	        float newx = posPJ.x + 400 * (float) Math.cos(angle);
	        float newy = posPJ.y + 400 * (float) Math.sin(angle);
	        if(item==0) {
	        	Cafe cafecito = new Cafe(newx,newy, mundo);
	        }else {
	        	Cigarro cigarrito = new Cigarro(newx,newy,mundo);
	        }
			
		}
	}
	public void dibujarConsumibles() {
		Array<Body> bodies = new Array<Body>();
		mundo.getBodies(bodies);

			for (Body body : bodies) {
				if(body!=null) {
					if (body.getUserData() instanceof Cigarro ) {
						Cigarro consumible = Cigarro.devolverObjetoBody(body);
						consumible.dibujar();
					} else if (body.getUserData() instanceof Cafe ) {
						Cafe consumible = Cafe.devolverObjetoBody(body);
						consumible.dibujar();
					} 
					else if (body.getUserData() instanceof LEDexp ) {
						LEDexp consumible = LEDexp.devolverObjetoBody(body);
						consumible.dibujar();
					}
				}
			}
	}
	
	private void aparecerEnter(Vector2 pos) {
		Imagen enter = new Imagen(Recursos.PressEnter, 200, 100);
		enter.setPosition(pos.x, pos.y);
		enters.add(enter);
	}
	private void textoAgujero() {
		eventoTextoPapel=true;
		TypingLabel label = new TypingLabel("Un olor a putrefaccion provoco este agujero.", skin);

		label.setPosition(140, 110);

		stage.addActor(label);
		
	}
	private void salirSalon() {
		player.setTransform(new Vector2(5590,691), 0);
		Started=true;
	}
	private void eventoPizarron() {
		eventoPizarron=true;
	}
	public void fadePizarron(float delta) {
		if(eventoPizarron) {
			alphaPizarron+=delta;
			if(alphaPizarron<1) {
				
				pizarron.setAlpha(alphaPizarron);
			}else {
				pizarron.setAlpha(1);
				pizarronFaded = true;
			}
		}else {
			pizarron.setAlpha(0);
		}
		
	}
	public void fadeHoja(float delta) {
		if(eventoTextoPapel) {
			alphaHoja+=delta;
			if(alphaHoja<1) {
				
				hojaTexto.setAlpha(alphaHoja);
			}else {
				hojaTexto.setAlpha(1);
				hojaTextoFaded = true;
			}
		}else {
			hojaTexto.setAlpha(0);
		}
		
	}
	 public void crearMenuPausa() {

			rectArriba = new Imagen(Recursos.RectanguloAzul, Config.ancho, 100);
			
			rectArriba.setAlpha(0.9f);
			
			
			
			rectMedio = new Imagen(Recursos.RectanguloAzul, Config.ancho-300, Config.alto-50);
			
			rectMedio.setAlpha(0.95f);
			
			guardado = new Imagen("Formas/configGuardada.PNG", rectMedio.getAncho()+100, 80);

			guardado.setAlpha(0f);
			musica = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			musica.setTexto("Volumen de musica");
			
			volumen = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			volumen.setTexto("Volumen general");
			
			resoluciones = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			resoluciones.setTexto("Resolucion");
			
			particulas = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			particulas.setTexto("Efectos de particulas");
			
			numeros = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			numeros.setTexto("Numeros de daño");
			
			politicas= new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			politicas.setTexto("Politicas de privacidad");
			
			preguntas = new Texto(Recursos.FuenteMedieval, 24,Color.WHITE, true);
			preguntas.setTexto("Preguntas frecuentes");
			
			guardar = new Texto(Recursos.FuenteMedieval, 36,Color.WHITE, true);
			guardar.setTexto("Guardar");
			
			

			checkBoxes = new Imagen[2];
			checkBoxes[0] = new Imagen(Recursos.MarcoRojoPaloma,30, 30);
			
			
			checkBoxes[1] = new Imagen(Recursos.MarcoRojoPaloma, 30, 30);
			
			
			abrir1 = new Texto(Recursos.FuenteMedieval, 30, Color.WHITE, true);
			//abrir1.setTexto("Salir al menu");
			
			reanudar = new Texto(Recursos.FuenteMedieval, 40, Color.WHITE, true);
			reanudar.setTexto("Reanudar");
		
			salir = new Texto(Recursos.FuenteMedieval, 40, Color.WHITE, true);
			salir.setTexto("Salir al menu");
			

			
			sr= new ShapeRenderer();
			
			opcion = new Texto[3];
			opcion[0]=salir;
			opcion[1]=guardar;
			opcion[2]=reanudar;
			marcoTriangulo = new Imagen(Recursos.MarcoTriangulo, 136, resoluciones.getAlto()+10);
			
			musicaSlide = new Slider(0, 100, 2, false, skin );
			volumenSlide = new Slider(0, 100, 2, false, skin );
			musicaSlide.setValue(Config.musica);
			volumenSlide.setValue(Config.efectos);
			musicaSlide.setWidth(300);
			volumenSlide.setWidth(300);
			
			volumenSlide.addListener(new ChangeListener() {
			    @Override
			    public void changed(ChangeEvent event, Actor actor) {
			        // Este método se llama cada vez que cambia el valor del slider
			        float volume = volumenSlide.getValue(); // Obtener el valor del slider
			        // Ajustar el volumen de la música a través del AudioManager u otro gestor de audio
			        Config.efectos=volume;
			        Render.clickEffect.setVolume(0, volume/100);
			        Render.hitEffect.setVolume(0, volume/100);
			    }
			});
			
			musicaSlide.addListener(new ChangeListener() {
			    @Override
			    public void changed(ChangeEvent event, Actor actor) {
			        // Este método se llama cada vez que cambia el valor del slider
			        float volume = musicaSlide.getValue(); // Obtener el valor del slider
			        // Ajustar el volumen de la música a través del AudioManager u otro gestor de audio
			        //audioManager.setMusicVolume(volume); // Suponiendo que tienes un gestor de audio llamado audioManager
			        Config.musica=volume;
			        Render.menuMusic.setVolume(volume/100);
			        Render.gameMusic.setVolume(volume/100);
			    }
			});
			
			
			stage2.addActor(musicaSlide);
			stage2.addActor(volumenSlide);
			
		
			
	    }
	 public void opcChange(float delta) {
		 int cont=0;
		 float posMouseX = camera.position.x-Config.ancho/2+entrada.getMouseX(), posMouseY =camera.position.y-Config.alto/2+entrada.getMouseY();
			for (int i = 0; i< opcion.length; i++) {
				if (posMouseX>=opcion[i].getEsqAbajoIzq().x && posMouseX <= opcion[i].getEsqArribaDer().x) {
					if(posMouseY>=opcion[i].getEsqAbajoIzq().y && posMouseY <= opcion[i].getEsqArribaDer().y) {
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
				case 2:
					pause=false;
					break;
				default:
					break;
				}
			}
		}
		public void checkChange() {
			float posMouseX = camera.position.x-Config.ancho/2+entrada.getMouseX(), posMouseY =camera.position.y-Config.alto/2+entrada.getMouseY();
			if(!entrada.isMouse1()) {
				contcheck=0;
			}
			opccheck=false;
			
			for (int i = 0; i< checkBoxes.length; i++) {
				if (posMouseX>=checkBoxes[i].getEsqAbajoIzq().x && posMouseX <= checkBoxes[i].getEsqArribaDer().x) {
					if(posMouseY>=checkBoxes[i].getEsqAbajoIzq().y && posMouseY <= checkBoxes[i].getEsqArribaDer().y) {
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
			alphaGuardado-=0.01;
			if(alphaGuardado<0) {
				alphaGuardado=0;
				dibujarGuardado=false;
			}else {
				
				
			}
			guardado.setAlpha(alphaGuardado);
		}
}
