package de.macbury.startup.map.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.map.Tile;

/**
 * A node for {@link MapGraph}. It contains only necessary information required to build path
 */
public class TileNode implements Pool.Poolable {
  private static final int CONNECTIONS_CAPACITY = 4;
  public int x;
  public int y;
  public Tile tile;
  public int index;
  public Array<Connection<TileNode>> connections;

  public TileNode() {
    this.connections = new Array<Connection<TileNode>>(CONNECTIONS_CAPACITY);
    this.reset();
  }

  @Override
  public void reset() {
    tile = null;
    x = 0;
    y = 0;
    index = 0;
    connections.clear();
  }
}
