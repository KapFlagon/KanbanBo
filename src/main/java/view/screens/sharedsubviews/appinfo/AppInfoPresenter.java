package view.screens.sharedsubviews.appinfo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AppInfoPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private TextArea appFullLicenseTextArea;

    @FXML
    private Accordion fontsAccordion;

    @FXML
    private Accordion iconsAccordion;

    @FXML
    private Accordion imagesAndIllustrationsAccordion;

    @FXML
    private Accordion technologyAccordion;


    // Other variables
    private String sourceCodeAddress;
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
        sourceCodeAddress = resources.getString("hyperlink.sourcecode.target");
    }

    // UI event methods
    @FXML
    private void openSourceCodeLink(ActionEvent event) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(sourceCodeAddress));
        } catch (Exception exception) {
            exception.printStackTrace();
            // TODO Insert proper error handling response here.
        }
        event.consume();
    }

    // Other methods


}