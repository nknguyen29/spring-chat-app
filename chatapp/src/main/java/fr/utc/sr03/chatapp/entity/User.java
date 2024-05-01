package fr.utc.sr03.chatapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;
import java.util.stream.Collectors;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    // Use ManyToMany annotation on both sides of the relationship
    // It creates a join table to store the relationship
    // See: https://www.baeldung.com/jpa-many-to-many#2-implementation-in-jpa
    // @ManyToMany(targetEntity = Chatroom.class, mappedBy = "users")
    // private Set<Chatroom> chatrooms;

    // The join table is created upstream in the Chatroom entity
    // It contains the user and chatroom entities and additional information
    // about the relationship
    // Enable orphan removal to delete the chatroom when the user is deleted
    @OneToMany(targetEntity = ChatroomUser.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ChatroomUser> chatroomUsers;

    protected User() {
    }

    public User(String firstName, String lastName, String email, String password, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<ChatroomUser> getChatroomUsers() {
        return chatroomUsers;
    }

    public void addChatroomUser(ChatroomUser chatroomUser) {
        chatroomUsers.add(chatroomUser);
    }

    public void removeChatroomUser(ChatroomUser chatroomUser) {
        chatroomUsers.remove(chatroomUser);
    }

    public Set<Chatroom> getChatrooms() {
        return chatroomUsers.stream().map(ChatroomUser::getChatroom).collect(Collectors.toSet());
    }

    // Helper methods to manage the chatrooms
    // Need Cascade.MERGE and FetchType.EAGER to save the chatroom when the user
    // is saved
    public void addChatroom(Chatroom chatroom) {
        chatroomUsers.add(new ChatroomUser(chatroom, this));
    }

    public void removeChatroom(Chatroom chatroom) {
        chatroomUsers.removeIf(chatroomUser -> chatroomUser.getChatroom().equals(chatroom));
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return id.equals(user.id);
    }
}
