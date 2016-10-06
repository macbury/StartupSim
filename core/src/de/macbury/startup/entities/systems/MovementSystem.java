package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.macbury.startup.entities.components.MovementComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Update information about movement from {@link de.macbury.startup.entities.components.MovementComponent}
 */
public class MovementSystem extends IteratingSystem {
  public MovementSystem() {
    super(Family.all(PositionComponent.class, MovementComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    MovementComponent movement = Components.Movement.get(entity);
    if (!movement.isFinished()) {
      PositionComponent position = Components.Position.get(entity);
      movement.step(deltaTime, position);
    }
  }
}
