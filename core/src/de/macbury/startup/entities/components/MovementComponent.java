package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;
import de.macbury.startup.map.pfa.TileNode;

/**
 * This component contains information about current path entity will follow and target position
 */
public class MovementComponent implements Component, Pool.Poolable {
  private static final float DEFAULT_SPEED = 10f;
  public Vector2 target = new Vector2();
  private GraphPath<TileNode> path;
  public float speed = DEFAULT_SPEED;

  @Override
  public void reset() {
    target.setZero();
    path = null;
    speed = DEFAULT_SPEED;
  }

  public void setPath(GraphPath<TileNode> resultPath) {
    path = resultPath;
  }

  public GraphPath<TileNode> getPath() {
    return path;
  }

  public static class Blueprint extends ComponentBlueprint<MovementComponent> {
    private float speed;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {

    }

    @Override
    public void assignDependencies(Assets assets) {

    }

    @Override
    public void applyTo(MovementComponent component, Entity target) {
      component.speed = speed;
    }

    @Override
    public void load(JsonValue source, Json json) {
      speed = source.getFloat("speed", DEFAULT_SPEED);
    }

    @Override
    public void save(Json target, MovementComponent source) {
      target.writeValue("speed", source.speed);
    }

    @Override
    public void dispose() {

    }
  }
}
