package fr.utc.sr03.chatapp.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.io.Serializable;

@Embeddable
public class ChatroomUserKey implements Serializable {

    @Column(name = "chatroom_id")
    private Long chatroomId;

    @Column(name = "user_id")
    private Long userId;

    public ChatroomUserKey() {
    }

    public ChatroomUserKey(Long chatroomId, Long userId) {
        this.chatroomId = chatroomId;
        this.userId = userId;
    }

    public Long getChatroomId() {
        return chatroomId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ChatroomUserKey that = (ChatroomUserKey) o;
        return chatroomId.equals(that.chatroomId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return chatroomId.hashCode() + userId.hashCode();
    }

}

// Note that a composite key class has to fulfill some key requirements:
// - We have to mark it with @Embeddable.
// - It has to implement java.io.Serializable.
// - We need to provide an implementation of the hashcode() and equals()
// methods.
//
// See
// https://www.baeldung.com/jpa-many-to-many#many-to-many-using-a-composite-key
// for more information on composite keys.
