package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;
import de.macbury.startup.entities.components.ConsumableComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;


/**
 * Created by macbury on 05.10.16.
 */
public class FindFoodTask extends EntityTask {
  private static final String TAG = "FindFoodTask";
  private Family consumableFamily = Family.all(PositionComponent.class, ConsumableComponent.class).get();

  @Override
  public Status execute() {
    Gdx.app.log(TAG, "Find path to food...");
    ImmutableArray<Entity> consumableEntities = getEntities().getEntitiesFor(consumableFamily);
    if (consumableEntities.size() == 0) {
      return Status.FAILED;
    } else {
      Entity targetConsumable = consumableEntities.random();
      Components.Target.get(getObject()).set(Components.Position.get(targetConsumable));
      return Status.SUCCEEDED;
    }
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
