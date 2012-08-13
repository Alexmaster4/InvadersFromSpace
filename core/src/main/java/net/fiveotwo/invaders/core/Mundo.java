package net.fiveotwo.invaders.core;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
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
import playn.core.CanvasImage;
import playn.core.ImmediateLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.TypedEvent;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.Surface;

public class Mundo {
	public List<Enemy> enemigos=new ArrayList<Enemy>();
	public List<Bullet> balas=new ArrayList<Bullet>();
	CanvasImage ScoreImage,Messages;
	Player player;
	private ImmediateLayer layer;
	public boolean left,right;
	public float EnemyMov=0.5f;
	public boolean hitLeft,hitRight;
	int initialenemynumber;
	boolean pause=true;
	boolean gameover;
	int Score;
	
	public Mundo(){
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
	 * Agregamos listeners para teclado y puntero. Estos metodos se sobrecargar para poder leer estos dispoistivos de entrada siempre
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
				// TODO Auto-generated method stub
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
				if(event.x()<=graphics().width()/2){
					left=true;
				}else{
					right=true;
				}
			}
			@Override
			public void onPointerEnd(Event event) {
				left=right=false;
			}
			@Override
			public void onPointerDrag(Event event) {
				// TODO Auto-generated method stub
				if(pause){
					pause=false;
					if(gameover)
						Reset();
				}else{
					getPlayer().Shoot();
					left=right=false;
				}
				
			}
		});
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
		layer.setAlpha(1f);
		
		graphics().rootLayer().add(layer);
		player=new Player(graphics().width()/2,(int) (graphics().height()-50),this);
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
			en.setPositionY(en.getPositionY()+10);
		}
	}
	//reinicia el juego
	public void Reset(){
		enemigos=new ArrayList<Enemy>();
		balas=new ArrayList<Bullet>();
		EnemyFormation();
	}
	//Agrega y ordena a los enemigos de la lista
	public void EnemyFormation(){
		int px=60;
		int py=40;
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,4,this));
			px+=65;
			initialenemynumber+=1;
		}
		py+=50;
		px=60;
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,2,this));
			px+=65;
			initialenemynumber+=1;
		}
		py+=50;
		px=60;
		for(int x=0;x<11;x++){
			enemigos.add(new Enemy(px,py,2,this));
			px+=65;
			initialenemynumber+=1;
		}
	}
	
}
