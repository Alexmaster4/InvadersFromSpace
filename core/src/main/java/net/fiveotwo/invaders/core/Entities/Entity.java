package net.fiveotwo.invaders.core.Entities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import net.fiveotwo.invaders.core.Utilities.Rectangle;
import playn.core.Image;

public abstract class Entity {
	
	float PosX,PosY,Width,Height;
	Image Texture;
	public String Name;
	public boolean remove;
	Rectangle BoundingBox,Bounds;
	//Metodos para obtener y poner la posicion del objeto
	public abstract void setPosition(float x, float y);
	public abstract void setPositionX(float x);
	public abstract void setPositionY(float y);
	public abstract float getPositionX();
	public abstract float getPositionY();
	//Metodo al que llamaremos cada tick de animacion
	public abstract void Update(float delta);
	public abstract Image getTexture();
	public abstract void Shoot();
	public abstract Rectangle CollisionRectangle();
}
