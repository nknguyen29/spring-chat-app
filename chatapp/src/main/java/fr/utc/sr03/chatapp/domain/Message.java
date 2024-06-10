package fr.utc.sr03.chatapp.domain;

public class Message {

    private String content;
    private Long sender; // For now it is the sender's ID only
    // string or long better ?

    public Message() {
    }
  
    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public Long getSender() {
      return sender;
    }

    public void setSender(Long sender) {
      this.sender = sender;
    }
}
