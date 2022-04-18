package persistence.services;

import domain.Attribution;

import java.util.*;
import java.util.stream.Collectors;

public class AttributionService {


    // Variables
    private ResourceBundle resourceBundle;


    // Constructors
    public AttributionService(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public List<Attribution> getAttributions(String attributionPrefix) {
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

    private Attribution parseMapToAttribution(Map<String, String> attributionStringDataMap) {
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
        licenseLocationKey.ifPresent(key -> attribution.setLicenseFileLocation(attributionStringDataMap.get(key)));
        return attribution;
    }


}
