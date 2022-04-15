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
        String title = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("title"))
                .collect(Collectors.toList())
                .get(0);
        attribution.setTitle(title);
        String website = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("website"))
                .collect(Collectors.toList())
                .get(0);
        attribution.setWebsite(website);
        String sourceCodeAddress = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("sourcecode"))
                .collect(Collectors.toList())
                .get(0);
        attribution.setSourceCodeAddress(sourceCodeAddress);
        String licenseType = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("licensetype"))
                .collect(Collectors.toList())
                .get(0);
        attribution.setLicenseType(licenseType);
        String licenseLocation = attributionStringDataMap.keySet()
                .stream()
                .filter(key -> key.contains("licenselocation"))
                .collect(Collectors.toList())
                .get(0);
        attribution.setLicenseFileLocation(licenseLocation);
        return attribution;
    }


}
