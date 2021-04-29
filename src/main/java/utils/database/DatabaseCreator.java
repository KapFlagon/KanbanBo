package utils.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

public class DatabaseCreator {


    // Variables
    private Task<Void> databaseCreationTask;

    // Constructors
    public DatabaseCreator() {
        databaseCreationTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
                int iterations;
                int max = DatabaseUtils.MAX_TABLES;
                System.out.println("Inside the task");
                for (iterations = 0; iterations < max; iterations++) {
                    System.out.println("Inside the table creator loop");
                    switch (iterations) {
                        case 0:
                            DatabaseUtils.createProjectTable(connectionSource);
                            this.updateMessage("'Project' table generated");
                            break;
                        case 1:
                            DatabaseUtils.createProjectTemplateTable(connectionSource);
                            this.updateMessage("'Project Template' table generated");
                            break;
                        case 2:
                            DatabaseUtils.createProjectStatusesTable(connectionSource);
                            this.updateMessage("'Project Statuses' table generated");
                            break;
                        case 3:
                            DatabaseUtils.createColumnTable(connectionSource);
                            this.updateMessage("'Column' table generated");
                            break;
                        case 4:
                            DatabaseUtils.createTemplateColumnTable(connectionSource);
                            this.updateMessage("'Column Template' table generated");
                            break;
                        case 5:
                            DatabaseUtils.createCardTable(connectionSource);
                            this.updateMessage("'Card' table generated");
                            break;
                        case 6:
                            DatabaseUtils.createTemplateCardTable(connectionSource);
                            this.updateMessage("'Card Template' table generated");
                            break;
                        case 7:
                            DatabaseUtils.createResourceItemTable(connectionSource);
                            this.updateMessage("'Resource Item' table generated");
                            break;
                        case 8:
                            DatabaseUtils.createIntermediaryTables(connectionSource);
                            this.updateMessage("'Intermediary' table generated");
                            break;
                    }
                    updateProgress(iterations, max);
                }
                connectionSource.close();
                this.succeeded();
                return null;
            }
        };
    }


    // Getters and Setters
    public Task<Void> getDatabaseCreationTask() {
        return databaseCreationTask;
    }

    // Initialisation methods


    // Other methods


    public void startDbCreation() {
        Thread thread = new Thread(databaseCreationTask);
        thread.setDaemon(true);
        thread.start();
        // TODO how to stop the task and scheduled service?
        // TODO how to close the pop-up?
    }
}
