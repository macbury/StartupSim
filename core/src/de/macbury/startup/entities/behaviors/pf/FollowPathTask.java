package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.pfa.GraphPath;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.components.MovementComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.pfa.TileNode;

/**
 * Created by macbury on 04.10.16.
 */
public class FollowPathTask extends EntityTask {

  private static final String TAG = "FollowPathTask";
  private float alpha;
  private float sleepFor;
  private int currentIndex;

  @Override
  public void start() {
    super.start();
    alpha = 0;
    currentIndex = 0;
    sleepFor = 1f + new Float(Math.random() * 3);
    Gdx.app.log(TAG, "Following path for: " + sleepFor);
  }

  @Override
  public void end() {
    //Components.Position.get(getObject()).set(Components.Movement.get(getObject()).target);
    Components.Movement.get(getObject()).reset();
    Gdx.app.log(TAG, "Finished");
  }

  @Override
  public Status execute() {
    MovementComponent movementComponent = Components.Movement.get(getObject());
    PositionComponent positionComponent = Components.Position.get(getObject());

    GraphPath<TileNode> path = movementComponent.getPath();
    alpha += Gdx.graphics.getDeltaTime();
    if (currentIndex == path.getCount()) {
      return Status.SUCCEEDED;
    }
    if (alpha > 0.1f) {
      alpha = 0f;
      TileNode node = path.get(currentIndex);
      positionComponent.set(node.x, node.y);
      currentIndex++;
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
