package de.macbury.startup.entities.helpers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import de.macbury.startup.entities.components.CharsetAnimationComponent;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.components.ProgrammerComponent;
import de.macbury.startup.entities.components.SpriteComponent;
import de.macbury.startup.graphics.CharsetAnimation;

/**
 * Simple helper for retrieving components from {@link Entity}
 */
public final class Components {
  public final static ComponentMapper<PositionComponent> Position = ComponentMapper.getFor(PositionComponent.class);
  public final static ComponentMapper<CharsetAnimationComponent> CharsetAnimation = ComponentMapper.getFor(CharsetAnimationComponent.class);
  public final static ComponentMapper<ProgrammerComponent> Programmer = ComponentMapper.getFor(ProgrammerComponent.class);
  public final static ComponentMapper<SpriteComponent> Sprite = ComponentMapper.getFor(SpriteComponent.class);
}
