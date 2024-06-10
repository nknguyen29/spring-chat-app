package fr.utc.sr03.chatapp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ChatroomPublicDTO {

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

    @NotNull
    @JsonProperty("createdAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp createdAt;

    @NotNull
    @JsonProperty("createdBy")
    private UserWithoutChatroomDTO createdBy;

    @JsonProperty("updatedAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp updatedAt;

    @JsonProperty("updatedBy")
    private UserWithoutChatroomDTO updatedBy;

    @NotNull
    @JsonProperty("users")
    private List<UserPublicWithoutChatroomDTO> users;

    public ChatroomPublicDTO() {
        this.users = new ArrayList<>();
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public UserWithoutChatroomDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final UserWithoutChatroomDTO createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserWithoutChatroomDTO getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final UserWithoutChatroomDTO updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<UserPublicWithoutChatroomDTO> getUsers() {
        return users;
    }

    public void setUsers(final List<UserPublicWithoutChatroomDTO> users) {
        this.users = users;
    }

    public void addUser(final UserPublicWithoutChatroomDTO user) {
        this.users.add(user);
    }

    public void removeUser(final UserPublicWithoutChatroomDTO user) {
        this.users.remove(user);
    }

    @Override
    public String toString() {
        return "ChatroomDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", validityDuration=" + validityDuration +
                ", createdAt=" + createdAt +
                ", createdBy=" + createdBy +
                ", updatedAt=" + updatedAt +
                ", updatedBy=" + updatedBy +
                ", users=" + users +
                '}';
    }

}
