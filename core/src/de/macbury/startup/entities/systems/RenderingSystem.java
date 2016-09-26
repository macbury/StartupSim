package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.components.CharsetAnimationComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.components.SpriteComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.graphics.Direction;


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
    PositionComponent position = Components.Position.get(entity);
    if (Components.CharsetAnimation.has(entity)) {
      CharsetAnimationComponent charsetAnimation = Components.CharsetAnimation.get(entity);
      charsetAnimation.stateTime += deltaTime;
      spriteBatch.draw(charsetAnimation.getKeyFrame(Direction.Down), position.x, position.y, 1f, 1f);
    } else if (Components.Sprite.has(entity)) {
      SpriteComponent spriteComponent = Components.Sprite.get(entity);
      spriteBatch.draw(spriteComponent.region, position.x, position.y, 1f, 1f);
    }
  }


  @Override
  public void dispose() {
    camera = null;
    spriteBatch.dispose();
  }
}
