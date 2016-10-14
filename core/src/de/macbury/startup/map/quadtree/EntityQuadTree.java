package de.macbury.startup.map.quadtree;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * Quad Tree for fetching entities
 */
public class EntityQuadTree extends QuadTree<Entity> {

  /**
   * Initialize new root node
   *
   * @param x
   * @param y
   * @param width
   * @param height
   */
  public EntityQuadTree(float x, float y, float width, float height) {
    super(x, y, width, height);
  }

  protected EntityQuadTree(QuadTree<Entity> parent) {
    super(parent);
  }

  /**
   * Find all elements that trees are in zone and match family
   * @param list
   * @param zone
   * @return
   */
  public Array<Entity> getElements(Array<Entity> list, Rectangle zone, Family family) {
    getElements(list, zone);
    tempElements.clear();
    for (Entity entity : list) {
      if (family.matches(entity)) {
        tempElements.add(entity);
      }
    }
    list.clear();
    list.addAll(tempElements);
    tempElements.clear();
    return list;
  }
}
