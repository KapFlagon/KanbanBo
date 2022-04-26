package view.screens.sharedsubviews.appinfo;

import domain.Attribution;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.w3c.dom.Attr;
import persistence.services.AttributionService;
import utils.LicenseFileReader;
import utils.view.ScrollPaneFixer;
import view.components.ui.breadcrumbsbox.BreadcrumbsBoxPresenter;
import view.components.ui.breadcrumbsbox.BreadcrumbsBoxView;
import view.screens.sharedsubviews.appinfo.attributionentry.AttributionEntryPresenter;
import view.screens.sharedsubviews.appinfo.attributionentry.AttributionEntryView;

import javax.inject.Inject;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AppInfoPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea appFullLicenseTextArea;
    @FXML
    private BorderPane attributionsBorderPane;


    // Other variables
    private AttributionService attributionService;
    @Inject
    private LicenseFileReader licenseFileReader;
    private String windowTitle;
    private String sourceCodeAddress;

    // Constructors

    // Getters & Setters
    public String getWindowTitle() {
        return windowTitle;
    }


    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        windowTitle = resources.getString("app.info.title");
        ScrollPaneFixer.fixBlurryScrollPan(scrollPane);
        sourceCodeAddress = resources.getString("app.sourcecode.hyperlink.target");
        String appLicenseLocation = resources.getString("app.license.location");
        try {
            String appLicenseContent = licenseFileReader.readLicenseFileContent(appLicenseLocation);
            appFullLicenseTextArea.textProperty().set(appLicenseContent);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            // TODO perform real error handling here
        }
        attributionService = new AttributionService(resources, licenseFileReader);

        BreadcrumbsBoxView breadcrumbsBoxView = new BreadcrumbsBoxView();
        BreadcrumbsBoxPresenter breadcrumbsBoxPresenter = (BreadcrumbsBoxPresenter) breadcrumbsBoxView.getPresenter();
        attributionsBorderPane.setTop(breadcrumbsBoxView.getView());
        try {
            Map<String, List<Attribution>> categoryAttributionListMap = attributionService.getAttributionCategoriesMap();
            // TODO need to put a Hyperlink into the breadcrumbs bar, and link it to both the list view and the actual views to be put in the borderpane.

            ListView<String> categoryListView = new ListView<>();
            ListView<String> categoryChildEntryListView = new ListView<>();

            AttributionEntryView attributionEntryView = new AttributionEntryView();
            AttributionEntryPresenter attributionEntryPresenter = (AttributionEntryPresenter) attributionEntryView.getPresenter();

            for(String categoryKey : categoryAttributionListMap.keySet()) {
                categoryListView.getItems().add(replaceUnderscoresWithSpaces(categoryKey));
            }
            categoryListView.getSelectionModel().selectedItemProperty().addListener(item -> {
                String keyValue = replaceSpacesWithUnderscores(categoryListView.getSelectionModel().getSelectedItem());
                for(Attribution attribution : categoryAttributionListMap.get(keyValue)) {
                    categoryChildEntryListView.getItems().add(attribution.getTitle());
                }
                attributionsBorderPane.setCenter(categoryChildEntryListView);
            });
            categoryChildEntryListView.getSelectionModel().selectedItemProperty().addListener((item -> {
                // TODO will need to be refactored, has to dive too deep
                for(List<Attribution> attributionList : categoryAttributionListMap.values()) {
                    for(Attribution attribution : attributionList) {
                        if (attribution.getTitle().equals(categoryChildEntryListView.getSelectionModel().getSelectedItem())){
                            attributionEntryPresenter.setAttribution(attribution);
                            attributionsBorderPane.setCenter(attributionEntryView.getView());
                        }
                    }
                }
            }));
            attributionsBorderPane.setCenter(categoryListView);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            // TODO insert better error handling here
        }

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
    private String replaceUnderscoresWithSpaces(String string) {
        String convertedString = string.replaceAll("_", " ");;
        return Character.toUpperCase(convertedString.charAt(0)) + convertedString.substring(1);
    }

    private String replaceSpacesWithUnderscores(String string) {
        String convertedString = string.replaceAll(" ", "_");
        return Character.toLowerCase(convertedString.charAt(0)) + convertedString.substring(1);
    }


}