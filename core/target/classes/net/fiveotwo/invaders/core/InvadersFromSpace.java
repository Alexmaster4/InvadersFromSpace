package net.fiveotwo.invaders.core;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

public class InvadersFromSpace implements Game {
	float UpdateRate;
	public static double scale;
	Mundo world=new Mundo();
  @Override
  public void init() {  	  
    // Si es un dispositivo movil, ocuparemos toda la pantalla, de lo contrario tendremos un tama~no fijo.
	  if (platformType() == platformType().ANDROID) {
			graphics().setSize(graphics().screenWidth(), graphics().screenHeight());
		}else{			
			graphics().setSize(800, 480);
		}
	//Agregamos una Capa de fondo a nuestro juego.
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    bgLayer.setSize(graphics().width(), graphics().height());
    graphics().rootLayer().add(bgLayer);
    //iniciamos el mundo
    world.Init();
  }

  @Override
  public void paint(float alpha) {
	  
  }

  @Override
  public void update(float delta) {
	  world.Update(delta);
  }

  @Override
  public int updateRate() {
    return 20;
  }
}
