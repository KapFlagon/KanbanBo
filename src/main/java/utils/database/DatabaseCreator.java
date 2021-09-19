package utils.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import javafx.concurrent.Task;

public class DatabaseCreator {


    // Variables
    private Task<Void> databaseCreationTask;

    // Constructors
    public DatabaseCreator() {
        // TODO Create an interface from this, and then make an ORM specific implementation
        // TODO move the task initialization into a specific method
        databaseCreationTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                JdbcConnectionSource connectionSource = DatabaseUtils.getConnectionSource();
                int iterations;
                int max = DatabaseUtils.MAX_TABLES;
                System.out.println("Inside the task");
                // TODO https://stackoverflow.com/questions/26203660/how-to-create-list-filled-with-methods-in-java-and-iterate-over-it-using-method
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
                            DatabaseUtils.createResourceItemTypeTable(connectionSource);
                            this.updateMessage("'Resource Item Type' table generated");
                            break;
                        case 9:
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
    }
}
