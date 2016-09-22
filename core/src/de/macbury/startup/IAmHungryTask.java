package de.macbury.startup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

/**
 * Created by macbury on 21.09.16.
 */
public class IAmHungryTask extends LeafTask<Programmer> {
  private static final String TAG = "IAmHungryTask";

  @Override
  public Status execute() {
    Programmer programmer = getObject();
    if (programmer.isHungry()) {
      Gdx.app.log(TAG, "Yes im hungry!");
      return Status.SUCCEEDED;
    } else {
      return Status.FAILED;
    }
  }

  @Override
  protected Task<Programmer> copyTo(Task<Programmer> task) {
    return new IAmHungryTask();
  }
}
