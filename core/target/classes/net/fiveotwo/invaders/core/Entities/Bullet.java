package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import net.fiveotwo.invaders.core.Mundo;
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.CanvasImage;
import playn.core.Image;

public class Bullet extends Entity {

	public Bullet(int x, int y, String who,Mundo worl){
		/*
		 * Dependiendo nuestro dispositivo puede que la imagen sea o mas grande o muy peque~na, por lo que haremos Scaling
		 * Lo ideal es tener sets de Sprites de diferentes resoluciones para cada pantalla, pero esto funcionara.
		 */
		this.Name=who;
		this.Texture=assets().getImage("images/"+who+".png");
		float sizeX=Texture.width()*worl.getScale();
		float sizeY=Texture.height()*worl.getScale();
		CanvasImage Image=graphics().createImage(sizeX, sizeY);
		Image.canvas().setFillColor(0xffffffff);
		Image.canvas().drawImage(Texture, 0, 0,sizeX, sizeY);
		Texture=Image;
		
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
