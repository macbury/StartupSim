package de.macbury.startup;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.kotcrab.vis.ui.VisUI;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.assets.EngineFileHandleResolver;
import de.macbury.startup.messages.MessagesManager;
import de.macbury.startup.screens.ScreenManager;

public abstract class CoreGame extends ApplicationAdapter {
  private static final String TAG = "CoreGame";
  /**
   * Manage in game screens
   */
  public final ScreenManager screens;
  /**
   *  Loads and stores assets like textures, bitmapfonts, tile maps, sounds, music and so on.
   */
  public final Assets assets;
  /**
   * Resolve files path
   */
  public final EngineFileHandleResolver resolver;
  /**
   * Manage loading and retreving behavior trees
   */
  public final BehaviorTreeLibrary behaviorTreeLibrary;

  public final MessagesManager messages;

  public CoreGame() {
    this.messages = new MessagesManager();
    this.resolver = new EngineFileHandleResolver();
    this.screens  = new ScreenManager(this);
    this.assets   = new Assets(resolver, this);
    this.behaviorTreeLibrary = new BehaviorTreeLibrary(resolver, BehaviorTreeParser.DEBUG_LOW);
    BehaviorTreeLibraryManager.getInstance().setLibrary(behaviorTreeLibrary);
  }

  @Override
	public void create () {
    Gdx.app.log(TAG, "Creating game...");
    resolver.setup();
	}

  @Override
  public void resize(int width, int height) {
    screens.resize(width, height);
  }

  @Override
  public void pause() {
    screens.pause();
  }

  @Override
  public void resume() {
    screens.resume();
  }

  @Override
	public void render () {
    screens.tick(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
    assets.dispose();
    screens.dispose();
    VisUI.dispose();
	}
}
