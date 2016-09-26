package de.macbury.startup.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.CoreGame;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.EntityBlueprint;

/**
 * Manage all {@link com.badlogic.ashley.core.Entity} in game
 */
public class EntityManager extends PooledEngine implements Disposable {
  private Assets assets;

  public EntityManager(CoreGame game) {
    this.assets = game.assets;
  }

  /**
   * Build entity using {@link de.macbury.startup.entities.blueprint.EntityBlueprint} but not add to the world
   * @param entityBlueprintName
   * @return
   */
  public Entity build(String entityBlueprintName) {
    EntityBlueprint blueprint = assets.get(entityBlueprintName);
    return blueprint.create(this);
  }

  /**
   * Build and add entity to the world
   * @param entityBlueprintName
   * @return
   */
  public Entity create(String entityBlueprintName) {
    Entity entity = build(entityBlueprintName);
    addEntity(entity);
    return entity;
  }

  @Override
  public void dispose() {
    removeAllEntities();
    clearPools();
    assets = null;
    for (EntitySystem system : this.getSystems()) {
      if (Disposable.class.isInstance(system)) {
        Disposable dis = (Disposable)system;
        dis.dispose();
      }
    }
  }
}
