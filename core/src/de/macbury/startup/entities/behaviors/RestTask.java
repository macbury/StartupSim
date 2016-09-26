package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import de.macbury.startup.Programmer;

/**
 * Created by macbury on 19.09.16.
 */
public class RestTask extends LeafTask<Entity> {
  private static final String TAG = "RestTask";
  private float alpha;

  @TaskAttribute(required = true) public float sleepFor = 5f;

  @Override
  public void start() {
    super.start();
    alpha = 0;
    Gdx.app.log(TAG, "Going to sleep: " + sleepFor);
  }

  @Override
  public void end() {
    super.end();
    Gdx.app.log(TAG, "Finished");
  }

  @Override
  public Status execute() {
    alpha += Gdx.graphics.getDeltaTime();
    if (alpha >= sleepFor) {
      Gdx.app.log(TAG, "Thats is nice!");
      return Status.SUCCEEDED;
    }
    return Status.RUNNING;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    RestTask restTask = (RestTask)task;
    restTask.sleepFor = sleepFor;
    return task;
  }
}
