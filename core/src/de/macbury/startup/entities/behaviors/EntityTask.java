package de.macbury.startup.entities.behaviors;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.GdxRuntimeException;
import de.macbury.startup.entities.EntityManager;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.level.LevelEnv;
import de.macbury.startup.map.MapData;
import de.macbury.startup.map.pfa.MapGraph;
import de.macbury.startup.map.quadtree.EntityQuadTree;
import de.macbury.startup.messages.MessageType;
import de.macbury.startup.messages.MessagesManager;

/**
 * Base Entity task with helper functions like for broadcasting messages
 */
public abstract class EntityTask extends LeafTask<Entity> implements Telegraph {

  protected LevelEnv getLevelEnv() {
    if (Components.LevelEnv.has(getObject())) {
      return Components.LevelEnv.get(getObject()).env;
    } else {
      throw new GdxRuntimeException("Entity: " + getObject().toString() + " dont have LevelEnv component!!!!!");
    }
  }

  /**
   * Reference to {@link LevelEnv#mapData}
   * @return
   */
  protected MapData getMapData() {
    return getLevelEnv().mapData;
  }

  /**
   * Reference to {@link LevelEnv#mapGraph}
   * @return
   */
  protected MapGraph getMapGraph() {
    return getLevelEnv().mapGraph;
  }

  /**
   * Reference to {@link LevelEnv#messages}
   * @return
   */
  protected MessagesManager getMessages() {
    return getLevelEnv().messages;
  }

  /**
   * Reference to {@link LevelEnv#entities}
   * @return
   */
  protected EntityManager getEntities() {
    return getLevelEnv().entities;
  }
  /**
   * Reference to {@link LevelEnv#tree}
   * @return
   */
  protected EntityQuadTree getTree() {
    return getLevelEnv().tree;
  }
  /**
   * Send message
   * @param type
   * @param payload
   */
  protected void dispatch(MessageType type, Object payload) {
    getMessages().dispatchMessage(this, type, payload);
  }

  protected void dispatch(MessageType type) {
    dispatch(type, null);
  }
}
