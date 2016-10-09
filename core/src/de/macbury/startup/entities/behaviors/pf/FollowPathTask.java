package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
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

  private final Vector2 tempPosition = new Vector2();

  @Override
  public void end() {
    Gdx.app.log(TAG, "Finished");
  }

  @Override
  public Status execute() {
    PositionComponent position = Components.Position.get(getObject());
    MovementComponent movement = Components.Movement.get(getObject());

    if (movement.isFinished()) {
      if (movement.path.size > 0) {
        TileNode node = movement.path.removeIndex(0);
        if (getMapData().isPassable(node.x, node.y)) {
          movement.beginMovement(position, tempPosition.set(node.x, node.y));
        } else {
          return Status.FAILED;
        }
      } else {
        return Status.SUCCEEDED;
      }
    }
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
