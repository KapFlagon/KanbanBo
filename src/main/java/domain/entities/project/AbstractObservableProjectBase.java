package domain.entities.project;

import persistence.dto.project.AbstractProjectDTO;
import domain.entities.column.AbstractObservableColumnBase;
import domain.entities.resourceitem.ObservableResourceItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import domain.entities.AbstractObservableEntity;

import java.util.List;
import java.util.UUID;

public class AbstractObservableProjectBase<T extends AbstractProjectDTO, U extends AbstractObservableColumnBase> extends AbstractObservableEntity<T> {


    // Variables
    protected UUID projectUUID;
    protected SimpleStringProperty projectTitle;
    protected SimpleStringProperty projectDescription;
    protected SimpleStringProperty creationTimestamp;
    protected SimpleStringProperty lastChangedTimestamp;
    protected ObservableList<ObservableResourceItem> resourceItems;
    protected ObservableList<U> columns;
    protected SimpleBooleanProperty hasFinalColumn;


    // Constructors

    public AbstractObservableProjectBase(T projectObjectDTO) {
        super();
        projectUUID = projectObjectDTO.getUuid();
        initAllProperties(projectObjectDTO);
        initAllObservableLists();
    }

    public AbstractObservableProjectBase(T projectObjectDTO, ObservableList<ObservableResourceItem> resourceItems, ObservableList<U> columnsList) {
        super();
        projectUUID = projectObjectDTO.getUuid();
        initAllProperties(projectObjectDTO);
        initAllObservableLists(resourceItems, columnsList);
    }

    protected AbstractObservableProjectBase(AbstractProjectBuilder abstractProjectBuilder) {
        super();
        this.projectUUID = abstractProjectBuilder.projectUUIDValue != null ? abstractProjectBuilder.projectUUIDValue : null;
        this.projectTitle = new SimpleStringProperty(abstractProjectBuilder.projectTitleValue);
        this.projectDescription = new SimpleStringProperty(abstractProjectBuilder.projectDescriptionValue);
        this.creationTimestamp = new SimpleStringProperty(abstractProjectBuilder.creationTimestampValue);
        this.lastChangedTimestamp = new SimpleStringProperty(abstractProjectBuilder.lastChangedTimestampValue);
        this.resourceItems = abstractProjectBuilder.resourceItemsListValue;
        this.columns = abstractProjectBuilder.columnsListValue;
        this.hasFinalColumn = new SimpleBooleanProperty(abstractProjectBuilder.hasFinalColumnValue);
        addListenersToObservableLists();
    }

    // Getters and Setters
    public UUID getProjectUUID() {
        return projectUUID;
    }

    public SimpleStringProperty projectTitleProperty() {
        return projectTitle;
    }

    public SimpleStringProperty projectDescriptionProperty() {
        return projectDescription;
    }

    public SimpleStringProperty creationTimestampProperty() {
        return creationTimestamp;
    }

    public SimpleStringProperty lastChangedTimestampProperty() {
        return lastChangedTimestamp;
    }

    public ObservableList<ObservableResourceItem> getResourceItems() {
        return resourceItems;
    }
    public void setResourceItems(ObservableList<ObservableResourceItem> resourceItems) {
        this.resourceItems = resourceItems;
        addListenersToResourceItemsList();
    }

    public ObservableList<U> getColumns() {
        return columns;
    }
    public void setColumns(ObservableList<U> columns) {
        this.columns = columns;
        addListenersToColumnsList();
    }

    // Initialisation methods
    protected void initAllProperties() {
        this.projectTitle = new SimpleStringProperty();
        this.projectDescription = new SimpleStringProperty();
        this.creationTimestamp = new SimpleStringProperty();
        this.lastChangedTimestamp = new SimpleStringProperty();
    }

    protected void initAllProperties(T projectObjectDTO) {
        this.projectTitle = new SimpleStringProperty(projectObjectDTO.getTitle());
        this.projectDescription = new SimpleStringProperty(projectObjectDTO.getDescription());
        this.creationTimestamp = new SimpleStringProperty(projectObjectDTO.getCreatedOnTimeStamp().toString());
        this.lastChangedTimestamp = new SimpleStringProperty(projectObjectDTO.getLastChangedOnTimeStamp().toString());
        this.hasFinalColumn = new SimpleBooleanProperty(false);
    }

    protected void initAllObservableLists(ObservableList<ObservableResourceItem> resourceItems, ObservableList<U> columnsList) {
        this.resourceItems = resourceItems;
        this.columns = columnsList;
        updateHasFinalColumn(columns);
        this.columns.addListener((ListChangeListener<U>) c -> {
            while(c.next()) {
                updateHasFinalColumn(columns);
            }
        });
    }

    protected void initAllObservableLists() {
        this.resourceItems = FXCollections.observableArrayList();
        this.columns = FXCollections.observableArrayList();
    }

    protected void addListenersToObservableLists() {
        addListenersToResourceItemsList();
        addListenersToColumnsList();
    }

    private void addListenersToResourceItemsList() {

    }

    private void addListenersToColumnsList() {
        this.columns.addListener((ListChangeListener<U>) c -> {
            while(c.next()) {
                updateHasFinalColumn(columns);
            }
        });
    }


    // Other methods
    private void updateHasFinalColumn(List<U> columnsList) {
        this.hasFinalColumn.set(false);
        for(AbstractObservableColumnBase abstractObservableColumnBase : columnsList) {
            if (abstractObservableColumnBase.isFinalColumn()) {
                this.hasFinalColumn.set(true);
            }
        }
    }


    protected static class AbstractProjectBuilder<U>{
        private UUID projectUUIDValue;
        private String projectTitleValue;
        private String projectDescriptionValue;
        private String creationTimestampValue;
        private String lastChangedTimestampValue;
        private ObservableList<ObservableResourceItem> resourceItemsListValue;
        private ObservableList<U> columnsListValue;
        private Boolean hasFinalColumnValue;

        protected AbstractProjectBuilder() {
            this.projectTitleValue = "";
            this.projectDescriptionValue = "";
            this.creationTimestampValue = "";
            this.lastChangedTimestampValue = "";
            this.resourceItemsListValue = FXCollections.observableArrayList();
            this.columnsListValue = FXCollections.observableArrayList();
            this.hasFinalColumnValue = false;
        }

        public static AbstractProjectBuilder newInstance() {
            return new AbstractProjectBuilder();
        }

        public AbstractProjectBuilder uuid(UUID uuid) {
            this.projectUUIDValue = uuid;
            return this;
        }

        public AbstractProjectBuilder title(String title) {
            this.projectTitleValue = title;
            return this;
        }

        public AbstractProjectBuilder description(String description) {
            this.projectDescriptionValue = description;
            return this;
        }

        public AbstractProjectBuilder creationTimestampString(String creationTimestampString) {
            this.creationTimestampValue = creationTimestampString;
            return this;
        }

        public AbstractProjectBuilder lastChangedTimestampString(String lastChangedTimestampString) {
            this.projectDescriptionValue = lastChangedTimestampString;
            return this;
        }

        public AbstractProjectBuilder resourceItemsList(ObservableList<ObservableResourceItem> observableResourceItems) {
            this.resourceItemsListValue = observableResourceItems;
            return this;
        }

        public AbstractProjectBuilder columns(ObservableList<U> columns) {
            this.columnsListValue = columns;
            return this;
        }

        public AbstractProjectBuilder hasFinalColumn(boolean hasFinalColumn) {
            this.hasFinalColumnValue = hasFinalColumn;
            return this;
        }
    }



}
