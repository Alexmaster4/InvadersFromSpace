package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import net.fiveotwo.invaders.core.Mundo;
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.Image;

public class Enemy extends Entity{
	Mundo World;
	public Enemy(int x, int y, int type,Mundo worl){
		this.Texture=assets().getImage("images/invader"+type+".png");
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
		// TODO Auto-generated method stub
		return this.Texture;
	}

	@Override
	public void Shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle CollisionRectangle() {
		return new Rectangle(getPositionX(),getPositionY(),getPositionX()+getTexture().width(),getPositionY()+getTexture().height());
	}

}
