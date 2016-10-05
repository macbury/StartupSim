package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.blueprint.EmptyComponentBlueprint;
import de.macbury.startup.map.pfa.TileNode;

/**
 * This component contains information about current path entity will follow and target position
 */
public class MovementComponent implements Component, Pool.Poolable {
  public Vector2 target = new Vector2();
  private GraphPath<TileNode> path;

  @Override
  public void reset() {
    target.setZero();
    path = null;
  }

  public void setPath(GraphPath<TileNode> resultPath) {
    path = resultPath;
  }

  public GraphPath<TileNode> getPath() {
    return path;
  }



  public static class Blueprint extends EmptyComponentBlueprint {

  }
}
