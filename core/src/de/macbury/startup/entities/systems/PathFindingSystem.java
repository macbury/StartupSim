package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.pfa.PathFinderQueue;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.sched.LoadBalancingScheduler;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.behaviors.pf.FindPathTask;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.messages.MessageType;
import de.macbury.startup.messages.MessagesManager;

/**
 * This system handles all path finding using scheduler in separate thread
 */
public class PathFindingSystem extends EntitySystem implements Disposable {
  private static final String TAG = "PathFindingSystem";
  private final MessagesManager messagesManager;
  private final MapGraph mapGraph;
  private final IndexedAStarPathFinder<TileNode> pathFinder;
  private final PathFinderQueue<TileNode> queue;
  private final LoadBalancingScheduler scheduler;
  private MapData mapData;
  private boolean running;

  public PathFindingSystem(MapGraph mapGraph, MapData mapData, MessagesManager messagesManager) {
    super();
    this.mapData         = mapData;
    this.messagesManager = messagesManager;
    this.mapGraph        = mapGraph;

    mapGraph.rebuildIfNeed();// TODO map change we need to create new path finder and queue
    this.pathFinder = new IndexedAStarPathFinder<TileNode>(mapGraph, true);
    this.queue      = new PathFinderQueue<TileNode>(pathFinder);
    this.scheduler  = new LoadBalancingScheduler(60);
    scheduler.addWithAutomaticPhasing(queue, 2);
    messagesManager.addListener(queue, MessageType.RequestPathFinding);

    Gdx.app.log(TAG, "Creating...");
  }

  @Override
  public void update(float deltaTime) {
    scheduler.run(50000);
  }

  @Override
  public void dispose() {
    Gdx.app.log(TAG, "Disposing...");
    messagesManager.removeListener(queue, MessageType.RequestPathFinding);
    running = false;
    mapData = null;
  }

}
