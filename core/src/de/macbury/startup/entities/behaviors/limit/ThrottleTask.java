package de.macbury.startup.entities.behaviors.limit;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.macbury.startup.entities.behaviors.EntityTask;

/**
 * Set throttle for {@link LimitGuardTask}
 */
public class ThrottleTask extends BaseLimitTask {
  private static final String TAG = "ThrottleTask";
  @TaskAttribute(name = "time", required = true)
  public float time;

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Throttling: " + type + " for " + time + " seconds!");
    getThrottle().put(type, time);
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    ThrottleTask throttleTask = new ThrottleTask();
    throttleTask.type = type;
    throttleTask.time = time;
    return throttleTask;
  }

}
