package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.components.*;
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
      MovementComponent movement = Components.Movement.get(entity);
      if (movement.isFinished())  {
        //charsetAnimation.stateTime = 0;
      } else {
        charsetAnimation.stateTime += deltaTime;
      }

      spriteBatch.draw(charsetAnimation.getKeyFrame(Direction.from(movement.getDirection())), position.x, position.y, 1f, 1f);
    } else if (Components.Sprite.has(entity)) {
      SpriteComponent spriteComponent = Components.Sprite.get(entity);
      spriteBatch.draw(spriteComponent.region, position.x, position.y, 1f, 1f);
    }

    if (Components.NotificationCloud.has(entity)) {
      NotificationCloudComponent notificationCloud = Components.NotificationCloud.get(entity);
      if (notificationCloud.isActive()) {
        TextureRegion notificationRegion = notificationCloud.getNotificationRegion();

        spriteBatch.draw(notificationRegion, position.x, position.y+0.5f, 1f, 1f);
      }
    }
  }


  @Override
  public void dispose() {
    camera = null;
    spriteBatch.dispose();
  }
}
