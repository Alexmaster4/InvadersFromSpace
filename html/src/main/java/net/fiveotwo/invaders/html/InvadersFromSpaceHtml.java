package net.fiveotwo.invaders.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import net.fiveotwo.invaders.core.InvadersFromSpace;

public class InvadersFromSpaceHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("invadersfromspace/");
    PlayN.run(new InvadersFromSpace());
  }
}
