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
	Mundo world=new Mundo();
  @Override
  public void init() {  	  
    // create and add background image layer
			graphics().setSize(800, 600);
		
    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
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
