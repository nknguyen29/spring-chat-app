package fr.utc.sr03.chatapp.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

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

    @OneToMany(mappedBy = "chatroom")
    private Set<ChatroomUser> chatroomUsers;

    protected Chatroom() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getValidityDuration() {
        return validityDuration;
    }

    public void setValidityDuration(Timestamp validityDuration) {
        this.validityDuration = validityDuration;
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

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", validityDuration=" + validityDuration +
                '}';
    }

    /**
     * Calcule la date de fin du chatroom
     * 
     * @return la date de fin du chatroom
     */
    public Timestamp getEndDate() {
        return new Timestamp(startDate.getTime() + validityDuration.getTime());
    }

    /**
     * Vérifie si le chatroom est valide
     * 
     * @return true si le chatroom est valide, false sinon
     */
    public boolean isValid() {
        return new Timestamp(System.currentTimeMillis()).before(getEndDate());
    }

    /**
     * Vérifie si le chatroom a commencé
     * 
     * @return true si le chatroom a commencé, false sinon
     */
    public boolean isStarted() {
        return new Timestamp(System.currentTimeMillis()).after(startDate);
    }

    /**
     * Vérifie si le chatroom est en cours
     * 
     * @return true si le chatroom est en cours, false sinon
     */
    public boolean isRunning() {
        return isStarted() && isValid();
    }

    /**
     * Vérifie si le chatroom est terminé
     * 
     * @return true si le chatroom est terminé, false sinon
     */
    public boolean isOver() {
        return !isValid();
    }

    /**
     * Calcule le temps restant avant la fin du chatroom
     * 
     * @return le temps restant avant la fin du chatroom
     */
    public long getRemainingTime() {
        return getEndDate().getTime() - System.currentTimeMillis();
    }

    /**
     * Calcule le temps écoulé depuis le début du chatroom
     * 
     * @return le temps écoulé depuis le début du chatroom
     */
    public long getElapsedTime() {
        return System.currentTimeMillis() - startDate.getTime();
    }

    /**
     * Calcule le temps d'attente avant le début du chatroom
     * 
     * @return le temps d'attente avant le début du chatroom
     */
    public long getWaitingTime() {
        return startDate.getTime() - System.currentTimeMillis();
    }
}
