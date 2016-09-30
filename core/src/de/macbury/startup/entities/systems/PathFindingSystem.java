package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.PathFinderQueue;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.sched.LoadBalancingScheduler;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.pfa.TileNode;
import de.macbury.startup.messages.MessageType;
import de.macbury.startup.messages.MessagesManager;

/**
 * This system handles all path finding using scheduler in separate thread
 */
public class PathFindingSystem extends EntitySystem implements Disposable, Runnable {
  private static final String TAG = "PathFindingSystem";
  private final Thread backgroundThread;
  private final MessagesManager messagesManager;
  private final MapGraph mapGraph;
  private final IndexedAStarPathFinder<TileNode> pathFinder;
  private final PathFinderQueue<TileNode> queue;
  private final LoadBalancingScheduler scheduler;
  private MapData mapData;
  private boolean running;

  public PathFindingSystem(MapData mapData, MessagesManager messagesManager) {
    super();
    this.mapData         = mapData;
    this.messagesManager = messagesManager;
    this.mapGraph   = new MapGraph(mapData);

    this.pathFinder = new IndexedAStarPathFinder<TileNode>(mapGraph, true);
    this.queue      = new PathFinderQueue<TileNode>(pathFinder);
    this.scheduler  = new LoadBalancingScheduler(60);

    messagesManager.addListener(queue, MessageType.RequestPathFinding);

    Gdx.app.log(TAG, "Creating...");

    this.backgroundThread = new Thread(this);
    backgroundThread.start();
  }

  @Override
  public void run() {
    Gdx.app.log(TAG, "Starting thread");
    running = true;
    scheduler.addWithAutomaticPhasing(queue, 20);
    while(running) {
      try {
        if (checkProcessing()) {
          mapGraph.rebuildIfNeed();
          scheduler.run(50000);
        }
        Thread.sleep(50);
      } catch (InterruptedException e) {
        Gdx.app.log(TAG, "Thread Interrupted");
        running = false;
      }
    }

    Gdx.app.log(TAG, "Thread Stopped");
  }

  @Override
  public void dispose() {
    Gdx.app.log(TAG, "Disposing...");
    messagesManager.removeListener(queue, MessageType.RequestPathFinding);
    backgroundThread.interrupt();
    running = false;
    mapData = null;
  }
}
