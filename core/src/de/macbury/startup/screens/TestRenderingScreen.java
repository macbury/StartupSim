package de.macbury.startup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.macbury.startup.entities.EntityManager;
import de.macbury.startup.entities.systems.RenderingSystem;
import de.macbury.startup.graphics.CharsetAnimation;
import de.macbury.startup.graphics.Direction;

/**
 * Created by macbury on 22.09.16.
 */
public class TestRenderingScreen extends AbstractScreen {
  private Texture programmerTexture;
  private SpriteBatch spriteBatch;
  private OrthographicCamera camera;
  private float stateTime;
  private EntityManager entities;
  private Viewport worldViewport;
  private CharsetAnimation charsetAnimation;

  @Override
  public void preload() {
    this.programmerTexture = new Texture("programmer.png");
    charsetAnimation       = new CharsetAnimation(programmerTexture);
  }

  @Override
  public void create() {
    this.entities    = new EntityManager();
    this.camera      = new OrthographicCamera();
    entities.addSystem(new RenderingSystem(camera));
    this.spriteBatch = new SpriteBatch();
    worldViewport    = new FillViewport(32, 32, camera);

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

    this.stateTime += delta;
    camera.update();
    spriteBatch.setProjectionMatrix(camera.combined);
    spriteBatch.begin(); {
      for (int x = -5; x < 5; x++) {
        for (int y = -5; y < 5; y++) {
          spriteBatch.draw(charsetAnimation.getKeyFrame(stateTime, Direction.values()[Math.abs(x % 4)]), -0.5f + x, -0.5f + y, 1, 1);
        }
      }

    } spriteBatch.end();
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
    programmerTexture.dispose();
    entities.dispose();
  }
}
