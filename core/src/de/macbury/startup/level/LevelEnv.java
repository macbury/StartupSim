package de.macbury.startup.level;

import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.CoreGame;
import de.macbury.startup.entities.EntityManager;
import de.macbury.startup.entities.systems.PathFindingSystem;
import de.macbury.startup.map.MapData;

/**
 * This model contains all information about everything in level
 */
public class LevelEnv implements Disposable {
  public final MapData mapData;
  public final EntityManager entities;

  public LevelEnv(CoreGame game) {
    mapData   = new MapData(100,100);
    entities  = new EntityManager(game);
    entities.addSystem(new PathFindingSystem(mapData, game.messages));

    for (int x = 1; x < 29; x++) {
      for (int y = 1; y < 29; y++) {
        mapData.build(x, y);
      }
    }


    for (int i = 0; i < 20; i++) {
      mapData.remove(i,4);
    }
  }

  /**
   * Run all level logic with rendering
   * @param delatTime
   */
  public void update(float delatTime) {
    this.entities.update(delatTime);
  }

  @Override
  public void dispose() {
    entities.dispose();
  }
}
