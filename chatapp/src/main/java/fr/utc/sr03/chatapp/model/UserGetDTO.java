package fr.utc.sr03.chatapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class UserGetDTO {

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

    @NotNull
    @JsonProperty("isLocked")
    private Boolean isLocked;

    @NotNull
    @JsonProperty("chatrooms")
    private List<ChatroomWithoutUserDTO> chatrooms;

    public UserGetDTO() {
        this.chatrooms = new ArrayList<>();
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

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(final Boolean isLocked) {
        this.isLocked = isLocked;
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

    @Override
    public String toString() {
        return "UserGetDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", lastConnection=" + lastConnection +
                ", isLocked=" + isLocked +
                ", chatrooms=" + chatrooms +
                '}';
    }

}
