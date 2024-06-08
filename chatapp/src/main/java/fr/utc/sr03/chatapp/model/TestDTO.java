package fr.utc.sr03.chatapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TestDTO {

    @JsonProperty("message")
    private String message;

    public TestDTO() {
    }

    public TestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
