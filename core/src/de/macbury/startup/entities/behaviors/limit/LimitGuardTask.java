package de.macbury.startup.entities.behaviors.limit;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.behaviors.EntityTask;

/**
 * Prevent execution of command if {@link ThrottleTask} had been set with name
 */
public class LimitGuardTask extends BaseLimitTask {
  @Override
  public Status execute() {
    if (getThrottle().isLimited(type)) {
      return Status.FAILED;
    } else {
      return Status.SUCCEEDED;
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    LimitGuardTask throttleTask = new LimitGuardTask();
    throttleTask.type = type;
    return throttleTask;
  }
}
