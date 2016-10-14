package de.macbury.startup.map.quadtree;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
}
