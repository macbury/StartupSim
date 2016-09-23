package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.components.PositionComponent;

import java.util.Comparator;

/**
 * System renders all sprites into sprite batch. SpriteBatch is managed by this system
 */
public class RenderingSystem extends IteratingSystem implements Disposable {
  private final SpriteBatch spriteBatch;
  private Camera camera;

  public RenderingSystem(Camera camera) {
    super(Family.all(PositionComponent.class).get());
    this.spriteBatch = new SpriteBatch();
    this.camera      = camera;
  }

  @Override
  public void update(float deltaTime) {
    camera.update();
    spriteBatch.setProjectionMatrix(camera.combined);
    spriteBatch.begin(); {
      super.update(deltaTime);
    } spriteBatch.end();
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

  }


  @Override
  public void dispose() {
    camera = null;
    spriteBatch.dispose();
  }
}
