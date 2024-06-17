package fr.utc.sr03.chatapp.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UserPublicDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @UserEmailUnique
    private String email;

    @NotNull
    @JsonProperty("isAdmin")
    private Boolean isAdmin;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp lastConnection;

    @JsonProperty("failedConnectionAttempts")
    private Integer failedConnectionAttempts;

    @NotNull
    @JsonProperty("isLocked")
    private Boolean isLocked;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp lockedAt;

    @NotNull
    @JsonProperty("chatrooms")
    private List<ChatroomWithoutUserDTO> chatrooms;

    @NotNull
    @JsonProperty("createdChatrooms")
    private List<ChatroomWithoutUserDTO> createdChatrooms;

    @NotNull
    @JsonProperty("updatedChatrooms")
    private List<ChatroomWithoutUserDTO> updatedChatrooms;

    public UserPublicDTO() {
        this.chatrooms = new ArrayList<>();
        this.createdChatrooms = new ArrayList<>();
        this.updatedChatrooms = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(final Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(final Timestamp lastConnection) {
        this.lastConnection = lastConnection;
    }

    public Integer getFailedConnectionAttempts() {
        return failedConnectionAttempts;
    }

    public void setFailedConnectionAttempts(final Integer failedConnectionAttempts) {
        this.failedConnectionAttempts = failedConnectionAttempts;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(final Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Timestamp getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(final Timestamp lockedAt) {
        this.lockedAt = lockedAt;
    }

    public List<ChatroomWithoutUserDTO> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(final List<ChatroomWithoutUserDTO> chatrooms) {
        this.chatrooms = chatrooms;
    }

    public void addChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.chatrooms.add(chatroom);
    }

    public void removeChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.chatrooms.remove(chatroom);
    }

    public List<ChatroomWithoutUserDTO> getCreatedChatrooms() {
        return createdChatrooms;
    }

    public void setCreatedChatrooms(final List<ChatroomWithoutUserDTO> createdChatrooms) {
        this.createdChatrooms = createdChatrooms;
    }

    public void addCreatedChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.createdChatrooms.add(chatroom);
    }

    public void removeCreatedChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.createdChatrooms.remove(chatroom);
    }

    public List<ChatroomWithoutUserDTO> getUpdatedChatrooms() {
        return updatedChatrooms;
    }

    public void setUpdatedChatrooms(final List<ChatroomWithoutUserDTO> updatedChatrooms) {
        this.updatedChatrooms = updatedChatrooms;
    }

    public void addUpdatedChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.updatedChatrooms.add(chatroom);
    }

    public void removeUpdatedChatroom(final ChatroomWithoutUserDTO chatroom) {
        this.updatedChatrooms.remove(chatroom);
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public Long getChatroomCount() {
        return Long.valueOf(this.chatrooms.size());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", lastConnection=" + lastConnection +
                ", failedConnectionAttempts=" + failedConnectionAttempts +
                ", isLocked=" + isLocked +
                ", lockedAt=" + lockedAt +
                ", chatrooms=" + chatrooms +
                ", createdChatrooms=" + createdChatrooms +
                ", updatedChatrooms=" + updatedChatrooms +
                '}';
    }

}
