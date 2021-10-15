package persistence.dto.card;

import persistence.dto.ResourceItemDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CardDTO extends AbstractCardDTO{


    // Variables
    private int position;
    private LocalDate dueOnDate;


    // Constructors
    public CardDTO() {
        super();
        this.position = 1;
        this.dueOnDate = null;
    }

    public CardDTO(UUID uuid, UUID parentColumnUUID, String title, String description, List<ResourceItemDTO> resourcesList, int position) {
        super(uuid, parentColumnUUID, title, description);
        this.position = position;
        this.dueOnDate = null;
    }


    // Getters and Setters
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
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
