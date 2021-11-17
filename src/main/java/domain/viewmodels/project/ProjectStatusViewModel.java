package domain.viewmodels.project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import persistence.tables.project.ProjectStatusTable;

import java.util.Locale;
import java.util.ResourceBundle;

public class ProjectStatusViewModel {


    // Variables
    private ProjectStatusTable projectStatusTable;
    private SimpleIntegerProperty statusId;
    private SimpleStringProperty statusText;


    // Constructors
    public ProjectStatusViewModel(ProjectStatusTable projectStatusTable) {
        this.projectStatusTable = projectStatusTable;
        initializeProperties();
    }


    // Getters and Setters
    public int getStatusId() {
        return statusId.get();
    }

    public SimpleIntegerProperty statusIdProperty() {
        return statusId;
    }

    public String getStatusText() {
        return statusText.get();
    }

    public SimpleStringProperty statusTextProperty() {
        return statusText;
    }

    // Initialisation methods
    private void initializeProperties() {
        statusId = new SimpleIntegerProperty(projectStatusTable.getProject_status_id());
        String localisedStatusText = getLocalisedText(projectStatusTable.getProject_status_text_key());
        statusText = new SimpleStringProperty(localisedStatusText);
    }


    // Other methods
    private String getLocalisedText(String resourceBundleKey) {
        Locale locale = Locale.getDefault();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("systemtexts", locale);
        return resourceBundle.getString(resourceBundleKey);
    }

    public void bindToStatusIdProperty(SimpleIntegerProperty propertyToBind) {
        propertyToBind.bind(statusId);
    }

    public void bindToStatusTextProperty(SimpleStringProperty propertyToBind) {
        propertyToBind.bind(statusText);
    }


    @Override
    public String toString() {
        return statusText.getValue();
    }
}
