package de.macbury.startup.desktop;

import de.macbury.startup.CoreGame;
import de.macbury.startup.screens.TestBehaviorScreen;
import de.macbury.startup.screens.TestRenderingScreen;

/**
 * Created by macbury on 22.09.16.
 */
public class DesktopGame extends CoreGame {
  @Override
  public void create() {
    super.create();
    screens.set(new TestRenderingScreen());
  }
}
