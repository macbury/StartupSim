package de.macbury.startup.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.macbury.startup.CoreGame;
import de.macbury.startup.entities.EntityManager;
import de.macbury.startup.entities.blueprint.EntityBlueprint;
import de.macbury.startup.entities.components.CharsetAnimationComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.systems.ProgrammerSystem;
import de.macbury.startup.entities.systems.RenderingSystem;
import de.macbury.startup.graphics.CharsetAnimation;
import de.macbury.startup.graphics.Direction;

/**
 * Created by macbury on 22.09.16.
 */
public class TestRenderingScreen extends AbstractScreen {
  private SpriteBatch spriteBatch;
  private OrthographicCamera camera;
  private EntityManager entities;
  private Viewport worldViewport;

  public TestRenderingScreen() {
    super();
  }

  @Override
  public void preload() {
    game.assets.load("entity:programmer.json", EntityBlueprint.class);
    game.assets.load("entity:computer.json", EntityBlueprint.class);
    game.assets.load("entity:coffee-cup.json", EntityBlueprint.class);
    game.assets.load("entity:sandwich.json", EntityBlueprint.class);
  }

  @Override
  public void create() {
    this.entities    = new EntityManager(game);
    this.camera      = new OrthographicCamera();
    //TODO refactor this into builder or something else...
    entities.addSystem(new ProgrammerSystem());
    entities.addSystem(new RenderingSystem(camera));

    this.spriteBatch = new SpriteBatch();
    worldViewport    = new FillViewport(24, 24, camera);

    entities.create("entity:programmer.json");
    entities.create("entity:computer.json");
    entities.create("entity:sandwich.json");
    entities.create("entity:coffee-cup.json");
  }

  @Override
  public void resize(int width, int height) {
    worldViewport.update(width,height);
  }

  @Override
  public void show() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    entities.update(delta);
  }

  @Override
  public void hide() {

  }

  @Override
  public boolean isDisposedAfterHide() {
    return true;
  }

  @Override
  public void dispose() {
    entities.dispose();
  }
}
