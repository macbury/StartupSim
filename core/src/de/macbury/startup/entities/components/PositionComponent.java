package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;

/**
 * Position of entity in world
 */
public class PositionComponent extends Vector2 implements Component, Pool.Poolable {
  @Override
  public void reset() {
    setZero();
  }

  public static class Blueprint extends ComponentBlueprint<PositionComponent> {
    public float x;
    public float y;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {

    }

    @Override
    public void assignDependencies(Assets assets) {

    }

    @Override
    public void applyTo(PositionComponent component, Entity owner) {
      component.set(x, y);
    }

    @Override
    public void load(JsonValue source, Json json) {
      x = source.getFloat("x", 0);
      y = source.getFloat("y", 0);
    }

    @Override
    public void save(Json target, PositionComponent source) {
      target.writeValue("x", source.x);
      target.writeValue("y", source.y);
    }

    @Override
    public void dispose() {

    }
  }
}
