package fr.utc.sr03.chatapp.model;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ChatroomWithoutUserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp validityDuration;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(final Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getValidityDuration() {
        return validityDuration;
    }

    public void setValidityDuration(final Timestamp validityDuration) {
        this.validityDuration = validityDuration;
    }

    @Override
    public String toString() {
        return "ChatroomWithoutUserDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", validityDuration=" + validityDuration +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":" + id +
                ",\"title\":\"" + title + '\"' +
                ",\"description\":\"" + description + '\"' +
                ",\"startDate\":\"" + startDate + '\"' +
                ",\"validityDuration\":\"" + validityDuration + '\"' +
                '}';
    }

}
