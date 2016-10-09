package de.macbury.startup.map.pfa;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.messages.MessagesManager;

/**
 * Reusable path finding request
 */
public class PoolablePathFinderRequest extends PathFinderRequest<TileNode> implements Pool.Poolable {
  /**
   * Pool with all requests
   */
  public final static Pool<PoolablePathFinderRequest> pool = new Pool<PoolablePathFinderRequest>() {
    @Override
    protected PoolablePathFinderRequest newObject() {
      return new PoolablePathFinderRequest();
    }
  };

  public PoolablePathFinderRequest() {
    super();
    this.heuristic  = new TileDistanceHeuristic();
    this.resultPath = new SmoothedGraphPath();
  }

  @Override
  public void reset() {
    resultPath.clear();
    startNode  = null;
    endNode    = null;
    dispatcher = null;
    executionFrames = 0;
    pathFound = false;
    status = SEARCH_NEW;
    statusChanged = false;
  }

  /**
   * Prepare request to find path between entity {@link de.macbury.startup.entities.components.PositionComponent} and {@link de.macbury.startup.entities.components.TargetComponent}
   * @param mapGraph
   * @param entity
   * @param messages
   */
  public void prepare(MapGraph mapGraph, Entity entity, MessagesManager messages) {
    this.reset();
    dispatcher = messages;
    Vector2 startPosition  = Components.Position.get(entity);
    Vector2 targetPosition = Components.Target.get(entity);

    startNode  = mapGraph.getNode((int)startPosition.x, (int)startPosition.y);//TODO snap to tile
    endNode    = mapGraph.getNode((int)targetPosition.x, (int)targetPosition.y);
  }
}
