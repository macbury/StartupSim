package de.macbury.startup.entities;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Disposable;

/**
 * Manage all {@link com.badlogic.ashley.core.Entity} in game
 */
public class EntityManager extends PooledEngine implements Disposable {

  @Override
  public void dispose() {
    removeAllEntities();
    clearPools();

    for (EntitySystem system : this.getSystems()) {
      if (Disposable.class.isInstance(system)) {
        Disposable dis = (Disposable)system;
        dis.dispose();
      }
    }
  }
}
