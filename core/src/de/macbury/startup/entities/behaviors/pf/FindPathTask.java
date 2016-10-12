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
import de.macbury.startup.map.pfa.PoolablePathFinderRequest;
import de.macbury.startup.map.pfa.SmoothedGraphPath;
import de.macbury.startup.map.pfa.TileDistanceHeuristic;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.messages.MessageType;

/**
 * Handles finding path
 */
public class FindPathTask extends EntityTask {
  private static final String TAG = "FindPathTask";
  private PoolablePathFinderRequest request;

  @Override
  public void start() {
    Gdx.app.log(TAG, "Searching path...");

    this.request = PoolablePathFinderRequest.pool.obtain();
    request.prepare(getMapGraph(), getObject(), getMessages());
    dispatch(MessageType.RequestPathFinding, request);
  }

  @Override
  public void end() {
    if (request != null)
      PoolablePathFinderRequest.pool.free(request);
    request = null;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new FindPathTask();
  }

  @Override
  public Status execute() {
    if (request == null) {
      Gdx.app.log(TAG, "No target found...");
      return Status.FAILED;
    } else if (request.status == PathFinderRequest.SEARCH_FINALIZED) {
      if (request.pathFound) {
        Components.Movement.get(getObject()).setPath(request.resultPath);
        Gdx.app.log(TAG, "Path found: " + request.resultPath.getCount());
        return Status.SUCCEEDED;
      } else {
        Gdx.app.log(TAG, "Path not found...");
        return Status.FAILED;
      }
    } else {
      return Status.RUNNING;
    }
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
