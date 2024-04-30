package fr.utc.sr03.chatapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

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

    public String getDescription() {
        return description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getValidityDuration() {
        return validityDuration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public void setValidityDuration(Timestamp validityDuration) {
        this.validityDuration = validityDuration;
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
}
