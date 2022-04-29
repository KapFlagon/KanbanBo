package view.screens.sharedsubviews.appinfo;

import domain.Attribution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

            VBox categoryBox = new VBox();
            Label categoryLbl = new Label(resources.getString("label.category.prompt"));
            categoryLbl.getStyleClass().add("h5");
            ListView<String> categoryListView = new ListView<>();
            categoryListView.getStyleClass().add("body");
            categoryBox.getChildren().add(categoryLbl);
            categoryBox.getChildren().add(categoryListView);

            VBox subCategoryBox = new VBox();
            Label subCategoryLbl = new Label(resources.getString("label.category_entry.prompt"));
            subCategoryLbl.getStyleClass().add("h5");
            ListView<String> subCategoryListView = new ListView<>();
            subCategoryListView.getStyleClass().add("body");
            subCategoryBox.getChildren().add(subCategoryLbl);
            subCategoryBox.getChildren().add(subCategoryListView);

            AttributionEntryView attributionEntryView = new AttributionEntryView();
            AttributionEntryPresenter attributionEntryPresenter = (AttributionEntryPresenter) attributionEntryView.getPresenter();
            Hyperlink categorySelectionLink = new Hyperlink(resources.getString("tab.attributions.categories"));
            categorySelectionLink.setOnAction(event -> {
                categoryListView.getSelectionModel().clearSelection();
                attributionsBorderPane.setCenter(categoryBox);
            });

            Hyperlink subCategorySelectionLink = new Hyperlink();
            subCategorySelectionLink.setOnAction(event -> {
                attributionsBorderPane.setCenter(subCategoryBox);
                subCategoryListView.getSelectionModel().clearSelection();
            });
            Hyperlink entryLink = new Hyperlink();

            for(String categoryKey : categoryAttributionListMap.keySet()) {
                categoryListView.getItems().add(replaceUnderscoresWithSpaces(categoryKey));
            }

            categoryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) { // Accounts for scenario where selection has being cleared, which also triggers the listener
                    String keyValue = replaceSpacesWithUnderscores(categoryListView.getSelectionModel().getSelectedItem());
                    subCategoryListView.getItems().clear();
                    for (Attribution attribution : categoryAttributionListMap.get(keyValue)) {
                        subCategoryListView.getItems().add(attribution.getTitle());
                    }
                    attributionsBorderPane.setCenter(subCategoryBox);
                    subCategorySelectionLink.setText(categoryListView.getSelectionModel().getSelectedItem());
                    breadcrumbsBoxPresenter.addCrumb(subCategorySelectionLink);
                }
            });

            subCategoryListView.getSelectionModel().selectedItemProperty().addListener((item -> {
                String key = replaceSpacesWithUnderscores(categoryListView.getSelectionModel().getSelectedItem());
                List<Attribution> attributionList = categoryAttributionListMap.get(key);
                for(Attribution attribution : attributionList) {
                    if (attribution.getTitle().equals(subCategoryListView.getSelectionModel().getSelectedItem())){
                        attributionEntryPresenter.setAttribution(attribution);
                        attributionsBorderPane.setCenter(attributionEntryView.getView());
                        entryLink.setText(subCategoryListView.getSelectionModel().getSelectedItem());
                        breadcrumbsBoxPresenter.addCrumb(entryLink);
                    }
                }
            }));
            attributionsBorderPane.setCenter(categoryBox);
            breadcrumbsBoxPresenter.addCrumb(categorySelectionLink);
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