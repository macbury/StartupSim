package de.macbury.startup.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Logger;
import de.macbury.startup.CoreGame;
import de.macbury.startup.entities.blueprint.EntityBlueprint;
import de.macbury.startup.entities.blueprint.EntityBlueprintLoader;

/**
 * This class wraps {@link AssetManager} and extends it with proper features required by game and automaticaly map asssets to path
 */
public class Assets extends AssetManager {

  public Assets(FileHandleResolver resolver, CoreGame game) {
    super(resolver);
    setLoader(EntityBlueprint.class, new EntityBlueprintLoader(resolver));
    getLogger().setLevel(Logger.DEBUG);
  }
}