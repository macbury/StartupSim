package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.Programmer;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;

/**
 * Created by macbury on 24.09.16.
 */
public class ProgrammerComponent implements Component, Pool.Poolable {
  public BehaviorTree<Entity> tree;
  public int hunger;

  public boolean isHungry() {
    return hunger > 200;
  }

  @Override
  public void reset() {
    tree = null;
  }

  public static class Blueprint extends ComponentBlueprint<ProgrammerComponent> {
    private String treeName;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {

    }

    @Override
    public void assignDependencies(Assets assets) {

    }

    @Override
    public void applyTo(ProgrammerComponent component, Entity target) {
      component.tree = BehaviorTreeLibraryManager.getInstance().createBehaviorTree(treeName, target);
    }

    @Override
    public void load(JsonValue source, Json json) {
      treeName = source.getString("tree");
    }

    @Override
    public void save(Json target, ProgrammerComponent source) {
      target.writeValue("tree", source.tree);
    }

    @Override
    public void dispose() {

    }
  }
}
