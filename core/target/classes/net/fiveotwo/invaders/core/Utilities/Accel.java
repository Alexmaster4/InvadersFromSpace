package net.fiveotwo.invaders.core.Utilities;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, COECYS USAC agosto 2012
 * @ Bryan Alvarado, 502Studios
 */
public interface Accel {
	float getX();
	float getY();
	float getZ();
	void start();
	void pause();
	float[] getValues();
}
