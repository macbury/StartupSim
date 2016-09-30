package de.macbury.startup.desktop;

import de.macbury.startup.CoreGame;
import de.macbury.startup.screens.test.TestAStarScreen;
import de.macbury.startup.screens.test.TestPathFindingScreen;
import de.macbury.startup.screens.test.TestRenderingScreen;

/**
 * Created by macbury on 22.09.16.
 */
public class DesktopGame extends CoreGame {
  @Override
  public void create() {
    super.create();
    screens.set(new TestPathFindingScreen());
  }
}
