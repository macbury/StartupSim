package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.components.MovementComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.pfa.TileNode;

/**
 * Follow path. If destination is reached then it returns Status.SUCCEEDED. If there is obstacle along way then task will be failed
 */
public class FollowPathTask extends EntityTask {

  private static final String TAG = "FollowPathTask";
  private float alpha;

  private final Vector2 startPosition = new Vector2();
  private final Vector2 endPosition   = new Vector2();
  private final Array<TileNode> path  = new Array<TileNode>();

  @Override
  public void start() {
    MovementComponent movementComponent = Components.Movement.get(getObject());
    alpha = 2.0f;
    for (TileNode node : movementComponent.getPath()) {
      path.add(node);
    }

    Gdx.app.log(TAG, "Following path for");
  }

  @Override
  public void end() {
    path.clear();
    Gdx.app.log(TAG, "Finished");
  }

  @Override
  public Status execute() {
    PositionComponent position = Components.Position.get(getObject());
    MovementComponent movement = Components.Movement.get(getObject());

    alpha += Gdx.graphics.getDeltaTime() * movement.speed;

    if (alpha > 1.0f) {
      if (path.size > 0) {
        TileNode node = path.removeIndex(0);
        startPosition.set(position);
        endPosition.set(node.x, node.y);
        alpha         = 0;
      } else {
        return Status.SUCCEEDED;
      }
    }

    position.set(startPosition).lerp(endPosition, alpha);

    return Status.RUNNING;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new FollowPathTask();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
