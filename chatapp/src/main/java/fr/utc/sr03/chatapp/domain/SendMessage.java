package fr.utc.sr03.chatapp.domain;

public class SendMessage {

  private String name;

  public SendMessage() {
  }

  public SendMessage(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}