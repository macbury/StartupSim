package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.*;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;

/**
 * Component for rendering simple sprite that is not animated
 */
public class SpriteComponent implements Component, Disposable, Pool.Poolable {
  public TextureRegion region;

  @Override
  public void dispose() {
    region = null;
  }

  @Override
  public void reset() {
    dispose();
  }

  public static class Blueprint extends ComponentBlueprint<SpriteComponent> {
    private String textureAtlasName;
    private String textureName;
    private TextureAtlas textureAtlas;
    private Texture texture;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {
      if (textureAtlasName == null) {
        dependencies.add(new AssetDescriptor(textureName, Texture.class));
      } else {
        dependencies.add(new AssetDescriptor(textureAtlasName, TextureAtlas.class));
      }
    }

    @Override
    public void assignDependencies(Assets assets) {
      if (textureAtlasName == null) {
        texture      = assets.get(textureName, Texture.class);
      } else {
        textureAtlas = assets.get(textureAtlasName, TextureAtlas.class);
      }
    }

    @Override
    public void applyTo(SpriteComponent component, Entity target) {
      if (textureAtlas != null) {
        component.region = textureAtlas.findRegion(textureName);
        if (component.region == null)
          throw new GdxRuntimeException("Not found region: " + textureName + " in " + textureAtlasName);
      } else if (texture != null) {
        component.region = new TextureRegion(texture);
      } else {
        throw new GdxRuntimeException("No texture had been loaded!");
      }
    }

    @Override
    public void load(JsonValue source, Json json) {
      textureAtlasName = source.getString("atlas");
      textureName      = source.getString("texture");
    }

    @Override
    public void save(Json target, SpriteComponent source) {
      throw new GdxRuntimeException("Implement this!");
    }

    @Override
    public void dispose() {
      textureAtlas = null;
      texture = null;
    }
  }
}
