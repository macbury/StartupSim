package de.macbury.startup.map.pfa;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.Tile;

/**
 * This graph represents graph of all {@link Tile} and its connections
 */
public class MapGraph implements IndexedGraph<TileNode>, Disposable {
  private final Array<TileNode> nodes;
  private long lastChange;
  private MapData mapData;


  public MapGraph(MapData mapData) {
    this.mapData = mapData;
    this.nodes   = new Array<TileNode>(mapData.getColumns() * mapData.getRows());
    this.lastChange = -1;
  }

  @Override
  public int getIndex(TileNode node) {
    return node.index;
  }

  public int getIndex(int x, int y) {
    return x * mapData.getRows() + y;
  }

  public TileNode getNode(int x, int y) {
    return nodes.get(getIndex(x,y));
  }

  @Override
  public int getNodeCount() {
    return nodes.size;
  }

  @Override
  public Array<Connection<TileNode>> getConnections(TileNode fromNode) {
    return fromNode.connections;
  }

  /**
   * Check if {@link MapData} did change and rebuild graph
   */
  public boolean rebuildIfNeed() {
    if (lastChange != mapData.getChanges()) {
      lastChange = mapData.getChanges();
      rebuild();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Rebuild graph connections and nodes
   */
  public void rebuild() {
    nodes.clear();

    /**
     * Build nodes
     */
    for (int x = 0; x < mapData.getColumns(); x++) {
      for (int y = 0; y < mapData.getRows(); y++) {
        TileNode node = new TileNode(x, y, getIndex(x,y), mapData.get(x,y));
        nodes.add(node);
      }
    }

    /**
     * Connect only nodes that are not diagonal and are passable
     */
    for (int x = 0; x < mapData.getColumns(); x++) {
      for (int y = 0; y < mapData.getRows(); y++) {
        TileNode rootNode = getNode(x,y);

        if (x > 0) addConnection(rootNode, -1, 0);
        if (y > 0) addConnection(rootNode, 0, -1);
        if (x < mapData.getColumns() - 1) addConnection(rootNode, 1, 0);
        if (y < mapData.getRows() - 1) addConnection(rootNode, 0, 1);
      }
    }
  }

  /**
   * If target node in offset is passable then build connection to it
   * @param rootNode
   * @param xOffset
   * @param yOffset
   */
  private void addConnection (TileNode rootNode, int xOffset, int yOffset) {
    TileNode targetNode = getNode(rootNode.x + xOffset, rootNode.y + yOffset);
    if (targetNode.tile != null) { //TODO check here if node is passable
      rootNode.connections.add(new TileConnection(rootNode, targetNode));
    }
  }

  @Override
  public void dispose() {
    nodes.clear();
    mapData = null;
  }
}
