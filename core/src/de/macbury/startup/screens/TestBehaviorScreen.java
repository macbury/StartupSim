package de.macbury.startup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.macbury.startup.Programmer;

/**
 * Created by macbury on 22.09.16.
 */
public class TestBehaviorScreen extends AbstractScreen {
  private BehaviorTree<Programmer> tree;
  private Stage stage;
  private Programmer john;

  @Override
  public void preload() {

  }

  @Override
  public void show() {

  }

  @Override
  public boolean isDisposedAfterHide() {
    return false;
  }

  @Override
  public void hide() {

  }

  @Override
  public void create() {
    this.stage = new Stage();
    Gdx.input.setInputProcessor(stage);

    this.john = new Programmer();

    BehaviorTreeLibraryManager libraryManager = BehaviorTreeLibraryManager.getInstance();
    //libraryManager.setLibrary(new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH));

    this.tree = libraryManager.createBehaviorTree("example.tree", john);
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void render(float delta) {
    tree.step();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
