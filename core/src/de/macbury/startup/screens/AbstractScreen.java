package de.macbury.startup.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.utils.Disposable;

/** <p>
 * Represents one of many application screens, such as a main menu, a settings menu, the game screen and so on.
 * </p>
 * <p>
 * Note that {@link #dispose()} is not called automatically.
 * </p>*/
public abstract class AbstractScreen implements Disposable {
  private boolean created;

  public AbstractScreen() {
    super();
  }


  /**
   * Called before {@link AbstractScreen#create()}. You can add assets to load here. If there are assets to load it shows loading screen
   */
  public abstract void preload();

  /**
   * Called after {@link AbstractScreen#preload()}. Create all objects that uses loaded assets here
   */
  public abstract void create();

  /**
   * Window of game did change size
   * @param width
   * @param height
   */
  public abstract void resize(int width, int height);

  /** Called when this screen becomes the current screen for a Game. */
  public abstract void show ();

  /** Called if window or game had been put into background **/
  public abstract void pause();

  /**
   * Called if window or game are now on top and user did resume here
   */
  public abstract void resume();

  /**
   * This is called every frame if game is not paused
   * @param delta
   */
  public abstract void render(float delta);


  /**
   * This method is called before next screen will show
   */
  public abstract void hide ();

  /**
   * If return true, after {@link AbstractScreen#hide()} it will call {@link AbstractScreen#dispose()}
   * @return
   */
  public abstract boolean isDisposedAfterHide();

  public boolean isCreated() {
    return created;
  }

  public void setCreated(boolean created) {
    this.created = created;
  }


}