package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.graphics;
import net.fiveotwo.invaders.core.InvadersFromSpace;
import net.fiveotwo.invaders.core.Mundo;
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.CanvasImage;
import playn.core.Image;

public class Enemy extends Entity{
	Mundo World;
	public Enemy(int x, int y, int type,Mundo worl){
		/*
		 * Dependiendo nuestro dispositivo puede que la imagen sea o mas grande o muy peque~na, por lo que haremos Scaling
		 * Lo ideal es tener sets de Sprites de diferentes resoluciones para cada pantalla, pero esto funcionara.
		 */
		if(type==2)
			this.Texture=InvadersFromSpace.inv2;
		if(type==4)
			this.Texture=InvadersFromSpace.inv4;
		float sizeX=Texture.width();
		float sizeY=Texture.height();
		CanvasImage Image=graphics().createImage(sizeX, sizeY);
		Image.canvas().drawImage(Texture, 0, 0,sizeX, sizeY);
		Texture=Image;
		this.PosX=x-Texture.width()/2;
		this.PosY=y;	
		this.World=worl;
		Name=String.valueOf(type);
	}
	
	@Override
	public void setPosition(float x, float y) {
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
		return this.PosX;
	}

	@Override
	public float getPositionY() {
		return this.PosY;
	}

	@Override
	public void Update(float delta) {
		if(World.EnemyMov>0){
			setPositionX(getPositionX()+World.EnemyMov);
			if(getPositionX()+getTexture().width()>=graphics().width()){
				World.HitTheWall(-1);
			}
		}
		if(World.EnemyMov<0){
			setPositionX(getPositionX()+World.EnemyMov);
			if(getPositionX()<=0){
				World.HitTheWall(-1);
			}
		}
	}

	@Override
	public Image getTexture() {
		return this.Texture;
	}

	@Override
	public void Shoot() {
		
	}

	@Override
	public Rectangle CollisionRectangle() {
		return new Rectangle(getPositionX(),getPositionY(),getPositionX()+getTexture().width(),getPositionY()+getTexture().height());
	}

}
