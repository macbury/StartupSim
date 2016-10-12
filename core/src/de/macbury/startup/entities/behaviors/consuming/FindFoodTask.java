package de.macbury.startup.entities.behaviors.consuming;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import de.macbury.startup.entities.behaviors.EntityTask;
import de.macbury.startup.entities.behaviors.pf.BaseFindPathTask;
import de.macbury.startup.entities.components.ConsumableComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.pfa.PoolablePathFinderRequest;

import java.util.Comparator;

/**
 * Find consumable entity and set as target to follow
 */
public class FindFoodTask extends BaseFindPathTask {
  private static final String TAG = "FindFoodTask";
  private Family consumableFamily = Family.all(PositionComponent.class, ConsumableComponent.class).get();
  private Array<Entity> consumableEntitiesToCheck = new Array<Entity>(false, 16);
  private PoolablePathFinderRequest validRequest;

  @Override
  public void start() {
    super.start();
    consumableEntitiesToCheck.clear();
    for (Entity entity : getEntities().getEntitiesFor(consumableFamily)) {
      consumableEntitiesToCheck.add(entity);
    }
    /*consumableEntitiesToCheck.sort(new Comparator<Entity>() {
      @Override
      public int compare(Entity o1, Entity o2) {
        return 0;
      }
    });*/ //TODO sort from farthest to closest
    checkPathToNextEntity();
  }

  private void checkPathToNextEntity() {
    if (consumableEntitiesToCheck.size > 0) {
      Entity entity = consumableEntitiesToCheck.pop();
      requestPathFinding(Components.Position.get(entity));
    }
  }

  @Override
  public Status execute() {
    if (validRequest != null) {
      Components.Movement.get(getObject()).setPath(validRequest.resultPath);
      return Status.SUCCEEDED;
    } else if (isSearchingPath()) {
      return Status.RUNNING;
    } else if (consumableEntitiesToCheck.size > 0) {
      return Status.RUNNING;
    } else {
      return Status.FAILED;
    }
  }

  @Override
  public void end() {
    super.end();
    validRequest = null;
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new FindFoodTask();
  }

  @Override
  public void onPathFinderRequestComplete(PoolablePathFinderRequest request) {
    if (request.pathFound) {
      validRequest = request;
    } else {
      checkPathToNextEntity();
    }
  }
}
