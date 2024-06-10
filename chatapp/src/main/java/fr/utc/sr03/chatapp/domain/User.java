package fr.utc.sr03.chatapp.domain;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


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

    @Column(nullable = false)
    private Timestamp createdAt;

    @Column(name = "\"lastConnection\"")
    private Timestamp lastConnection;

    @Column(name = "\"failedConnectionAttempts\"")
    private Integer failedConnectionAttempts;

    @Column(nullable = false)
    private Boolean isLocked;

    @Column(name = "\"lockedAt\"")
    private Timestamp lockedAt;

    @OneToMany(targetEntity = Token.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Token> tokens;

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

    @OneToMany(targetEntity = Chatroom.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "createdBy")
    private Set<Chatroom> createdChatrooms;

    @OneToMany(targetEntity = Chatroom.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "updatedBy")
    private Set<Chatroom> updatedChatrooms;

    public User() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(
        String firstName, String lastName, String email, String password,
        boolean isAdmin, Timestamp createdAt, Timestamp lastConnection,
        Integer failedConnectionAttempts, Boolean isLocked, Timestamp lockedAt
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.createdAt = createdAt;
        this.lastConnection = lastConnection;
        this.failedConnectionAttempts = failedConnectionAttempts;
        this.isLocked = isLocked;
        this.lockedAt = lockedAt;
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

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(final Set<Token> tokens) {
        this.tokens = tokens;
    }

    public void addToken(final Token token) {
        tokens.add(token);
    }

    public void removeToken(final Token token) {
        tokens.remove(token);
    }

    public Set<ChatroomUser> getChatroomUsers() {
        return chatroomUsers;
    }

    public void setChatroomUsers(final Set<ChatroomUser> chatroomUsers) {
        this.chatroomUsers = chatroomUsers;
    }

    public void addChatroomUser(final ChatroomUser chatroomUser) {
        chatroomUsers.add(chatroomUser);
    }

    public void removeChatroomUser(final ChatroomUser chatroomUser) {
        chatroomUsers.remove(chatroomUser);
    }

    public Set<Chatroom> getChatrooms() {
        return chatroomUsers.stream()
                .map(ChatroomUser::getChatroom).collect(Collectors.toSet());
    }

    // Helper methods to manage the chatrooms
    // Need Cascade.MERGE and FetchType.EAGER to save the chatroom when the user
    // is saved
    public void addChatroom(final Chatroom chatroom) {
        chatroomUsers.add(new ChatroomUser(chatroom, this));
    }

    public void removeChatroom(final Chatroom chatroom) {
        chatroomUsers.removeIf(chatroomUser -> chatroomUser.getChatroom().equals(chatroom));
    }

    public Set<Chatroom> getCreatedChatrooms() {
        return createdChatrooms;
    }

    public void setCreatedChatrooms(final Set<Chatroom> createdChatrooms) {
        this.createdChatrooms = createdChatrooms;
    }

    public void addCreatedChatroom(final Chatroom chatroom) {
        createdChatrooms.add(chatroom);
    }

    public void removeCreatedChatroom(final Chatroom chatroom) {
        createdChatrooms.remove(chatroom);
    }

    public Set<Chatroom> getUpdatedChatrooms() {
        return updatedChatrooms;
    }

    public void setUpdatedChatrooms(final Set<Chatroom> updatedChatrooms) {
        this.updatedChatrooms = updatedChatrooms;
    }

    public void addUpdatedChatroom(final Chatroom chatroom) {
        updatedChatrooms.add(chatroom);
    }

    public void removeUpdatedChatroom(final Chatroom chatroom) {
        updatedChatrooms.remove(chatroom);
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
                ", createdAt=" + createdAt +
                ", lastConnection=" + lastConnection +
                ", failedConnectionAttempts=" + failedConnectionAttempts +
                ", isLocked=" + isLocked +
                ", lockedAt=" + lockedAt +
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
