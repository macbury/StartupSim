package de.macbury.startup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by macbury on 21.09.16.
 */
public class EatTask extends LeafTask<Programmer> {
  private static final String TAG = "EatTask";

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Munch munch im eating");
    getObject().hunger = 0;
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Programmer> copyTo(Task<Programmer> task) {
    return new EatTask();
  }
}
