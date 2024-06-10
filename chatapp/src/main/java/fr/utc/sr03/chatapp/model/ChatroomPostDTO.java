package fr.utc.sr03.chatapp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ChatroomPostDTO {

    @JsonIgnore
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

    @JsonProperty("createdById")
    private Long createdById;

    @NotNull
    @Size(min = 1)
    @JsonProperty("userIds")
    private List<Long> userIds;

    public ChatroomPostDTO() {
        this.userIds = new ArrayList<>();
    }

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

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(final Long createdById) {
        this.createdById = createdById;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(final List<Long> userIds) {
        this.userIds = userIds;
    }

    public void addUserId(final Long userId) {
        this.userIds.add(userId);
    }

    public void removeUserId(final Long userId) {
        this.userIds.remove(userId);
    }

    @Override
    public String toString() {
        return "ChatroomPostDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", validityDuration=" + validityDuration +
                ", createdById=" + createdById +
                ", userIds=" + userIds +
                '}';
    }

}
