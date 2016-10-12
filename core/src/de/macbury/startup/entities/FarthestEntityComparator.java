package de.macbury.startup.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import de.macbury.startup.entities.helpers.Components;

import java.util.Comparator;

/**
 * Sort entities from farthest to nearest
 */
public class FarthestEntityComparator implements Comparator<Entity> {
  private Entity sourceEntity;
  @Override
  public int compare(Entity o1, Entity o2) {
    Vector2 sourcePosition = Components.Position.get(sourceEntity);
    Vector2 o1Position = Components.Position.get(o1);
    Vector2 o2Position = Components.Position.get(o2);
    final float dst = (int)(1000f * sourcePosition.dst2(o1Position)) - (int)(1000f * sourcePosition.dst2(o2Position));
    final int result = dst < 0 ? -1 : (dst > 0 ? 1 : 0);
    return -result;
  }

  public Entity getSourceEntity() {
    return sourceEntity;
  }

  public void setSourceEntity(Entity sourceEntity) {
    this.sourceEntity = sourceEntity;
  }
}
