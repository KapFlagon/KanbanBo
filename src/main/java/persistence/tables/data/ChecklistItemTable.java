package persistence.tables.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import persistence.tables.TableObject;

import java.util.UUID;

@DatabaseTable(tableName = "checklist_item")
public class ChecklistItemTable implements TableObject<UUID> {


    // Variables
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID parent_card_uuid;
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, canBeNull = false, dataType = DataType.UUID, useGetSet = true)
    private UUID checklist_item_uuid;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.BOOLEAN)
    private boolean checklist_item_finished;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.STRING)
    private String checklist_item_title;
    @DatabaseField(canBeNull = true, useGetSet = true, dataType = DataType.STRING)
    private String checklist_item_due_on_date;
    @DatabaseField(canBeNull = false, useGetSet = true, dataType = DataType.INTEGER)
    private int checklist_item_position;

    // Constructors
    public ChecklistItemTable() {
    }


    // Getters and Setters
    public UUID getParent_card_uuid() {
        return parent_card_uuid;
    }
    public void setParent_card_uuid(UUID parent_card_uuid) {
        this.parent_card_uuid = parent_card_uuid;
    }

    public UUID getChecklist_item_uuid() {
        return checklist_item_uuid;
    }
    public void setChecklist_item_uuid(UUID checklist_item_uuid) {
        this.checklist_item_uuid = checklist_item_uuid;
    }

    public String getChecklist_item_title() {
        return checklist_item_title;
    }
    public void setChecklist_item_title(String checklist_item_title) {
        this.checklist_item_title = checklist_item_title;
    }

    public boolean isChecklist_item_finished() {
        return checklist_item_finished;
    }
    public void setChecklist_item_finished(boolean checklist_item_finished) {
        this.checklist_item_finished = checklist_item_finished;
    }

    public String getChecklist_item_due_on_date() {
        return checklist_item_due_on_date;
    }
    public void setChecklist_item_due_on_date(String checklist_item_due_on_date) {
        this.checklist_item_due_on_date = checklist_item_due_on_date;
    }

    public int getChecklist_item_position() {
        return checklist_item_position;
    }
    public void setChecklist_item_position(int checklist_item_position) {
        this.checklist_item_position = checklist_item_position;
    }

    @Override
    public UUID getID() {
        return checklist_item_uuid;
    }


    // Initialisation methods


    // Other methods


}
