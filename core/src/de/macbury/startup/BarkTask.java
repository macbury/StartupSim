package de.macbury.startup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import sun.rmi.runtime.Log;

/**
 * Created by macbury on 19.09.16.
 */
public class BarkTask extends LeafTask<Programmer> {
  private static final String TAG = "BarkTask";

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Bark!");
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Programmer> copyTo(Task<Programmer> task) {
    return new BarkTask();
  }
}
