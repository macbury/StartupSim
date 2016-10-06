package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.math.Vector2;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.pfa.SmoothedGraphPath;
import de.macbury.startup.map.pfa.TileDistanceHeuristic;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.messages.MessageType;

/**
 * Handles finding path
 */
public class FindPathTask extends EntityTask {
  private static final String TAG = "FindPathTask";
  private PathFinderRequest<TileNode> request;

  @Override
  public void start() {
    Gdx.app.log(TAG, "Searching path...");
    Vector2 startPosition  = Components.Position.get(getObject());
    Vector2 targetPosition = Components.Movement.get(getObject()).target;
    Components.Movement.get(getObject()).setPath(null);

    TileNode startNode  = getMapGraph().getNode((int)startPosition.x, (int)startPosition.y);
    TileNode targetNode = getMapGraph().getNode((int)targetPosition.x, (int)targetPosition.y);

    this.request = new PathFinderRequest<TileNode>(startNode, targetNode, new TileDistanceHeuristic(), new SmoothedGraphPath());
    dispatch(MessageType.RequestPathFinding, request);
  }

  @Override
  public Status execute() {
    if (request.status == PathFinderRequest.SEARCH_FINALIZED) {
      if (request.pathFound) {
        Components.Movement.get(getObject()).setPath(request.resultPath);
        Gdx.app.log(TAG, "Path found: " + request.resultPath.getCount());
        return Status.SUCCEEDED;
      } else {
        Gdx.app.log(TAG, "Failed...");
        return Status.FAILED;
      }
    } else {
      return Status.RUNNING;
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new FindPathTask();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
