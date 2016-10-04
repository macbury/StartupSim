package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.helpers.Components;

/**
 * Created by macbury on 04.10.16.
 */
public class HaveTargetTask extends EntityTask {
  @Override
  public Status execute() {
    if (Components.Movement.get(getObject()).target.isZero()) {
      return Status.FAILED;
    } else {
      return Status.SUCCEEDED;
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new HaveTargetTask();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
