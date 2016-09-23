package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Position of entity in world
 */
public class PositionComponent extends Vector2 implements Component, Pool.Poolable {
  @Override
  public void reset() {
    setZero();
  }
}
