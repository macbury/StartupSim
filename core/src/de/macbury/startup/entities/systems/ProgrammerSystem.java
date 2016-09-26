package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import de.macbury.startup.entities.components.ProgrammerComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Update behavior trees for programmers
 */
public class ProgrammerSystem extends IteratingSystem {
  public ProgrammerSystem() {
    super(Family.all(ProgrammerComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    ProgrammerComponent programmerComponent = Components.Programmer.get(entity);
    programmerComponent.tree.step();
  }
}
