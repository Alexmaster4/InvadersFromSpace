package net.fiveotwo.invaders.core;
/* Codigo de la presentacion de desarrollo multiplataforma con PlayN, COECYS USAC agosto 2012
 * @ Ricardo Illescas, 502Studios
 */
import static playn.core.PlayN.*;
import net.fiveotwo.invaders.core.Utilities.Accel;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Platform.Type;
import playn.core.ResourceCallback;


public class InvadersFromSpace implements Game {
	float UpdateRate;
	public static double scale;
	public Accel Acelerometer;
	Mundo world=new Mundo(Acelerometer);
	public static Image inv2,inv4,ship,bullet;
	public InvadersFromSpace(Accel accel) {
		this.Acelerometer=accel;
	}
	  
  @Override
  public void init() {  	
	  int WIDTH = 800;
	  int HEIGHT = 600;
    platformType();
	// Si es un dispositivo movil, ocuparemos toda la pantalla, de lo contrario tendremos un tama~no fijo.
	  if (platformType() == Type.ANDROID||platformType() == Type.IOS) {
			platformType();	
			if ((platformType() == Type.ANDROID)||(platformType() == Type.IOS)){
				graphics().rootLayer().setScale(graphics().screenWidth()/WIDTH, graphics().screenHeight()/HEIGHT);
			}
		}else{
			graphics().setSize(800, 600);
		}		
		
	//Cargamos las imagenes usando un callback para evitar problemas Async con js
	  assets().getImage("images/bg.png").addCallback(new ResourceCallback<Image>(){
			@Override
			public void done(Image resource) {
				// TODO Auto-generated method stub
			    Image bgImage = resource;
			    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
			    graphics().rootLayer().add(bgLayer);
			    
			    assets().getImage("images/invader4.png").addCallback(new ResourceCallback<Image>(){
					@Override
					public void done(Image resource) {
						inv4=resource;
						
						assets().getImage("images/invader2.png").addCallback(new ResourceCallback<Image>(){
							@Override
							public void done(Image resource) {
								inv2=resource;
								
								assets().getImage("images/ship.png").addCallback(new ResourceCallback<Image>(){
									@Override
									public void done(Image resource) {
										ship=resource;
										
										assets().getImage("images/playerbullet.png").addCallback(new ResourceCallback<Image>(){
											@Override
											public void done(Image resource) {
												bullet=resource;
											    //iniciamos el mundo
											    world=new Mundo(Acelerometer);
											    world.Init();
											}
											@Override
											public void error(Throwable err) {}});
									}

									@Override
									public void error(Throwable err) {}});
							}
							@Override
							public void error(Throwable err) {}});
					}

					@Override
					public void error(Throwable err) {
						// TODO Auto-generated method stub
						
					}
			    	
			    });
			    
			    
			}

			@Override
			public void error(Throwable err) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });  
	  
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
