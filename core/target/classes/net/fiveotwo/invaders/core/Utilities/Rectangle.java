package net.fiveotwo.invaders.core.Utilities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
public class Rectangle {
	float left,right,top,bottom;
	
	public Rectangle(float l, float t, float r,float b){
		this.left=l;
		this.right=r;
		this.top=t;
		this.bottom=b;
	}
	
	//Metodos para acceder a las propiedades del rectangulo
	public float Left(){
		return this.left;
	}
	public float Right(){
		return this.right;
	}
	public float Top(){
		return this.top;
	}
	public float Bottom(){
		return this.bottom;
	}
	//Regresa un TRUE o FALSE dependiendo si hay overlap entre rectangulos.
	public boolean Intersects(Rectangle r) {
		return !(Left() > r.Right() || Top() > r.Bottom() || Right() < r.Left() || Bottom() < r.Top());
	}
}