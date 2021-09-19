package domain.activerecords.project;

import persistence.tables.project.TemplateProjectTable;

public class TemplateProjectActiveRecord<T extends TemplateProjectTable> extends AbstractProjectActiveRecord{



    // Variables
    protected TemplateProjectTable templateProjectModel;

    // Constructors
    public TemplateProjectActiveRecord() {
        super(TemplateProjectTable.class);
    }

    // Getters and Setters


    // Initialisation methods


    // Other methods


}
