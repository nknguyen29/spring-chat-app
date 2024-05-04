package fr.utc.sr03.chatapp.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import org.springframework.format.annotation.DateTimeFormat;

public class ChatroomWithStatsDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private Timestamp validityDuration;

    private Long userCount;

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

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(final Long userCount) {
        this.userCount = userCount;
    }

    public Timestamp getEndDate() {
        return new Timestamp(startDate.getTime() + validityDuration.getTime());
    }

    public boolean isValid() {
        return new Timestamp(System.currentTimeMillis()).before(getEndDate());
    }

    public boolean isStarted() {
        return new Timestamp(System.currentTimeMillis()).after(startDate);
    }

    public boolean isRunning() {
        return isStarted() && isValid();
    }

    public boolean isOver() {
        return !isValid();
    }

    public long getRemainingTime() {
        return getEndDate().getTime() - System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - startDate.getTime();
    }

    public long getWaitingTime() {
        return startDate.getTime() - System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "ChatroomWithStatsDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", validityDuration=" + validityDuration +
                ", userCount=" + userCount +
                '}';
    }

}
