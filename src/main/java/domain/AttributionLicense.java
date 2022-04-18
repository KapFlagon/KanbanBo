package domain;

import javafx.beans.property.SimpleStringProperty;

public class AttributionLicense {


    // Variables
    private SimpleStringProperty licenseType;
    private SimpleStringProperty licenseFileLocation;
    private SimpleStringProperty licenseData;

    // Constructors

    public AttributionLicense(String licenseType, String licenseFileLocation, String licenseData) {
        this.licenseType = new SimpleStringProperty(licenseType);
        this.licenseFileLocation = new SimpleStringProperty(licenseFileLocation);
        this.licenseData = new SimpleStringProperty(licenseData);
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

    public String getLicenseData() {
        return licenseData.get();
    }

    public SimpleStringProperty licenseDataProperty() {
        return licenseData;
    }

    public void setLicenseData(String licenseData) {
        this.licenseData.set(licenseData);
    }


    // Initialisation methods


    // Other methods


}
