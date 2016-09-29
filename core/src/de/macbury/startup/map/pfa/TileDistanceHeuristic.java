package de.macbury.startup.map.pfa;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.MathUtils;

/**
 * A Manhattan distance heuristic for a {@link MapGraph}. It simply calculates the Manhattan distance between two given
 * tiles.
 */
public class TileDistanceHeuristic implements Heuristic<TileNode> {
  @Override
  public float estimate(TileNode node, TileNode endNode) {
    return Math.abs(endNode.x - node.x) + Math.abs(endNode.y - node.y);
  }
}
