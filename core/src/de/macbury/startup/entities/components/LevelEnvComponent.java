package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;
import de.macbury.startup.level.LevelEnv;

/**
 * Contains reference to current level in which entity live
 */
public class LevelEnvComponent implements Component, Pool.Poolable {
  public LevelEnv env;
  @Override
  public void reset() {
    this.env = null;
  }

  public static class Blueprint extends EmptyComponentBlueprint {}
}
