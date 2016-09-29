package de.macbury.startup.map.pfa;

import com.badlogic.gdx.ai.pfa.DefaultConnection;

/**
 * A connection for a {@link MapGraph}.
 */
public class TileConnection extends DefaultConnection<TileNode> {
  public TileConnection(TileNode fromNode, TileNode toNode) {
    super(fromNode, toNode);
  }

  @Override
  public float getCost() {
    return 1f;
  }
}
