package de.macbury.startup;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;
import de.macbury.startup.screens.ScreenManager;

public class CoreGame extends ApplicationAdapter {
  private static final String TAG = "CoreGame";
  public final ScreenManager screens;

  public CoreGame() {
    this.screens = new ScreenManager(this);
  }

  @Override
	public void create () {
    
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
    screens.dispose();
    VisUI.dispose();
	}
}
