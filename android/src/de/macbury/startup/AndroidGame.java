package de.macbury.startup;

import de.macbury.startup.screens.test.TestRenderingScreen;

/**
 * Created by macbury on 26.09.16.
 */
public class AndroidGame extends CoreGame {
  @Override
  public void create() {
    super.create();
    screens.set(new TestRenderingScreen());
  }
}
