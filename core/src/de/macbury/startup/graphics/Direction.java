package de.macbury.startup.graphics;

import com.badlogic.gdx.math.Vector2;

/**
 * Direction of movement
 */
public enum Direction {
  Down,
  Up,
  Left,
  Right;

  public static Direction from(Vector2 direction) {
    if (direction.x > 0) {
      return Left;
    } else if (direction.x < 0) {
      return Right;
    } else if (direction.y < 0) {
      return Up;
    } else {
      return Down;
    }
  }
}
