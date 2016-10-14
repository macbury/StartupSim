package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import de.macbury.startup.entities.components.ConsumableComponent;
import de.macbury.startup.entities.components.ProgrammerComponent;
import de.macbury.startup.entities.helpers.Components;

/**
 * Created by macbury on 21.09.16.
 */
public class EatTask extends EntityTask {
  private static final String TAG                 = "EatTask";
  private int accumulator                         = 0;
  private Array<Entity> consumableEntities        = new Array<Entity>();
  private Rectangle tile                          = new Rectangle(0,0, 1,1);
  private Entity consumableEntity;

  @Override
  public void start() {
    super.start();
    accumulator = 0;
    consumableEntities.clear();

    tile.setPosition(Components.Target.get(getObject()));
    getTree().getElements(consumableEntities, tile);

    consumableEntity = consumableEntities.get(0);
  }

  @Override
  public Status execute() {
    ProgrammerComponent programmer = Components.Programmer.get(getObject());

    if (consumableEntity != null) {
      if (accumulator > 50) {
        Gdx.app.log(TAG, "Munch munch im eating");
        programmer.hunger = 0;
        getEntities().removeEntity(consumableEntity);
        return Status.SUCCEEDED;
      } else {
        accumulator++;
        return Status.RUNNING;
      }
    } else {
      Gdx.app.log(TAG, "No food at this position");
      return Status.FAILED;
    }
  }

  @Override
  public void end() {
    super.end();
    consumableEntity = null;
    consumableEntities.clear();
  }

  @Override
  protected Task<Entity> copyTo(Task<Entity> task) {
    return new EatTask();
  }

  @Override
  public boolean handleMessage(Telegram msg) {
    return false;
  }
}
