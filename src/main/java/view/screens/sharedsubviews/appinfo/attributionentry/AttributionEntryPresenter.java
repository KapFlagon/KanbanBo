package view.screens.sharedsubviews.appinfo.attributionentry;

import domain.Attribution;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class AttributionEntryPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label entryTitleLbl;
    @FXML
    private Label entryWebsiteLbl;
    @FXML
    private Label entrySourceCodeRepoLbl;
    @FXML
    private Label licenseTypeLbl;
    @FXML
    private TextArea licenseTextArea;


    // Other variables
    @Inject
    private Attribution attribution;

    // Constructors

    // Getters & Setters

    // Initialization methods
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing the attribution entry + " + attribution.getLicenseType());
        bindNodesToModel();
    }

    // UI event methods

    // Other methods
    private void bindNodesToModel() {
        entryTitleLbl.textProperty().bind(attribution.titleProperty());
        entryWebsiteLbl.textProperty().bind(attribution.websiteProperty());
        entrySourceCodeRepoLbl.textProperty().bind(attribution.sourceCodeAddressProperty());
        licenseTypeLbl.textProperty().bind(attribution.licenseTypeProperty());
        licenseTextArea.textProperty().bind(attribution.licenseContentProperty());
    }

}