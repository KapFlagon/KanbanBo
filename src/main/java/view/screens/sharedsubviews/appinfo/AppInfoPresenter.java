package view.screens.sharedsubviews.appinfo;

import domain.Attribution;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import persistence.services.AttributionService;
import utils.LicenseFileReader;
import utils.view.ScrollPaneFixer;
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
    private Accordion fontsAccordion;
    @FXML
    private Accordion iconsAccordion;
    @FXML
    private Accordion imagesAndIllustrationsAccordion;
    @FXML
    private Accordion technologyAccordion;


    // Other variables
    private AttributionService attributionService;
    @Inject
    private LicenseFileReader licenseFileReader;
    private String windowTitle;
    private String sourceCodeAddress;
    private List<Attribution> fontAttributions;
    private List<Attribution> imageAttributions;
    private List<Attribution> iconAttributions;
    private List<Attribution> techAttributions;
    private final String FONTS_PREFIX = "attributions.fonts.";
    private final String IMAGES_PREFIX = "attributions.imagesandillustrations.";
    private final String ICONS_PREFIX = "attributions.icons.";
    private final String TECH_PREFIX = "attributions.technology.";

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
        fontAttributions = new ArrayList<>();
        imageAttributions = new ArrayList<>();
        iconAttributions = new ArrayList<>();
        techAttributions = new ArrayList<>();
        attributionService = new AttributionService(resources, licenseFileReader);
        try {
            fontAttributions = attributionService.getAttributions(FONTS_PREFIX);
            imageAttributions = attributionService.getAttributions(IMAGES_PREFIX);
            iconAttributions = attributionService.getAttributions(ICONS_PREFIX);
            techAttributions = attributionService.getAttributions(TECH_PREFIX);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
            // TODO insert better error handling here
        }

        populateAttributionCategoryAccordion(fontAttributions, fontsAccordion);
        populateAttributionCategoryAccordion(imageAttributions, imagesAndIllustrationsAccordion);
        populateAttributionCategoryAccordion(iconAttributions, iconsAccordion);
        populateAttributionCategoryAccordion(techAttributions, technologyAccordion);

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
    private void populateAttributionCategoryAccordion(List<Attribution> attributionList, Accordion attributionAccordion) {
        for (Attribution attribution : attributionList) {
            AttributionEntryView attributionEntryView = new AttributionEntryView(f -> attribution);
            TitledPane tPane = new TitledPane(attribution.getTitle(), attributionEntryView.getView());
            attributionAccordion.getPanes().add(tPane);
        }
    }


}