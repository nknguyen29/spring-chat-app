package fr.utc.sr03.chatapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
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
    private Boolean isAdmin;

    // Use ManyToMany annotation on both sides of the relationship
    // It creates a join table to store the relationship
    // See: https://www.baeldung.com/jpa-many-to-many#2-implementation-in-jpa
    // @ManyToMany(targetEntity = Chatroom.class, mappedBy = "users")
    // private Set<Chatroom> chatrooms;

    // The join table is created upstream in the Chatroom entity
    // It contains the user and chatroom entities and additional information
    // about the relationship
    // Enable orphan removal to delete the chatroom when the user is deleted
    // @OneToMany(targetEntity = ChatroomUser.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
    @OneToMany(targetEntity = ChatroomUser.class, mappedBy = "user")
    private Set<ChatroomUser> chatroomUsers;

    public User() {
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

    public Set<ChatroomUser> getChatroomUsers() {
        return chatroomUsers;
    }

    public void setChatroomUsers(final Set<ChatroomUser> chatroomUsers) {
        this.chatroomUsers = chatroomUsers;
    }

}
