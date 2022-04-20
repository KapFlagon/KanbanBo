package view.screens.sharedsubviews.appinfo.attributionentry;

import domain.Attribution;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javax.inject.Inject;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AttributionEntryPresenter implements Initializable {

    // JavaFX injected node variables
    @FXML
    private Label entryTitleLbl;
    @FXML
    private Hyperlink entryWebsiteHyperlink;
    @FXML
    private Hyperlink entrySourceCodeHyperlink;
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
    @FXML
    void openAttributionSourceCodeSite(ActionEvent event) {
        openWebsite(entrySourceCodeHyperlink.textProperty().get());
        event.consume();
    }

    @FXML
    void openAttributionWebsite(ActionEvent event) {
        openWebsite(entryWebsiteHyperlink.textProperty().get());
        event.consume();
    }


    // Other methods
    private void bindNodesToModel() {
        entryTitleLbl.textProperty().bind(attribution.titleProperty());
        entryWebsiteHyperlink.textProperty().bind(attribution.websiteProperty());
        entrySourceCodeHyperlink.textProperty().bind(attribution.sourceCodeAddressProperty());
        licenseTypeLbl.textProperty().bind(attribution.licenseTypeProperty());
        licenseTextArea.textProperty().bind(attribution.licenseContentProperty());
    }

    private void openWebsite(String websiteAddress) {
        if (!websiteAddress.equals("")) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(websiteAddress));
            } catch (Exception exception) {
                exception.printStackTrace();
                // TODO Insert proper error handling response here.
            }
        }
    }

}