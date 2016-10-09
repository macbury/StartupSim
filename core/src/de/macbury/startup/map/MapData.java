package de.macbury.startup.map;

import com.badlogic.gdx.utils.Disposable;

/**
 * This class contains all information about content of current map like: walls, doors, interior content
 */
public class MapData implements Disposable {
  private final Tile[][] tiles;
  private final int columns;
  private final int rows;
  /**
   * Did map data change
   */
  private boolean dirty;
  /**
   * Number of changes in this map
   */
  private long changes;

  public MapData(int columns, int rows) {
    this.tiles   = new Tile[columns][rows];
    this.columns = columns;
    this.rows    = rows;
    changes      = 0;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public Tile get(int x, int y) {
    return tiles[x][y];
  }

  public void remove(int x, int y) {
    tiles[x][y] = null;
    changes++;
  }

  public Tile build(int x, int y) {
    Tile tile   = new Tile(x,y);
    tiles[x][y] = tile;
    changes++;
    return tile;
  }

  public void set(int x, int y, Tile value) {
    tiles[x][y] = value;
    changes++;
  }

  @Override
  public void dispose() {
    for (int x = 0; x < columns; x++) {
      for (int y = 0; y < rows; y++) {
        remove(x,y);
      }
    }
  }

  public boolean isNotEmpty(int x, int y) {
    return tiles[x][y] != null;
  }

  public boolean isEmpty(int x, int y) {
    return tiles[x][y] == null;
  }

  /**
   * Number of changes in MapData
   * @return
   */
  public long getChanges() {
    return changes;
  }

  public boolean isPassable(int x, int y) {
    return isNotEmpty(x,y);
  }
}
