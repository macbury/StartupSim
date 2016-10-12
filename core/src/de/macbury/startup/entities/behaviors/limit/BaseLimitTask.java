package de.macbury.startup.entities.behaviors.limit;

import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.components.NotificationCloudComponent;
import de.macbury.startup.entities.components.ThrottleComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Base logic for all limit tasks
 */
public abstract class BaseLimitTask extends EntityTask {
  @TaskAttribute(name = "type", required = true)
  public String type;

  /**
   * Return component with throttle information
   * @return
   */
  protected ThrottleComponent getThrottle() {
    return Components.Throttle.get(getObject());
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
