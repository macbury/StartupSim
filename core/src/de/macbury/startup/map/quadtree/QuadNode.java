package de.macbury.startup.map.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;

/**
 * Represents record in quad tree
 */
public class QuadNode<QuadNodeType> extends Rectangle implements Pool.Poolable {
  private QuadNodeType element;
  @Override
  public void reset() {
    set(0,0, 1,1);
    setElement(null);
  }

  public QuadNodeType getElement() {
    return element;
  }

  public void setElement(QuadNodeType element) {
    this.element = element;
  }
}
