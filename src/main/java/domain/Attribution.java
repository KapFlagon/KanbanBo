package domain;

import javafx.beans.property.SimpleStringProperty;

public class Attribution {


    // Variables
    private SimpleStringProperty title;
    private SimpleStringProperty website;
    private SimpleStringProperty sourceCodeAddress;
    private AttributionLicense attributionLicense;

    // Constructors


    public Attribution() {
        title = new SimpleStringProperty("");
        website = new SimpleStringProperty("");
        sourceCodeAddress = new SimpleStringProperty("");
        attributionLicense = new AttributionLicense("","","");
    }

    public Attribution(String title, String website, String sourceCodeAddress, AttributionLicense attributionLicense) {
        this.title = new SimpleStringProperty(title);
        this.website = new SimpleStringProperty(website);
        this.sourceCodeAddress = new SimpleStringProperty(sourceCodeAddress);
        this.attributionLicense = attributionLicense;
    }


    // Getters and Setters
    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getWebsite() {
        return website.get();
    }

    public SimpleStringProperty websiteProperty() {
        return website;
    }

    public void setWebsite(String website) {
        this.website.set(website);
    }

    public String getSourceCodeAddress() {
        return sourceCodeAddress.get();
    }

    public SimpleStringProperty sourceCodeAddressProperty() {
        return sourceCodeAddress;
    }

    public void setSourceCodeAddress(String sourceCodeAddress) {
        this.sourceCodeAddress.set(sourceCodeAddress);
    }

    public String getLicenseType() {
        return this.attributionLicense.getLicenseType();
    }

    public SimpleStringProperty licenseTypeProperty() {
        return attributionLicense.licenseTypeProperty();
    }

    public void setLicenseType(String licenseType) {
        this.attributionLicense.setLicenseType(licenseType);
    }

    public String getLicenseFileLocation() {
        return this.attributionLicense.getLicenseFileLocation();
    }

    public SimpleStringProperty licenseFileLocationProperty() {
        return attributionLicense.licenseFileLocationProperty();
    }

    public void setLicenseFileLocation(String licenseFileLocation) {
        this.attributionLicense.setLicenseFileLocation(licenseFileLocation);
    }

    public String getLicenseContent() {
        return attributionLicense.getLicenseContent();
    }

    public SimpleStringProperty licenseContentProperty() {
        return attributionLicense.licenseContentProperty();
    }

    public void setLicenseContent(String licenseData) {
        this.attributionLicense.setLicenseContent(licenseData);
    }


    // Initialisation methods


    // Other methods


}
