package de.macbury.startup.messages;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import de.macbury.startup.entities.behaviors.EntityTask;

/**
 * Handle creating and receiving messages in game
 */
public class MessagesManager extends MessageDispatcher {
  /** Sends an immediate message to all registered listeners, with extra info.
   * <p>
   * This is a shortcut method for {@link #dispatchMessage(float, Telegraph, Telegraph, int, Object, boolean) dispatchMessage(0,
   * null, null, msg, extraInfo, false)}
   *
   * @param type the message code
   * @param payload an optional object */
  public void dispatchMessage(MessageType type, Object payload) {
    if (!type.verifyPayload(payload))
      throw new InvalidPayloadError(payload, type);
    dispatchMessage(type.ordinal(), payload);
  }

  public void addListener(Telegraph listener, MessageType ...types) {
    for (MessageType type: types) {
      addListener(listener, type.ordinal());
    }
  }

  public void removeListener(Telegraph listener, MessageType ...types) {
    for (MessageType type: types) {
      removeListener(listener, type.ordinal());
    }
  }

  /** Sends an immediate message to all registered listeners, with extra info.
   * <p>
   * This is a shortcut method for {@link #dispatchMessage(float, Telegraph, Telegraph, int, Object, boolean) dispatchMessage(0,
   * sender, null, msg, extraInfo, false)}
   *
   * @param sender the sender of the telegram
   * @param type the message code
   * @param payload an optional object */
  public void dispatchMessage(Telegraph sender, MessageType type, Object payload) {
    if (!type.verifyPayload(payload))
      throw new InvalidPayloadError(payload, type);
    dispatchMessage(sender, type.ordinal(), payload);
  }
}
