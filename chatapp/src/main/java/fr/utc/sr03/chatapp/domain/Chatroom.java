package fr.utc.sr03.chatapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import java.util.Set;
// La classe java.sql.Timestamp étend la classe java.util.Date et permet de
// stocker des informations de date et d'heure.
import java.sql.Timestamp; // or java.util.Date

@Entity
@Table(name = "chatrooms")
public class Chatroom {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "\"description\"")
    private String description;

    @Column(nullable = false)
    private Timestamp startDate;

    @Column(nullable = false)
    private Timestamp validityDuration;

    // @ManyToMany(targetEntity = User.class)
    // @JoinTable(
    //     name = "chatroom_users",
    //     joinColumns = @JoinColumn(name = "chatroom_id"),
    //     inverseJoinColumns = @JoinColumn(name = "user_id"))
    // private Set<User> users;

    // orphanRemoval = true to delete the chatroom when the user is deleted
    // @OneToMany(targetEntity = ChatroomUser.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "chatroom")
    @OneToMany(targetEntity = ChatroomUser.class, mappedBy = "chatroom")
    private Set<ChatroomUser> chatroomUsers;

    public Chatroom() {
    }

    public Chatroom(String title, String description, Timestamp startDate, Timestamp validityDuration) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.validityDuration = validityDuration;
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

    public Set<ChatroomUser> getChatroomUsers() {
        return chatroomUsers;
    }

    public void setChatroomUsers(final Set<ChatroomUser> chatroomUsers) {
        this.chatroomUsers = chatroomUsers;
    }

}
