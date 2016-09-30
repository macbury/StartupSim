package de.macbury.startup.map.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.map.Tile;

/**
 * A node for {@link MapGraph}. It contains only necessary information required to build path
 */
public class TileNode {
  private static final int CONNECTIONS_CAPACITY = 4;
  public int x;
  public int y;
  /**
   * Reference to tile and can be nil
   */
  public Tile tile;
  public int index;
  public final Array<Connection<TileNode>> connections;

  public TileNode(int x, int y, int index, Tile tile) {
    this.connections = new Array<Connection<TileNode>>(CONNECTIONS_CAPACITY);
    this.x           = x;
    this.y           = y;
    this.tile        = tile;
    this.index       = index;
  }

}
