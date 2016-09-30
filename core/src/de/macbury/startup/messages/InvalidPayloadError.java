package de.macbury.startup.messages;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class InvalidPayloadError extends GdxRuntimeException {
  public InvalidPayloadError(Telegram telegram, MessageType type) {
    this(telegram.extraInfo, type);
  }

  public InvalidPayloadError(Object payload, MessageType type) {
    super("Payload of " + type.toString() + " should be " + type.getPayloadType().toString() + " but was "+ payload);
  }
}
