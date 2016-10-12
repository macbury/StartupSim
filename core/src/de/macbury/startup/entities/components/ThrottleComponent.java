package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;

/**
 * Contain all information about throttled events
 */
public class ThrottleComponent extends ObjectFloatMap<String> implements Component, Pool.Poolable {
  @Override
  public void reset() {
    clear();
  }

  public boolean isLimited(String type) {
    return get(type, 0) > 0.0f;
  }

  public static class Blueprint extends EmptyComponentBlueprint<ThrottleComponent> {

  }
}
