package de.macbury.startup.entities.helpers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import de.macbury.startup.entities.components.*;
import de.macbury.startup.level.LevelEnv;

/**
 * Simple helper for retrieving components from {@link Entity}
 */
public final class Components {
  public final static ComponentMapper<PositionComponent> Position = ComponentMapper.getFor(PositionComponent.class);
  public final static ComponentMapper<CharsetAnimationComponent> CharsetAnimation = ComponentMapper.getFor(CharsetAnimationComponent.class);
  public final static ComponentMapper<ProgrammerComponent> Programmer = ComponentMapper.getFor(ProgrammerComponent.class);
  public final static ComponentMapper<SpriteComponent> Sprite = ComponentMapper.getFor(SpriteComponent.class);
  public final static ComponentMapper<LevelEnvComponent> LevelEnv = ComponentMapper.getFor(LevelEnvComponent.class);
  public final static ComponentMapper<MovementComponent> Movement = ComponentMapper.getFor(MovementComponent.class);
  public final static ComponentMapper<TargetComponent> Target = ComponentMapper.getFor(TargetComponent.class);
}
