package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
  private GraphPath<TileNode> path;
  public float speed = DEFAULT_SPEED;

  /**
   * This variables are used for interpolating between two points
   */
  private float alpha;
  private final Vector2 startPosition = new Vector2();
  private final Vector2 endPosition = new Vector2();
  private final Vector2 direction = new Vector2();
  private boolean finished;

  public Vector2 getDirection() {
    return direction;
  }

  @Override
  public void reset() {
    direction.setZero();
    startPosition.setZero();
    endPosition.setZero();
    alpha = 0.0f;
    finished = true;
    path = null;
    speed = DEFAULT_SPEED;
  }

  /**
   * Start interpolation between two points
   * @param start
   * @param finish
   */
  public void beginMovement(Vector2 start, Vector2 finish) {
    alpha = 0.0f;
    finished = false;
    startPosition.set(start);
    endPosition.set(finish);
    direction.set(start).sub(finish).nor();
  }

  /**
   * Did interpolation between two points end?
   * @return
   */
  public boolean isFinished() {
    return finished;
  }

  /**
   * Do movement and calculate new position
   * @param deltaTime
   * @param out
   */
  public void step(float deltaTime, Vector2 out) {
    alpha += deltaTime * speed;
    if (alpha > 1.0f) {
      finished = true;
      alpha = 1.0f;
    }
    out.set(startPosition).lerp(endPosition, alpha);
  }

  /**
   * Stops current movement
   * @param resultPath
   */
  public void setPath(GraphPath<TileNode> resultPath) {
    alpha = 1.0f;
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
