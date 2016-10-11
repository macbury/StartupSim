package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.components.NotificationCloudComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Show notification above head of entity
 */
public class NotificationTask extends EntityTask {
  @TaskAttribute(name = "type", required = true)
  public NotificationCloudComponent.Notification type;
  private float timeLeft;

  @Override
  public void start() {
    getNotificationCloud().current = type;
    timeLeft = 3f;
  }

  public Status execute() {
    timeLeft -= Gdx.graphics.getDeltaTime();
    if (timeLeft > 0) {
      return Status.RUNNING;
    } else {
      return Status.FAILED;
    }
  }

  @Override
  public void end() {
    getNotificationCloud().current = NotificationCloudComponent.Notification.None;
  }

  private NotificationCloudComponent getNotificationCloud() {
    return Components.NotificationCloud.get(getObject());
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    NotificationTask nTask = new NotificationTask();
    nTask.type = type;
    return nTask;
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
