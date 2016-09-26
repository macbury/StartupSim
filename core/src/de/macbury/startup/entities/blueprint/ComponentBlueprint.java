package de.macbury.startup.entities.blueprint;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import de.macbury.startup.assets.Assets;

/**
 * This class is used as model for blueprints
 */
public abstract class ComponentBlueprint<T extends Component> implements Disposable {
  public Class<? extends Component> componentKlass;
  /**
   * Pass all dependencies that are needed to be loaded by {@link Assets}
   * @return
   */
  public abstract void prepareDependencies(Array<AssetDescriptor> dependencies);

  /**
   * Assign all dependencies from assets
   * @param assets
   */
  public abstract void assignDependencies(Assets assets);

  /**
   * Apply all blueprint configuration to component
   * @param component
   */
  public abstract void applyTo(T component, Entity target);

  /**
   * Loads all information from json to this blueprint class
   * @param source
   */
  public abstract void load(JsonValue source, Json json);

  /**
   * Save component values to blueprint(for save purpose)
   * @param target
   * @param json
   * @param source
   */
  public abstract void save(Json target, T source);
}