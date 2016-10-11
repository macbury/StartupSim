package de.macbury.startup.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Pool;
import de.macbury.startup.assets.Assets;
import de.macbury.startup.entities.blueprint.ComponentBlueprint;

/**
 * Show current bubble above entity
 */
public class NotificationCloudComponent implements Component, Pool.Poolable {
  private TextureAtlas notificationsTextureAtlas;

  public enum Notification {
    None,
    NoFoodFound,
    NoRouteFound,
    ObstalceFound
  }

  public Notification current;

  @Override
  public void reset() {
    current = Notification.None;
  }

  /**
   * Should show current
   * @return
   */
  public boolean isActive() {
    return current != Notification.None;
  }

  /**
   * Return texture region for current current
   * @return
   */
  public TextureRegion getNotificationRegion() {
    return notificationsTextureAtlas.findRegion(current.name());
  }


  public static class Blueprint extends ComponentBlueprint<NotificationCloudComponent> {
    private static final String TEXTURE_ATLAS_NOTIFICATIONS = "atlas:notifications.atlas";
    private TextureAtlas notificationsTextureAtlas;

    @Override
    public void prepareDependencies(Array<AssetDescriptor> dependencies) {
      dependencies.add(new AssetDescriptor(TEXTURE_ATLAS_NOTIFICATIONS, TextureAtlas.class));
    }

    @Override
    public void assignDependencies(Assets assets) {
      notificationsTextureAtlas = assets.get(TEXTURE_ATLAS_NOTIFICATIONS);
    }

    @Override
    public void applyTo(NotificationCloudComponent component, Entity target) {
      component.notificationsTextureAtlas = notificationsTextureAtlas;
    }

    @Override
    public void load(JsonValue source, Json json) {

    }

    @Override
    public void save(Json target, NotificationCloudComponent source) {

    }

    @Override
    public void dispose() {
      notificationsTextureAtlas = null;
    }
  }
}
