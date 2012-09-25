package net.fiveotwo.invaders.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import net.fiveotwo.invaders.core.InvadersFromSpace;

public class InvadersFromSpaceActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("net/fiveotwo/invaders/resources");
    AndroidAccelerometer Accel=new AndroidAccelerometer(this);
    PlayN.run(new InvadersFromSpace(Accel));
  }
}
