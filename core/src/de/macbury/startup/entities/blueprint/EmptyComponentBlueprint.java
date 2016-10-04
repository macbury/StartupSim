package de.macbury.startup.entities.blueprint;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import de.macbury.startup.assets.Assets;

/**
 * Empty blueprint implementation
 */
public class EmptyComponentBlueprint<T extends Component> extends ComponentBlueprint<T> {
  @Override
  public void prepareDependencies(Array<AssetDescriptor> dependencies) {

  }

  @Override
  public void assignDependencies(Assets assets) {

  }

  @Override
  public void applyTo(T component, Entity target) {

  }

  @Override
  public void load(JsonValue source, Json json) {

  }

  @Override
  public void save(Json target, T source) {

  }

  @Override
  public void dispose() {

  }
}
