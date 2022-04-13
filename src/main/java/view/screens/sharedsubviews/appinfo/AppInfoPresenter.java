package view.screens.sharedsubviews.appinfo;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class AppInfoPresenter implements Initializable {

    // JavaFX injected node variables

    // Other variables
    List< Map<String, String>> attributionEntryTextMapsList;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attributionEntryTextMapsList = new ArrayList<>();
        String attributionPrefix = "attributions.technology.";
        List<String> allKeys = resources.keySet()
                .stream()
                .filter(key -> key.startsWith(attributionPrefix))
                .collect(Collectors.toList());
        List< Map<String, String> > allEntryMapList = new ArrayList<>();
        for(int iterator = 0; iterator < allKeys.size(); iterator++) {
            String attributionIterationPrefix = attributionPrefix + (iterator + 1);
            List<String> keySubList = allKeys
                    .stream()
                    .filter(item -> item.startsWith(attributionIterationPrefix))
                    .collect(Collectors.toList());
            Map<String, String> innerMap = new HashMap<>();

            if (keySubList.size() > 0) {
                for(String key : keySubList) {
                    innerMap.put(key, resources.getString(key));
                }
                attributionEntryTextMapsList.add(innerMap);
            }
        }
        System.out.println(attributionEntryTextMapsList);
    }

    // UI event methods

    // Other methods


}