package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.pfa.PoolablePathFinderRequest;
import de.macbury.startup.messages.MessageType;

/**
 * This task contains all methods needed to find path to specified target in {@link de.macbury.startup.entities.components.TargetComponent}
 */
public abstract class BaseFindPathTask extends EntityTask {
  private Array<PoolablePathFinderRequest> pooledRequests = new Array<PoolablePathFinderRequest>();
  private boolean searchingPath;

  /**
   * Sets target on current entity and try to find path. After path finding is done it will trigger {@link BaseFindPathTask#onPathFinderRequestComplete(PoolablePathFinderRequest)}
   * @param target position to visit
   */
  protected void requestPathFinding(Vector2 target) {
    Components.Target.get(getObject()).set(target);
    PoolablePathFinderRequest request = PoolablePathFinderRequest.pool.obtain();
    request.prepare(getMapGraph(), getObject(), getMessages());
    dispatch(MessageType.RequestPathFinding, request);
    pooledRequests.add(request);
    searchingPath = true;
  }

  /**
   * Callback with current path finding request
   * @param request
   */
  public abstract void onPathFinderRequestComplete(PoolablePathFinderRequest request);

  @Override
  public void end() {
    super.end();
    PoolablePathFinderRequest.pool.freeAll(pooledRequests);
    pooledRequests.clear();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    if (MessageType.get(msg) == MessageType.RequestPathFinding) {
      searchingPath = false;
      onPathFinderRequestComplete((PoolablePathFinderRequest)msg.extraInfo);
      return true;
    }
    return false;
  }

  protected boolean isSearchingPath() {
    return searchingPath;
  }
}
