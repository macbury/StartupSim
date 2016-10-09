package de.macbury.startup.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.macbury.startup.CoreGame;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.EntityBlueprint;
import de.macbury.startup.entities.helpers.Components;

/**
 * Manage all {@link com.badlogic.ashley.core.Entity} in game
 */
public class EntityManager extends PooledEngine implements Disposable {
  private static final String TAG = "EntityManager";
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
        Gdx.app.log(TAG, "Disposing " + system.getClass().getSimpleName());
        Disposable dis = (Disposable)system;
        dis.dispose();
      }
    }
  }

  /**
   * Build entity and spawn it at position
   * @param entityBlueprintName
   * @param x
   * @param y
   */
  public Entity spawn(String entityBlueprintName, float x, float y) {
    Entity entity = build(entityBlueprintName);
    if (!Components.Position.has(entity)) {
      throw new GdxRuntimeException("Entity " + entityBlueprintName + " dont have Position component!!!!");
    }
    Components.Position.get(entity).set(x,y);
    addEntity(entity);
    return entity;
  }
}
