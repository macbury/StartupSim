package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import de.macbury.startup.Programmer;

/**
 * Created by macbury on 19.09.16.
 */
public class BarkTask extends LeafTask<Entity> {
  private static final String TAG = "BarkTask";

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Bark!");
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new BarkTask();
  }
}
