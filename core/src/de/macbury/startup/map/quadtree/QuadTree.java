package de.macbury.startup.map.quadtree;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.lang.reflect.InvocationTargetException;

/**
 * Simple implementation quad tree with object pooling
 */
public class QuadTree<QuadNodeElementType> implements Disposable, Pool.Poolable {
  /**
   * GLOBAL CONFIGRATION
   * if this is reached,
   * the zone is subdivised
   */
  public static int maxItemByNode = 5;
  public static int maxLevel = 10;

  /**
   * the four sub regions,
   * may be null if not needed
   */
  protected QuadTree<QuadNodeElementType>[] regions;

  protected static final int REGION_SELF = -1;
  protected static final int REGION_NW = 0;
  protected static final int REGION_NE = 1;
  protected static final int REGION_SW = 2;
  protected static final int REGION_SE = 3;

  protected Pool<QuadNode<QuadNodeElementType>> nodePool;
  protected Pool<QuadTree<QuadNodeElementType>> treePool;
  /**
   * Current level of tree
   */
  protected int level;
  /**
   * Nodes on current level
   */
  protected final Array<QuadNode<QuadNodeElementType>> nodes;

  /**
   * current rectangle zone
   */
  private final Rectangle zone;

  protected final Array<QuadNode<QuadNodeElementType>> tempNodes = new Array<QuadNode<QuadNodeElementType>>();

  /**
   * Initialize new root node
   */
  public QuadTree(float x, float y, float width, float height) {
    this();
    initializePools();
    set(x, y, width, height, 0);
  }

  private QuadTree() {
    zone          = new Rectangle();
    this.nodes    = new Array<QuadNode<QuadNodeElementType>>();
    regions       = null;
  }

  protected QuadTree(QuadTree<QuadNodeElementType> parent) {
    this();
    this.treePool = parent.treePool;
    this.nodePool = parent.nodePool;
  }

  /**
   * Initialize pools used for creating objects
   */
  protected void initializePools() {
    Class[] quadTreeConstructorArguments = new Class[] { QuadTree.class };
    this.nodePool = new Pool<QuadNode<QuadNodeElementType>>() {
      @Override
      protected QuadNode<QuadNodeElementType> newObject() {
        return new QuadNode<QuadNodeElementType>();
      }
    };
    this.treePool = new Pool<QuadTree<QuadNodeElementType>>() {
      @Override
      protected QuadTree<QuadNodeElementType> newObject() {
        /**
         * We want sub nodes to be the same class as root node
         */
        try {
          return QuadTree.this.getClass().getDeclaredConstructor(QuadTree.class).newInstance(QuadTree.this);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }

        return null;
      }
    };
  }

  /**
   * Set on which level tree resides and what size it has
   */
  public QuadTree<QuadNodeElementType> set(float x, float y, float width, float height, int newLevel) {
    this.level = newLevel;
    this.zone.set(x, y, width, height);
    return this;
  }

  protected int findRegion(Rectangle r) {
    int region = REGION_SELF;
    if (nodes.size >= maxItemByNode && this.level < maxLevel) {
      if (regions == null) {
        this.split();
      }

      if (regions[REGION_NW].getZone().contains(r)) {
        region = REGION_NW;
      } else if (regions[REGION_NE].getZone().contains(r)) {
        region = REGION_NE;
      } else if (regions[REGION_SW].getZone().contains(r)) {
        region = REGION_SW;
      } else if (regions[REGION_SE].getZone().contains(r)) {
        region = REGION_SE;
      }
    }

    return region;
  }

  /**
   * Split current node in sub nodes
   * @return
   */
  private QuadTree<QuadNodeElementType> split() {
    float newWidth = zone.width / 2;
    float newHeight = zone.height / 2;
    int newLevel = level + 1;
    regions = new QuadTree[4];
    regions[REGION_NW] = treePool.obtain().set(
            zone.x,
            zone.y + zone.height / 2,
            newWidth,
            newHeight,
            newLevel
    );

    regions[REGION_NE] = treePool.obtain().set(
            zone.x + zone.width / 2,
            zone.y + zone.height / 2,
            newWidth,
            newHeight,
            newLevel
    );

    regions[REGION_SW] = treePool.obtain().set(
            zone.x,
            zone.y,
            newWidth,
            newHeight,
            newLevel
    );

    regions[REGION_SE] = treePool.obtain().set(
            zone.x + zone.width / 2,
            zone.y,
            newWidth,
            newHeight,
            newLevel
    );
    return this;
  }

  /**
   * Insert new node into the tree
   * @param rectangle
   * @param element
   * @return
   */
  public QuadTree<QuadNodeElementType> insert(Rectangle rectangle, QuadNodeElementType element) {
    int region = this.findRegion(rectangle);

    if (region == REGION_SELF || this.level == maxLevel) {
      QuadNode<QuadNodeElementType> node = nodePool.obtain();
      node.set(rectangle);
      node.setElement(element);
      nodes.add(node);
      return this;
    } else {
      regions[region].insert(rectangle, element);
    }

    if (nodes.size >= maxItemByNode && this.level < maxLevel) {
      tempNodes.addAll(nodes);
      nodes.clear();
      for (QuadNode<QuadNodeElementType> node : tempNodes) {
        this.insert(node, node.getElement());
      }
      tempNodes.clear();
    }

    return this;
  }

  @Override
  public void dispose() {
    clear();
    tempNodes.clear();
    nodePool = null;
    treePool = null;
  }

  /**
   * Clear all nodes and sub trees
   */
  public void clear() {
    if (regions != null) {
      for (int i = 0; i < regions.length; i++) {
        regions[i].clear();
        treePool.free(regions[i]);
      }
    }

    regions = null;
    nodePool.freeAll(nodes);
    nodes.clear();
  }

  @Override
  public void reset() {
    clear();
    zone.set(0,0,1,1);
    regions = null;
    level = 0;
  }

  /**
   * Rectangle representing current zone
   * @return
   */
  public Rectangle getZone() {
    return zone;
  }

  /**
   * Find all elements that trees are in zone
   * @param list
   * @param zone
   * @return
   */
  public Array<QuadNodeElementType> getElements(Array<QuadNodeElementType> list, Rectangle zone) {
    int region = this.findRegion(zone);
    //TODO filter all elements after get elements, remove query from this method
    for (QuadNode<QuadNodeElementType> node : nodes) {
      if (node.overlaps(zone))
        list.add(node.getElement());
    }

    if (region != REGION_SELF) {
      regions[region].getElements(list, zone);
    } else {
      getAllElements(list, true);
    }

    return list;
  }

  /**
   * Get all elements from this node and sub nodes
   * @param list
   * @param firstCall
   * @return
   */
  public Array<QuadNodeElementType> getAllElements(Array<QuadNodeElementType> list, boolean firstCall) {
    if (regions != null) {
      regions[REGION_NW].getAllElements(list, false);
      regions[REGION_NE].getAllElements(list, false);
      regions[REGION_SW].getAllElements(list, false);
      regions[REGION_SE].getAllElements(list, false);
    }

    if (!firstCall) {
      for (QuadNode<QuadNodeElementType> node : nodes) {
        list.add(node.getElement());
      }
    }

    return list;
  }

  /**
   * Return all zones
   * @param out
   */
  public void getAllZones(Array<Rectangle> out) {
    out.add(this.zone);
    if (regions != null) {
      regions[REGION_NW].getAllZones(out);
      regions[REGION_NE].getAllZones(out);
      regions[REGION_SW].getAllZones(out);
      regions[REGION_SE].getAllZones(out);
    }
  }

  public Pool<QuadNode<QuadNodeElementType>> getNodePool() {
    return nodePool;
  }

  public Pool<QuadTree<QuadNodeElementType>> getTreePool() {
    return treePool;
  }
}
