package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import de.macbury.startup.entities.helpers.Components;

/**
 * Created by macbury on 05.10.16.
 */
public class FindFoodTask extends EntityTask {
  private static final String TAG = "FindFoodTask";

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Find path to food...");
    //Components.Movement.get(getObject()).target.set(10,10);
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new FindFoodTask();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
