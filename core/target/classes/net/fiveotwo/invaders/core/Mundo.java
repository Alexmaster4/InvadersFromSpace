package net.fiveotwo.invaders.core;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, COECYS USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import static playn.core.PlayN.*;
import java.util.ArrayList;
import java.util.List;
import net.fiveotwo.invaders.core.Entities.Bullet;
import net.fiveotwo.invaders.core.Entities.Enemy;
import net.fiveotwo.invaders.core.Entities.Player;
import net.fiveotwo.invaders.core.Utilities.Accel;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImmediateLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.TypedEvent;
import playn.core.Platform.Type;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.Surface;

public class Mundo {
	public List<Enemy> enemigos=new ArrayList<Enemy>();
	public List<Bullet> balas=new ArrayList<Bullet>();
	CanvasImage ScoreImage,Messages;
	Image lt,rt,fr;
	Player player;
	private ImmediateLayer layer;
	public boolean left,right;
	//podemos modificar EnemyMov para acelerar o disminuir el movimiento de nuestros enemigos
	public float EnemyMov;
	public boolean hitLeft,hitRight;
	int initialenemynumber;
	boolean pause=true;
	boolean gameover;
	int Score;
	public float scale;
	Accel Acelerometer;
	
	public Mundo(Accel ac){
		Acelerometer=ac;
	}
	
	/*
	 * Metodos para agregar al marcador y obtener el marcador actual.
	 */
	public int getScore(){
		return this.Score;
	}
	public void addScore(int v){
		this.Score+=v;
	}
	/*
	 * Agregamos listeners para teclado y puntero. Estos metodos se sobrecargar para poder leer estos dispositivos de entrada siempre
	 * dentro de sus metodos start, end podemos definir distintas logicas para el juego
	 * 
	 * Crearemos un nuevo Layer inmediato para dibujar en pantalla.
	 * 
	 * Creamos un "jugador" nuevo y agregamos enemigos.
	 */
	public void Init(){

		keyboard().setListener(new Keyboard.Listener() {
			@Override
			public void onKeyDown(playn.core.Keyboard.Event event) {
				
				// Dependiendo que tecla oprimimos moveremos al jugador.
				if (event.key() == Key.LEFT) {
					left=true;
				}
				if (event.key() == Key.RIGHT) {
					right=true;
				}
				if (event.key() == Key.X) {
					if(pause){
						pause=false;
						if(gameover)
							Reset();
					}else{
						getPlayer().Shoot();
					}
				}
				//Si presionamos "back" en el movil, cerramos la aplicacion
				if (event.key() == Key.BACK) {
					if(pause){
						//System.exit(0);
					}else{
						pause=true;
					}
			}
			}
			@Override
			public void onKeyTyped(TypedEvent event) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onKeyUp(playn.core.Keyboard.Event event) {
				// TODO Auto-generated method stub
				left=right=false;
			}
			
		});
		pointer().setListener(new Pointer.Listener() {
			@Override
			public void onPointerStart(Event event) {
				getPlayer().Shoot();
			}
			@Override
			public void onPointerEnd(Event event) {
			}
			@Override
			public void onPointerDrag(Event event) {
				// TODO Auto-generated method stub
				if(pause){
					pause=false;
					if(gameover)
						Reset();
				}else{

				}
			}
		});
		
		if (platformType() == Type.ANDROID) {
			Acelerometer.start();
		}
		
		/*Usaremos una capa imediata, como se hablo, esta cuenta con un mejor rendimiento al dibujar las escenas
		 * "a mano".
		 */
		layer = graphics().createImmediateLayer(graphics().screenWidth(),
				graphics().screenHeight(), new ImmediateLayer.Renderer() {
					@Override
					public void render(Surface surface) {
						
							Draw(surface);
					}
				});
		ScoreImage=graphics().createImage(200, 50);
		ScoreImage.canvas().setFillColor(0xffffffff);
		ScoreImage.canvas().drawText("Score: "+getScore(), 10f, 20f);
		Messages=graphics().createImage(200, 50);
		Messages.canvas().setFillColor(0xffffffff);
		Messages.canvas().drawText("GAME PAUSED, Shoot to begin", 10f, 20f
				);
		//agregamos nuestra capa a la capa raiz del juego.
		layer.setAlpha(1f);		
		scale=graphics().width()/800f;
		EnemyMov=0.7f*getScale();
		graphics().rootLayer().add(layer);
		//Creamos al jugador y agregamos enemigos.
		player=new Player(graphics().width()/2,(int) (graphics().height()-assets().getImage("images/ship.png").height()*this.scale),this);
		EnemyFormation();

	}
	
	/*
	 * Actualiza la logica de nuestro jugador
	 * Actualiza nuestras listas de objetos (Enemigos, Balas)
	 * Manejo de Colisiones
	 * Mostramos mensajes en pantalla
	 */
	public void Update(float delta){
		if(!pause){
		for(Enemy en:enemigos){
			if (!en.remove) {
				en.Update(delta);
				if(en.getPositionY()>=getPlayer().getPositionY()-getPlayer().getTexture().height()*2){
				 Messages.canvas().clear();
					Messages.canvas().setFillColor(0xffffffff);
					Messages.canvas().drawText("You Lose, Shoot to try again", 10f, 20f);
					pause=gameover=true;
				}
			} else {
				enemigos.remove(en);
				break;
			}
	}
		for(Bullet en:balas){
			if (!en.remove) {
				en.Update(delta);
				if(en.Name.equalsIgnoreCase("playerbullet")&&en.getPositionY()<0){
					getPlayer().CanShoot=true;
					en.remove=true;
				}
		} else {
				balas.remove(en);
				break;
			}
		}
		
		if (platformType() == Type.ANDROID) {
		getPlayer().setPositionX(getPlayer().getPositionX()+Acelerometer.getY());
		}
		getPlayer().Update(delta);
		Collisions();
		ScoreImage.canvas().clear();
		ScoreImage.canvas().setFillColor(0xffffffff);
		ScoreImage.canvas().drawText("Score: "+getScore(), 10f, 20f);
		if(enemigos.size()==0){
			Messages.canvas().clear();
			Messages.canvas().setFillColor(0xffffffff);
		Messages.canvas().drawText("You Win, Shoot to begin", 10f, 20f);
		pause=gameover=true;
	}
		}
	}
	/*
	 * Dibujamos todos nuestros objetos de las listas a pantalla
	 * Dibujamos al jugador
	 */
	public void Draw(Surface surf){
		surf.drawImage(ScoreImage, 0, 0);
		if(pause){
			surf.drawImage(Messages, graphics().width()/2-Messages.width()/2, graphics().height()/2);
		}
		for(Enemy en:enemigos){
			surf.drawImage(en.getTexture(), en.getPositionX(), en.getPositionY());
		}
		for(Bullet en:balas){
			surf.drawImage(en.getTexture(), en.getPositionX(), en.getPositionY());
		}

		surf.drawImage(getPlayer().getTexture(), getPlayer().getPositionX(), getPlayer().getPositionY());
	}
	
	/*
	 * Touch Controls for mobile devices
	 */

	
	
	/*
	 * Recorremos nuestra lista de balas y lista de enemigos en busca de intersecciones entre rectangulos, en caso de haberlas
	 * procedemos a eliminar los 2 objetos de las listas y habilitamos el disparo del jugador.
	 */
	public void Collisions(){
		for(Bullet bul:balas){
			if(bul.Name.equalsIgnoreCase("playerbullet")){
				for(Enemy en:enemigos){
					if(bul.CollisionRectangle().Intersects(en.CollisionRectangle())){
						addScore(Integer.parseInt(en.Name)*100);
						en.remove=true;
						bul.remove=true;
						getPlayer().CanShoot=true;
						break;
					}
				}

			}
		}
	}
	//Regresa el objeto player
	public Player getPlayer(){
		return this.player;
	}
	//Avisa a la formacion de enemigos que se llego a un borde de la pantalla
	public void HitTheWall(float vel){
		EnemyMov*=vel;
		for(Enemy en:enemigos){
			en.setPositionY(en.getPositionY()+en.getTexture().height()/2);
		}
	}
	//reinicia el juego
	public void Reset(){
		enemigos=new ArrayList<Enemy>();
		balas=new ArrayList<Bullet>();
		EnemyFormation();
		getPlayer().CanShoot=true;
	}
	//Agrega y ordena a los enemigos de la lista
	public void EnemyFormation(){
		int px=(int) (60*getScale());
		int py=(int) (40*getScale());
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,4,this));
			px+=65*getScale();
			initialenemynumber+=1;
		}
		py+=50*getScale();
		px=(int) (60*getScale());
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,2,this));
			px+=65*getScale();
			initialenemynumber+=1;
		}
		py+=50*getScale();
		px=(int) (60*getScale());
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,2,this));
			px+=65*getScale();
			initialenemynumber+=1;
		}
	}
	
	public float getScale(){
		return this.scale;
	}
	
}
