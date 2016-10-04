package de.macbury.startup.entities.behaviors.pf;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.helpers.Components;

/**
 * Created by macbury on 04.10.16.
 */
public class FollowPathTask extends EntityTask {

  private static final String TAG = "FollowPathTask";
  private float alpha;
  private float sleepFor;

  @Override
  public void start() {
    super.start();
    alpha = 0;
    sleepFor = 1f + new Float(Math.random() * 3);
    Gdx.app.log(TAG, "Following path for: " + sleepFor);
  }

  @Override
  public void end() {
    Components.Position.get(getObject()).set(Components.Movement.get(getObject()).target);
    Components.Movement.get(getObject()).reset();
    Gdx.app.log(TAG, "Finished");
  }

  @Override
  public Status execute() {
    alpha += Gdx.graphics.getDeltaTime();
    if (alpha >= sleepFor) {
      return Status.SUCCEEDED;
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
