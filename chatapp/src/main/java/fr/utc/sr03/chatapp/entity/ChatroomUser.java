package fr.utc.sr03.chatapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
@Table(name = "chatroom_users")
public class ChatroomUser {
    @EmbeddedId
    private ChatroomUserKey id;

    @ManyToOne(targetEntity = Chatroom.class, optional = false)
    @MapsId("chatroomId")
    private Chatroom chatroom;

    @ManyToOne(targetEntity = User.class, optional = false)
    @MapsId("userId")
    private User user;

    public ChatroomUser() {
    }

    public ChatroomUser(Chatroom chatroom, User user) {
        this.chatroom = chatroom;
        this.user = user;
        this.id = new ChatroomUserKey(chatroom.getId(), user.getId());
    }

    public ChatroomUserKey getId() {
        return id;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChatroomUser that = (ChatroomUser) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ChatroomUser{" +
                "id=" + id +
                ", chatroom=" + chatroom +
                ", user=" + user +
                '}';
    }
}
