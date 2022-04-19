package persistence.services;

import domain.Attribution;
import utils.LicenseFileReader;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class AttributionService {


    // Variables
    private ResourceBundle resourceBundle;
    private LicenseFileReader licenseFileReader;


    // Constructors
    public AttributionService(ResourceBundle resourceBundle, LicenseFileReader licenseFileReader) {
        this.resourceBundle = resourceBundle;
        this.licenseFileReader = licenseFileReader;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public List<Attribution> getAttributions(String attributionPrefix) throws FileNotFoundException {
        List< Map<String, String>> attributionEntryTextMapsList = new ArrayList<>();
        List<String> allKeys = resourceBundle.keySet()
                .stream()
                .filter(key -> key.startsWith(attributionPrefix))
                .collect(Collectors.toList());
        for(int iterator = 0; iterator < allKeys.size(); iterator++) {
            String attributionIterationPrefix = attributionPrefix + (iterator + 1);
            List<String> keySubList = allKeys
                    .stream()
                    .filter(item -> item.startsWith(attributionIterationPrefix))
                    .collect(Collectors.toList());
            Map<String, String> innerMap = new HashMap<>();

            if (keySubList.size() > 0) {
                for(String key : keySubList) {
                    innerMap.put(key, resourceBundle.getString(key));
                }
                attributionEntryTextMapsList.add(innerMap);
            }
        }
        List<Attribution> attributionList = new ArrayList<>();
        for(Map<String, String> attributeMap : attributionEntryTextMapsList) {
            Attribution attribution = parseMapToAttribution(attributeMap);
            attributionList.add(attribution);
        }
        return attributionList;
    }

    private Attribution parseMapToAttribution(Map<String, String> attributionStringDataMap) throws FileNotFoundException{
        Attribution attribution = new Attribution();
        Optional<String> titleKey = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("title"))
                .findFirst();
        titleKey.ifPresent(key -> attribution.setTitle(attributionStringDataMap.get(key)));
        Optional<String> websiteKey = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("website"))
                .findFirst();
        websiteKey.ifPresent(key -> attribution.setWebsite(attributionStringDataMap.get(key)));
        Optional<String> sourceCodeAddressKey = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("sourcecode"))
                .findFirst();
        sourceCodeAddressKey.ifPresent(key -> attribution.setSourceCodeAddress(attributionStringDataMap.get(key)));
        Optional<String> licenseTypeKey = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("licensetype"))
                .findFirst();
        licenseTypeKey.ifPresent(key -> attribution.setLicenseType(attributionStringDataMap.get(key)));
        Optional<String> licenseLocationKey = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("licenselocation"))
                .findFirst();
        String licenseContent = "";
        if (licenseLocationKey.isPresent()) {
            attribution.setLicenseFileLocation(attributionStringDataMap.get(licenseLocationKey.get()));
            if(!attribution.getLicenseFileLocation().equals("") && !attribution.getLicenseFileLocation().toLowerCase().equals("none")) {
                licenseContent = licenseFileReader.readLicenseFileContent(attribution.getLicenseFileLocation());
            }
            attribution.setLicenseContent(licenseContent);
        }
        return attribution;
    }


}
