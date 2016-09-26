package de.macbury.startup.entities.blueprint;


import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.entities.EntityManager;

/**
 * This blueprint is used to build all entities using {@link de.macbury.expanse.core.entities.EntityManager} and all information in
 * {@link ComponentBlueprint}
 */
public class EntityBlueprint implements Disposable {
  private Array<ComponentBlueprint> componentBlueprints;

  public EntityBlueprint(Array<ComponentBlueprint> componentBlueprints) {
    this.componentBlueprints = new Array<ComponentBlueprint>(componentBlueprints);
  }

  /**
   * Creates entity using pools from entity manager
   * @param entityManager
   * @return
   */
  public Entity create(EntityManager entityManager) {
    Entity entity = entityManager.createEntity();
    for (ComponentBlueprint blueprint : componentBlueprints) {
      Component component  = entityManager.createComponent(blueprint.componentKlass);
      ((Pool.Poolable)component).reset();
      blueprint.applyTo(component, entity);
      entity.add(component);
    }
    return entity;
  }

  /**
   * Creates new entity and adds it to {@link EntityManager}
   * @param entityManager
   * @return
   */
  public Entity createAndAdd(EntityManager entityManager) {
    Entity entity = create(entityManager);
    entityManager.addEntity(entity);
    return entity;
  }

  @Override
  public void dispose() {
    for (ComponentBlueprint blueprint : componentBlueprints) {
      blueprint.dispose();
    }
    componentBlueprints.clear();
  }
}