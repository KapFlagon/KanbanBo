package utils;

import java.io.File;
import java.io.IOException;

public class FileCreationUtils {

    // Other methods
    public static void createEmptyDatabaseFile(File newDatabaseFile) throws IOException {
        if(newDatabaseFile.createNewFile()) {
            System.out.println("Log new file creation");
        } else {
            System.out.println("Log new file creation error");
        }
        //TODO Insert better logging of events
    }

    public static void deleteDatabaseFile(File databaseFile) throws IOException{
        if(databaseFile.delete()) {
            System.out.println("Log file deletion");
        } else {
            System.out.println("Log file deletion error");
        }
        //TODO Insert better logging of events
    }
}
