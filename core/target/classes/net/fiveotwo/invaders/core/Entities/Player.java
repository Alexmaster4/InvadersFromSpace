package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, COECYS USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.graphics;
import net.fiveotwo.invaders.core.InvadersFromSpace;
import net.fiveotwo.invaders.core.Mundo;
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.CanvasImage;
import playn.core.Image;

public class Player extends Entity{
	Mundo world;
	public boolean CanShoot=true;
	public Player(int x, int y,Mundo world){
		/*
		 * Dependiendo nuestro dispositivo puede que la imagen sea o mas grande o muy peque~na, por lo que haremos Scaling
		 * Lo ideal es tener sets de Sprites de diferentes resoluciones para cada pantalla, pero esto funcionara.
		 */
			this.Texture=InvadersFromSpace.ship;
		float sizeX=Texture.width()*world.getScale();
		float sizeY=Texture.height()*world.getScale();
		CanvasImage Image=graphics().createImage(sizeX, sizeY);

		Image.canvas().drawImage(Texture, 0, 0,sizeX, sizeY);
		Texture=Image;
		this.PosX=x;
		this.PosY=y;
		this.world=world;
	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		this.PosX=x;
		this.PosY=y;
	}

	@Override
	public void setPositionX(float x) {
		this.PosX=x;
	}

	@Override
	public void setPositionY(float y) {
		this.PosY=y;
	}

	@Override
	public float getPositionX() {
		// TODO Auto-generated method stub
		return this.PosX;
	}

	@Override
	public float getPositionY() {
		// TODO Auto-generated method stub
		return this.PosY;
	}

	@Override
	public void Update(float delta) {
		if(world.left){
			setPositionX(getPositionX()-5f*world.getScale());
		}
		if(world.right){
			setPositionX(getPositionX()+5f*world.getScale());
		}
		//Evitemos salir del area visible
		if(getPositionX()<0){
			setPositionX(0);
		}
		if(getPositionX()+getTexture().width()>graphics().width()){
			setPositionX(graphics().width()-getTexture().width());
		}
	}

	@Override
	public Image getTexture() {
		// TODO Auto-generated method stub
		return this.Texture;
	}

	@Override
	public void Shoot() {
		// TODO Auto-generated method stub
		if(CanShoot){
		world.balas.add(new Bullet((int)(getPositionX()+getTexture().width()/2),(int)getPositionY(),"playerbullet",world));
		CanShoot=false;
		}
	}

	@Override
	public Rectangle CollisionRectangle() {
		return new Rectangle(getPositionX(),getPositionY(),getPositionX()+getTexture().width(),getPositionY()+getTexture().height());
	}

}
