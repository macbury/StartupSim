package de.macbury.startup.entities.blueprint;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.*;
import de.macbury.startup.assets.Assets;

/**
 * Loads blueprint and all its dependencies
 */
public class EntityBlueprintLoader extends SynchronousAssetLoader<EntityBlueprint, EntityBlueprintLoader.BlueprintParameter> {
  private final JsonReader jsonReader;
  private final Json json;
  private Array<ComponentBlueprint> componentBlueprints;

  public EntityBlueprintLoader(FileHandleResolver resolver) {
    super(resolver);

    this.jsonReader = new JsonReader();
    this.json       = new Json();
  }

  @Override
  public EntityBlueprint load(AssetManager assetManager, String fileName, FileHandle file, BlueprintParameter parameter) {
    for (ComponentBlueprint blueprint : componentBlueprints) {
      blueprint.assignDependencies((Assets)assetManager);
    }
    EntityBlueprint entityBlueprint = new EntityBlueprint(componentBlueprints);
    componentBlueprints.clear();
    return entityBlueprint;
  }

  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BlueprintParameter parameter) {
    Array<AssetDescriptor> deps                   = new Array<AssetDescriptor>();
    this.componentBlueprints                      = new Array<ComponentBlueprint>();
    JsonValue blueprintRoot                       = jsonReader.parse(file);
    Json json                                     = new Json();
    for (JsonValue jsonBlueprint : blueprintRoot) {
      String componentSimpleNamePart = jsonBlueprint.name();
      try {
        Class<ComponentBlueprint> blueprintKlassToLoad = (Class<ComponentBlueprint>)Class.forName(getComponentKlassName(componentSimpleNamePart) + "$Blueprint");
        Class<Component> componentKlass                = (Class<Component>)Class.forName(getComponentKlassName(componentSimpleNamePart));
        ComponentBlueprint blueprint                   = blueprintKlassToLoad.newInstance();
        blueprint.load(jsonBlueprint, json);
        blueprint.componentKlass                       = componentKlass;
        blueprint.prepareDependencies(deps);

        componentBlueprints.add(blueprint);
      } catch (ClassNotFoundException e) {
        throw new GdxRuntimeException(e);
      } catch (InstantiationException e) {
        throw new GdxRuntimeException(e);
      } catch (IllegalAccessException e) {
        throw new GdxRuntimeException(e);
      }
    }
    return deps;
  }

  private String getComponentKlassName(String simpleName) {
    return "de.macbury.startup.entities.components." + simpleName + "Component";
  }

  static public class BlueprintParameter extends AssetLoaderParameters<EntityBlueprint> {
  }
}