package de.macbury.startup.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * This class splits texture charset in texture regions and build walkable animations
 */
public class CharsetAnimation implements Disposable {
  public static final int NUMBER_OF_WALK_FRAMES = 4;
  private static final float WALK_FRAME_TIME = 0.15f;
  private static final int CHARSET_FRAME_SIZE = 16;

  private ObjectMap<Direction, Animation> animations;

  public CharsetAnimation() {
    animations = new ObjectMap<Direction, Animation>();
  }

  public CharsetAnimation(Texture texture) {
    this();
    build(texture);
  }

  /**
   * Build animations from texture
   * @param texture
   */
  private void build(Texture texture) {
    TextureRegion[][] frames = TextureRegion.split(texture, CHARSET_FRAME_SIZE, CHARSET_FRAME_SIZE);
    for (Direction direction : Direction.values()) {
      Array<TextureRegion> walkFrames = new Array<TextureRegion>(NUMBER_OF_WALK_FRAMES);
      for (int row = 0; row < NUMBER_OF_WALK_FRAMES; row++) {
        walkFrames.add(frames[row][direction.ordinal()]);
      }
      Animation animation = new Animation(WALK_FRAME_TIME, walkFrames, Animation.PlayMode.LOOP);
      animations.put(direction, animation);
    }
  }

  /**
   * Get key frame for direction
   * @param stateTime
   * @param direction
   * @return
   */
  public TextureRegion getKeyFrame(float stateTime, Direction direction) {
    return animations.get(direction).getKeyFrame(stateTime, true);
  }

  @Override
  public void dispose() {
    animations = null;
  }
}
