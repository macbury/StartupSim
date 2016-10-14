package de.macbury.startup.level;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.CoreGame;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.EntityManager;
import de.macbury.startup.entities.systems.*;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.quadtree.EntityQuadTree;
import de.macbury.startup.map.quadtree.QuadTree;
import de.macbury.startup.messages.MessagesManager;

/**
 * This model contains all information about everything in level
 */
public class LevelEnv implements Disposable {
  public final MapData mapData;
  public final EntityManager entities;
  public final OrthographicCamera camera;
  public final MapGraph mapGraph;
  public final EntityQuadTree tree;
  public Assets assets;
  public MessagesManager messages;

  public LevelEnv(CoreGame game) {
    tree              = new EntityQuadTree(0, 0, 50, 50);
    messages          = game.messages;
    assets            = game.assets;
    camera            = new OrthographicCamera();
    mapData           = new MapData(50,50);
    mapGraph          = new MapGraph(mapData);
    entities          = new EntityManager(game);

    for (int x = 1; x < 29; x++) {
      for (int y = 1; y < 29; y++) {
        mapData.build(x, y);
      }
    }

    for (int x = 31; x < 40; x++) {
      for (int y = 1; y < 40; y++) {
        mapData.build(x, y);
      }
    }
    mapData.build(29, 20);
    mapData.build(30, 20);
    mapData.build(31, 20);

    for (int x = 41; x < 45; x++) {
      for (int y = 1; y < 40; y++) {
        mapData.build(x, y);
      }
    }

    mapData.build(39, 3);
    mapData.build(40, 3);
    mapData.build(41, 3);

    for (int i = 0; i < 20; i++) {
      mapData.remove(i,4);
    }

    entities.addSystem(new QuadTreeSystem(tree));
    entities.addSystem(new RateLimitSystem());
    entities.addSystem(new RefLevelSystem(this));
    entities.addSystem(new ProgrammerSystem());
    entities.addSystem(new PathFindingSystem(mapGraph, mapData, game.messages));
    entities.addSystem(new MovementSystem());
    entities.addSystem(new RenderingSystem(camera));
  }

  /**
   * Run all level logic with rendering
   * @param deltaTime
   */
  public void update(float deltaTime) {
    camera.update();
    this.entities.update(deltaTime);
  }

  @Override
  public void dispose() {
    tree.dispose();
    entities.dispose();
    mapGraph.dispose();
    assets   = null;
    messages = null;
  }
}
