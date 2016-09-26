package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;
import de.macbury.startup.graphics.CharsetAnimation;
import de.macbury.startup.graphics.Direction;

/**
 * Stores animations for component
 */
public class CharsetAnimationComponent extends CharsetAnimation implements Component, Pool.Poolable {
  /**
   * Accumulated time for current direction
   */
  public float stateTime = 0;

  @Override
  public void reset() {
    stateTime = 0;
    dispose();
  }

  public TextureRegion getKeyFrame(Direction direction) {
    return getKeyFrame(stateTime, direction);
  }

  public static class Blueprint extends ComponentBlueprint<CharsetAnimationComponent> {
    public String name;
    public Texture texture;
    private CharsetAnimation charsetAnimation;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {
      AssetDescriptor modelAssetDescriptor = new AssetDescriptor(name, Texture.class);
      dependencies.add(modelAssetDescriptor);
    }

    @Override
    public void assignDependencies(Assets assets) {
      texture           = assets.get(name);
      charsetAnimation  = new CharsetAnimation(texture);
    }

    @Override
    public void applyTo(CharsetAnimationComponent component, Entity target) {
      component.set(charsetAnimation);
    }

    @Override
    public void load(JsonValue source, Json json) {
      name = source.getString("name");
    }

    @Override
    public void save(Json target, CharsetAnimationComponent source) {
      target.writeValue("name", name);
    }

    @Override
    public void dispose() {
      charsetAnimation.dispose();
      texture = null;
    }
  }
}
