package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import de.macbury.startup.entities.components.ThrottleComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Update throttle for each {@link de.macbury.startup.entities.components.ThrottleComponent}
 */
public class RateLimitSystem extends IteratingSystem {
  public RateLimitSystem() {
    super(Family.all(ThrottleComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    ThrottleComponent throttle = Components.Throttle.get(entity);

    for (String key : throttle.keys()) {
      float value = throttle.get(key, 0.0f);
      value -= deltaTime;
      if (value < 0)
        value = 0.0f;
      if (value >= 0)
        throttle.put(key, value);
    }
  }
}
