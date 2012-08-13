package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.assets;
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.Image;

public class Bullet extends Entity {

	public Bullet(int x, int y, String who){
		this.Name=who;
		this.Texture=assets().getImage("images/"+who+".png");
		setPosition(x-Texture.width()/2,y);
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
		return 		this.PosX;
	}

	@Override
	public float getPositionY() {
		// TODO Auto-generated method stub
		return this.PosY;
	}

	@Override
	public void Update(float delta) {
		if(Name.equalsIgnoreCase("playerbullet")){
			setPositionY(getPositionY()-10f);
		}
		if(Name.equalsIgnoreCase("enemybullet")){
			setPositionY(getPositionY()+10f);
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
	public void Collisions(){
		
	}
	@Override
	public Rectangle CollisionRectangle() {
		// TODO Auto-generated method stub
		return new Rectangle(getPositionX(),getPositionY(),getPositionX()+getTexture().width(),getPositionY()+getTexture().height());
	}

}
