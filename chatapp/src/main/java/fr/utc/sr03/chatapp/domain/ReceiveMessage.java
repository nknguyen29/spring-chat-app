package fr.utc.sr03.chatapp.domain;

public class ReceiveMessage {

  private String content;

  public ReceiveMessage() {
  }

  public ReceiveMessage(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

}
