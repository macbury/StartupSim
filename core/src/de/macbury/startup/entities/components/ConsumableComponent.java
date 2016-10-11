package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;

/**
 * Entity can be consumed by other entity
 */
public class ConsumableComponent implements Pool.Poolable, Component {
  @Override
  public void reset() {

  }

  public static class Blueprint extends EmptyComponentBlueprint {}
}
