package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import de.macbury.startup.Programmer;
import de.macbury.startup.entities.components.ProgrammerComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Created by macbury on 21.09.16.
 */
public class IAmHungryTask extends LeafTask<Entity> {
  private static final String TAG = "IAmHungryTask";

  @Override
  public Status execute() {
    Entity entity = getObject();
    ProgrammerComponent programmer = Components.Programmer.get(entity);
    if (programmer.isHungry()) {
      Gdx.app.log(TAG, "Yes im hungry!");
      return Status.SUCCEEDED;
    } else {
      return Status.FAILED;
    }
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new IAmHungryTask();
  }
}
