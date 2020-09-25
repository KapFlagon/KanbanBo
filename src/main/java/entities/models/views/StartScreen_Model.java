package entities.models.views;

import java.nio.file.Path;
import java.util.ArrayList;

public class StartScreen_Model implements I_ViewModel{

    private ArrayList<Path> recentItemsPathList;


    public ArrayList<Path> getRecentItemsPathList() {
        return recentItemsPathList;
    }

    public void setRecentItemsPathList(ArrayList<Path> recentItemsPathList) {
        this.recentItemsPathList = recentItemsPathList;
    }

    public ArrayList<String> getFileNamesFromPathList() {
        ArrayList<String> fileNamesList = new ArrayList<String>();
        for (Path filePath : recentItemsPathList) {
            fileNamesList.add(filePath.getFileName().toString());
        }
        return fileNamesList;
    }
}
