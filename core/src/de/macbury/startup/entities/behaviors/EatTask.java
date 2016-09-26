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
public class EatTask extends LeafTask<Entity> {
  private static final String TAG = "EatTask";

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Munch munch im eating");
    ProgrammerComponent programmer = Components.Programmer.get(getObject());
    programmer.hunger = 0;
    return Status.SUCCEEDED;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new EatTask();
  }
}
