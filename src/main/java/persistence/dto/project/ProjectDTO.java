package persistence.dto.project;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class ProjectDTO extends AbstractProjectDTO{


    // Variables
    private int status;
    private LocalDate dueOnDate;


    // Constructors


    public ProjectDTO() {
        super();
        this.status = 1;
        this.dueOnDate = null;
    }

    public ProjectDTO(UUID uuid, String title, String description, ZonedDateTime createdOnDate, ZonedDateTime lastChangedOnDate, int status, LocalDate dueOnDate) {
        super(uuid, title, description, createdOnDate, lastChangedOnDate);
        this.status = status;
        this.dueOnDate = dueOnDate;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
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
