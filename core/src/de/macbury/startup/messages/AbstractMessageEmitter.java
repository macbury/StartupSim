package de.macbury.startup.messages;

/**
 * Base class for emitters of messages
 */
public abstract class AbstractMessageEmitter {
  protected final MessagesManager messages;

  public AbstractMessageEmitter(MessagesManager messages) {
    this.messages = messages;
  }
}
