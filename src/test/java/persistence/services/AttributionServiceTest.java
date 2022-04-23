package persistence.services;

import domain.Attribution;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.LicenseFileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.*;

class AttributionServiceTest {

    private LicenseFileReader licenseFileReader = new LicenseFileReader();
    private AttributionService attributionService;
    private final String attributionPrefix = "attributions";
    private final char keyDelimiter = '.';
    private final String category1 = "technology";
    private final String category2 = "fonts";
    private final String category3 = "illustrations_and_images";
    private final String category4 = "icons";
    private final String category5 = "other";
    private String testCategoryPrefix = attributionPrefix + keyDelimiter + category1 + keyDelimiter;

    @Test
    public void resourceBundleParsesCorrectly() throws FileNotFoundException {
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.title", "First attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.website", "http://www.firstattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.sourcecode", "https://www.firstattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licensetype", "Apache 2.0"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licenselocation", "none")
        );
        ResourceBundle dummyResourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(dummyResourceBundle, licenseFileReader);

        Map<String, List<Attribution>> attributionCategoriesListMap = attributionService.getAttributionCategoriesMap();
        List<Attribution> attributionList = attributionCategoriesListMap.get(category1);

        assertAll(
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.title"), attributionList.get(0).getTitle()),
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.website"), attributionList.get(0).getWebsite()),
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.sourcecode"), attributionList.get(0).getSourceCodeAddress()),
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.licensetype"), attributionList.get(0).getLicenseType()),
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.licenselocation"), attributionList.get(0).getLicenseFileLocation())
        );
    }

    @Test
    public void correctNumberOfEntriesParsedFromBundle() throws FileNotFoundException {
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.title", "First attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.website", "http://www.firstattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.sourcecode", "https://www.firstattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licensetype", "Apache 2.0"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.title", "Second attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.website", "http://www.secondattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.sourcecode", "http://www.secondattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.licensetype", "ISC.txt"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "3.title", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "3.website", "http://www.thirdattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "3.sourcecode", "http://www.thirdattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "3.licensetype", "GNU GLP V 3"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "3.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "4.title", "Fourth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "4.website", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "4.sourcecode", "http://www.fourthattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "4.licensetype", "Copyleft"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "4.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "5.title", "Fifth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "5.website", "http://www.fifthattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "5.sourcecode", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "5.licensetype", "Apache 2.0"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "5.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "6.title", "Sixth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "6.website", "http://www.sixtattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "6.sourcecode", "http://www.sixthattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "6.licensetype", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "6.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "7.title", "Seventh attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "7.website", "http://www.seventhattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "7.sourcecode", "http://www.seventhattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "7.licensetype", "GNU GPL V 2"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "7.licenselocation", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "8.title", "Eight attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "9.title", "Ninth attribution title")
        );
        ResourceBundle dummyResourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(dummyResourceBundle, licenseFileReader);

        Map<String, List<Attribution>> attributionCategoriesListMap = attributionService.getAttributionCategoriesMap();
        List<Attribution> attributionList = attributionCategoriesListMap.get(category1);
        assertEquals(9, attributionList.size());
    }

    @Test
    public void correctlyParsesIncompleteData() throws FileNotFoundException {
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.title", "First attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.website", "http://www.firstattribution.com/")
        );
        ResourceBundle dummyResourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(dummyResourceBundle, licenseFileReader);

        Map<String, List<Attribution>> attributionCategoriesListMap = attributionService.getAttributionCategoriesMap();
        List<Attribution> attributionList = attributionCategoriesListMap.get(category1);

        assertAll(
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.title"), attributionList.get(0).getTitle()),
                () -> assertEquals(dummyDataMap.get(testCategoryPrefix + "1.website"), attributionList.get(0).getWebsite()),
                () -> assertEquals("", attributionList.get(0).getSourceCodeAddress()),
                () -> assertEquals("", attributionList.get(0).getLicenseType()),
                () -> assertEquals("", attributionList.get(0).getLicenseFileLocation())
        );
    }

    @Test
    public void correctlyParsesLicenseFile() throws FileNotFoundException {
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licenselocation", "/otherlicenses/testattribution/GNU GPL V3.txt")
        );
        ResourceBundle dummyResourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(dummyResourceBundle, licenseFileReader);

        Map<String, List<Attribution>> attributionCategoriesListMap = attributionService.getAttributionCategoriesMap();
        List<Attribution> attributionList = attributionCategoriesListMap.get(category1);

        InputStream inputStream = getClass().getResourceAsStream(dummyDataMap.get(testCategoryPrefix + "1.licenselocation"));
        String dummyLicenseContent;
        if (inputStream != null) {
            dummyLicenseContent = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            dummyLicenseContent = "";
            System.out.println("Error getting resource, inputStream is null...");
        }

        assertEquals(dummyLicenseContent, attributionList.get(0).getLicenseContent());
    }

    @Test
    public void throwsExceptionIfFailsToParseLicenseFile() throws FileNotFoundException {
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licenselocation", "/otherlicenses/testattribution/none.txt")
        );
        ResourceBundle dummyResourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(dummyResourceBundle, licenseFileReader);

        assertThrows(FileNotFoundException.class, () -> attributionService.getAttributionCategoriesMap());
    }

    @Test
    public void dataCorrectlyParsedIntoCategories() throws FileNotFoundException {
        String testCategoryPrefix2 = attributionPrefix + keyDelimiter + category2 + keyDelimiter;
        String testCategoryPrefix3 = attributionPrefix + keyDelimiter + category3 + keyDelimiter;
        String testCategoryPrefix4 = attributionPrefix + keyDelimiter + category4 + keyDelimiter;
        String testCategoryPrefix5 = attributionPrefix + keyDelimiter + category5 + keyDelimiter;
        Map<String, String> dummyDataMap = Map.ofEntries(
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.title", "First attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.website", "http://www.firstattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.sourcecode", "https://www.firstattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licensetype", "Apache 2.0"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "1.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.title", "Second attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.website", "http://www.secondattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.sourcecode", "http://www.secondattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.licensetype", "ISC.txt"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix + "2.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix2 + "1.title", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix2 + "1.website", "http://www.thirdattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix2 + "1.sourcecode", "http://www.thirdattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix2 + "1.licensetype", "GNU GLP V 3"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix2 + "1.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix3 + "1.title", "Fourth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix3 + "1.website", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix3 + "1.sourcecode", "http://www.fourthattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix3 + "1.licensetype", "Copyleft"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix3 + "1.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix4 + "5.title", "Fifth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix4 + "5.website", "http://www.fifthattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix4 + "5.sourcecode", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix4 + "5.licensetype", "Apache 2.0"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix4 + "5.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "6.title", "Sixth attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "6.website", "http://www.sixtattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "6.sourcecode", "http://www.sixthattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "6.licensetype", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "6.licenselocation", "none"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "7.title", "Seventh attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "7.website", "http://www.seventhattribution.com/"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "7.sourcecode", "http://www.seventhattribution.com/source"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "7.licensetype", "GNU GPL V 2"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "7.licenselocation", ""),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "8.title", "Eight attribution title"),
                new AbstractMap.SimpleEntry<String, String>(testCategoryPrefix5 + "9.title", "Ninth attribution title")
        );
        ResourceBundle resourceBundle = createDummyResourceBundle(dummyDataMap);
        attributionService = new AttributionService(resourceBundle, licenseFileReader);

        Map<String, List<Attribution>> categoryMap = attributionService.getAttributionCategoriesMap();
        assertAll(
            () -> assertNotNull(categoryMap),
            () -> assertTrue(categoryMap.containsKey(category1)),
            () -> assertTrue(categoryMap.containsKey(category2)),
            () -> assertTrue(categoryMap.containsKey(category3)),
            () -> assertTrue(categoryMap.containsKey(category4)),
            () -> assertTrue(categoryMap.containsKey(category5))
        );
    }

    private ResourceBundle createDummyResourceBundle(Map<String, String> dummyData) {
        return new ResourceBundle() {
            Map<String, String> map = dummyData;

            @Override
            protected Object handleGetObject(String key) {
                return map.get(key);
            }

            @Override
            public Enumeration<String> getKeys() {
                return Collections.enumeration(map.keySet());
            }
        };
    }

}