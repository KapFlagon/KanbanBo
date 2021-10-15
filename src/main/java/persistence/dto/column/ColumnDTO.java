package persistence.dto.column;

import persistence.dto.card.AbstractCardDTO;

import java.util.List;
import java.util.UUID;

public class ColumnDTO extends AbstractColumnDTO{


    // Variables
    private int position;



    // Constructors
    public ColumnDTO() {
        super();
        this.position = 1;
    }

    public ColumnDTO(UUID uuid, UUID parentProjectUUID, String title, boolean finalColumn, List<AbstractCardDTO> cardDTOList, int position) {
        super(uuid, parentProjectUUID, title, finalColumn);
        this.position = position;
    }


    // Getters and Setters
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    // Initialisation methods


    // Other methods


}
