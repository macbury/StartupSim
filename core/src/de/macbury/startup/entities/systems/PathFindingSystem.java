package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.pfa.PathFinderQueue;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.sched.LoadBalancingScheduler;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.behaviors.pf.FindPathTask;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.pfa.PoolablePathFinderRequest;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.messages.MessageType;
import de.macbury.startup.messages.MessagesManager;

import static com.badlogic.gdx.ai.pfa.PathFinderRequest.SEARCH_FINALIZED;

/**
 * This system handles all path finding using scheduler in separate thread
 */
public class PathFindingSystem extends EntitySystem implements Disposable, Telegraph {
  private static final String TAG = "PathFindingSystem";
  private final MessagesManager messagesManager;
  private final MapGraph mapGraph;
  private final DelayedRemovalArray<PoolablePathFinderRequest> runningRequests;
  private IndexedAStarPathFinder<TileNode> pathFinder;
  private PathFinderQueue<TileNode> queue;
  private LoadBalancingScheduler scheduler;
  private MapData mapData;

  public PathFindingSystem(MapGraph mapGraph, MapData mapData, MessagesManager messagesManager) {
    super();
    this.mapData         = mapData;
    this.messagesManager = messagesManager;
    this.mapGraph        = mapGraph;
    this.runningRequests = new DelayedRemovalArray<PoolablePathFinderRequest>();

    Gdx.app.log(TAG, "Creating...");

    messagesManager.addListener(this, MessageType.RequestPathFinding);
  }

  @Override
  public void update(float deltaTime) {
    if (mapGraph.rebuildIfNeed()) {
      this.pathFinder = new IndexedAStarPathFinder<TileNode>(mapGraph);
      this.queue      = new PathFinderQueue<TileNode>(pathFinder);

      this.scheduler  = new LoadBalancingScheduler(60);
      scheduler.addWithAutomaticPhasing(queue, 2);

      runningRequests.begin(); {
        for (PoolablePathFinderRequest request : runningRequests) {
          request.status = SEARCH_FINALIZED;
          request.pathFound = false;
          runningRequests.removeValue(request, true);
        }
      } runningRequests.end();
    } else {
      scheduler.run(100000);
      runningRequests.begin(); {
        for (PoolablePathFinderRequest request : runningRequests) {
          if (request.status == SEARCH_FINALIZED) {
            runningRequests.removeValue(request, true);
          }
        }
      } runningRequests.end();
    }
  }

  @Override
  public void dispose() {
    Gdx.app.log(TAG, "Disposing...");
    messagesManager.removeListener(this, MessageType.RequestPathFinding);
    mapData = null;
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    if (queue != null && MessageType.get(msg) == MessageType.RequestPathFinding) {
      runningRequests.add((PoolablePathFinderRequest) msg.extraInfo);
      return queue.handleMessage(msg);
    }
    return false;
  }
}
