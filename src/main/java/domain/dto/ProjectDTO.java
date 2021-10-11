package domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProjectDTO {


    // Variables
    private UUID uuid;
    private String title;
    private String description;
    private int status;
    private LocalDateTime createdOnDate;
    private LocalDateTime lastChangedOnDate;
    private LocalDate dueOnDate;


    // Constructors


    // Getters and Setters
    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOnDate() {
        return createdOnDate;
    }
    public void setCreatedOnDate(LocalDateTime createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public LocalDateTime getLastChangedOnDate() {
        return lastChangedOnDate;
    }
    public void setLastChangedOnDate(LocalDateTime lastChangedOnDate) {
        this.lastChangedOnDate = lastChangedOnDate;
    }

    public LocalDate getDueOnDate() {
        return dueOnDate;
    }
    public void setDueOnDate(LocalDate dueOnDate) {
        this.dueOnDate = dueOnDate;
    }


    // Initialisation methods


    // Other methods


}
