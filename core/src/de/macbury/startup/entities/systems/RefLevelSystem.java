package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.components.LevelEnvComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.level.LevelEnv;

/**
 * Adds reference to {@link de.macbury.startup.level.LevelEnv} to each
 */
public class RefLevelSystem extends EntitySystem implements Disposable, EntityListener {
  private final Family family;
  private LevelEnv levelEnv;

  public RefLevelSystem(LevelEnv levelEnv) {
    this.levelEnv = levelEnv;
    this.family   = Family.all(LevelEnvComponent.class).get();
  }

  @Override
  public void addedToEngine(Engine engine) {
    engine.addEntityListener(family, this);
  }

  @Override
  public void removedFromEngine(Engine engine) {
    engine.removeEntityListener(this);
  }

  @Override
  public void dispose() {
    levelEnv = null;
  }

  @Override
  public void entityAdded(Entity entity) {
    Components.LevelEnv.get(entity).env = levelEnv;
  }

  @Override
  public void entityRemoved(Entity entity) {
    Components.LevelEnv.get(entity).env = null;
  }
}
