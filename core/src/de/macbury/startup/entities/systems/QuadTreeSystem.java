package de.macbury.startup.entities.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import de.macbury.startup.entities.components.PositionComponent;
import de.macbury.startup.entities.helpers.Components;
import de.macbury.startup.map.quadtree.QuadTree;

/**
 * Refresh {@link de.macbury.startup.level.LevelEnv#tree} with all {@link Entity} that are in game
 */
public class QuadTreeSystem extends IteratingSystem implements Disposable {
  private final Rectangle tempRectangle;
  private QuadTree<Entity> tree;

  public QuadTreeSystem(QuadTree<Entity> tree) {
    super(Family.all(PositionComponent.class).get());
    this.tree = tree;
    this.tempRectangle = new Rectangle();
  }

  @Override
  public void update(float deltaTime) {
    tree.clear();
    super.update(deltaTime);
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    PositionComponent position = Components.Position.get(entity);
    tempRectangle.setPosition(position);
    tempRectangle.setSize(1,1);
    tree.insert(tempRectangle, entity);
  }

  @Override
  public void dispose() {
    tree = null;
  }
}
