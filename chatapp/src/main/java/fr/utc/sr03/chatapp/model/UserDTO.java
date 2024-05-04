package fr.utc.sr03.chatapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

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
    @Size(max = 255)
    private String password;

    @NotNull
    @JsonProperty("isAdmin")
    private Boolean isAdmin;

    @NotNull
    @JsonProperty("chatrooms")
    private List<ChatroomWithoutUserDTO> chatrooms;

    public UserDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(final Boolean isAdmin) {
        this.isAdmin = isAdmin;
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

}
