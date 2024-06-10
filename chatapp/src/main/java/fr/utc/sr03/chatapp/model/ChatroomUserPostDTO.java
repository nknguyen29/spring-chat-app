package fr.utc.sr03.chatapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ChatroomUserPostDTO {

    @NotNull
    @JsonProperty("chatroom")
    private ChatroomPostDTO chatroomDTO;

    @NotNull
    @Size(min = 1)
    @JsonProperty("userIds")
    private List<Long> userIds;

    public ChatroomUserPostDTO() {
        this.userIds = new ArrayList<>();
    }

    public ChatroomPostDTO getChatroom() {
        return chatroomDTO;
    }

    public void setChatroom(final ChatroomPostDTO chatroomDTO) {
        this.chatroomDTO = chatroomDTO;
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

}
