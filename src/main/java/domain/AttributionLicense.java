package domain;

import javafx.beans.property.SimpleStringProperty;

public class AttributionLicense {


    // Variables
    private SimpleStringProperty licenseType;
    private SimpleStringProperty licenseFileLocation;
    private SimpleStringProperty licenseContent;

    // Constructors

    public AttributionLicense(String licenseType, String licenseFileLocation, String licenseContent) {
        this.licenseType = new SimpleStringProperty(licenseType);
        this.licenseFileLocation = new SimpleStringProperty(licenseFileLocation);
        this.licenseContent = new SimpleStringProperty(licenseContent);
    }


    // Getters and Setters
    public String getLicenseType() {
        return licenseType.get();
    }

    public SimpleStringProperty licenseTypeProperty() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType.set(licenseType);
    }

    public String getLicenseFileLocation() {
        return licenseFileLocation.get();
    }

    public SimpleStringProperty licenseFileLocationProperty() {
        return licenseFileLocation;
    }

    public void setLicenseFileLocation(String licenseFileLocation) {
        this.licenseFileLocation.set(licenseFileLocation);
    }

    public String getLicenseContent() {
        return licenseContent.get();
    }

    public SimpleStringProperty licenseContentProperty() {
        return licenseContent;
    }

    public void setLicenseContent(String licenseContent) {
        this.licenseContent.set(licenseContent);
    }


    // Initialisation methods


    // Other methods


}
