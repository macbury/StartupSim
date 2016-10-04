package de.macbury.startup.messages;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * All messages that are sended in system
 */
public enum MessageType {
  /**
   * Search path in background
   */
  RequestPathFinding(PathFinderRequest.class),
  Test, FindPath;

  /**
   * What type of payload is sended in Telegram
   */
  private final Class payloadType;

  MessageType() {
    payloadType = null;
  }

  MessageType(Class payloadType) {
    this.payloadType = payloadType;
  }

  /**
   * Check if payload is valid for {@link Telegram}
   * @param payload
   * @return
   */
  public boolean verifyPayload(Object payload) {
    return payloadType == null || payloadType.isInstance(payload);
  }

  /**
   * Returns Message type for {@link Telegram}. If payload is wrong then throws {@link InvalidPayloadError}
   * @param msg
   * @return
   */
  public static MessageType get(Telegram msg) {
    MessageType type = MessageType.values()[msg.message];
    if (!type.verifyPayload(msg.extraInfo)) {
      throw new InvalidPayloadError(msg, type);
    }
    return type;
  }

  public Class getPayloadType() {
    return payloadType;
  }

}
