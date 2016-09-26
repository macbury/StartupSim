package de.macbury.startup.assets;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.HashMap;

/**
 * Maps special paths like :textures, :audio, :maps
 */
public class EngineFileHandleResolver implements FileHandleResolver {
  private static final String TAG = "EngineFileHandleResolver";
  private HashMap<String, String> pathMappings;

  public EngineFileHandleResolver() {
    pathMappings = new HashMap<String, String>();
  }

  public void setup() {
    putMapping("texture", "textures/");
    putMapping("model", "models/");
    putMapping("entity", "entities/");
    putMapping("terrain", "terrains/");
    putMapping("world", "worlds/");
    putMapping("charset", "charsets/");
    putMapping("behavior", "behaviors/");
  }

  private String applyMapping(String path) {
    for(String mapping : pathMappings.keySet()) {
      if (path.startsWith(mapping)) {
        return path.replace(mapping, pathMappings.get(mapping));
      }
    }
    return path;
  }

  private void putMapping(String key, String path) {
    pathMappings.put(key + ":", path);
    Gdx.app.log(TAG, "Mapping: " + key + " is " + path);
  }

  @Override
  public FileHandle resolve(String fileName) {
    return Gdx.files.internal(applyMapping(fileName));
  }
}