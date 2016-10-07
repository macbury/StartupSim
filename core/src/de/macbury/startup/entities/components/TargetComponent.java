package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;

/**
 * This component contains information
 */
public class TargetComponent extends Vector2 implements Component, Pool.Poolable {
  @Override
  public void reset() {
    setZero();
  }

  public static class Blueprint extends EmptyComponentBlueprint<TargetComponent> {

  }
}
