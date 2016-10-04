package de.macbury.startup.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
    Gdx.app.setLogLevel(Application.LOG_DEBUG);
    screens.set(new TestPathFindingScreen());
  }
}
