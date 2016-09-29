package de.macbury.startup.messages;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.utils.GdxRuntimeException;


public class InvalidPayloadError extends GdxRuntimeException {
  public InvalidPayloadError(Telegram telegram, MessageType type) {
    super("Payload of " + type.toString() + " should be " + type.getPayloadType().toString() + " but was "+ telegram.extraInfo);
  }
}
