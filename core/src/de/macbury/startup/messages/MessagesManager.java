package de.macbury.startup.messages;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.Telegraph;

/**
 * Handle creating and receiving messages in game
 */
public class MessagesManager extends MessageDispatcher {

  public void dispatchMessage(MessageType type, Object payload) {
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
}
