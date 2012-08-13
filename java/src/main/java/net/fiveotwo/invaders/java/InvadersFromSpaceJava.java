package net.fiveotwo.invaders.java;

import net.fiveotwo.invaders.core.InvadersFromSpace;
import playn.core.PlayN;
import playn.java.JavaPlatform;

public class InvadersFromSpaceJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("net/fiveotwo/invaders/resources");
    PlayN.run(new InvadersFromSpace());
  }
}
