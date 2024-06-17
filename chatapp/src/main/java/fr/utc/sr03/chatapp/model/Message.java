package fr.utc.sr03.chatapp.model;

import jakarta.validation.constraints.NotNull;


public class Message {

    @NotNull
    private String content;

    @NotNull
    private Long sender; // For now it is the sender's ID only.
    // We may consider using the User object instead.

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(final Long sender) {
        this.sender = sender;
    }

}
