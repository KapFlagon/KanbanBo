package persistence.services;

import domain.Attribution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class AttributionServiceTest {

    private ResourceBundle dummyResourceBundle;
    private AttributionService attributionService;
    private List<Attribution> attributionList;
    private String stringPrefix = "attributions.technology.";


    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        dummyResourceBundle = getDummyResourceBundle();
        attributionService = new AttributionService(dummyResourceBundle);
    }



    @Test
    void countOfAttributions() {
        attributionList = attributionService.getAttributions(stringPrefix);
        assertEquals(8, attributionList.size());
    }

    @Test
    void checkAttributionTitles() {

    }

    private ResourceBundle getDummyResourceBundle() {
        return new ResourceBundle() {
            Map<String, String> map = Map.ofEntries(
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.1.title", "First attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.1.website", "http://www.firstattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.1.sourcecode", "https://www.firstattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.1.licensetype", "Apache 2.0"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.1.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.2.title", "Second attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.2.website", "http://www.secondattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.2.sourcecode", "http://www.secondattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.2.licensetype", "ISC"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.2.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.3.title", ""),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.3.website", "http://www.thirdattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.3.sourcecode", "http://www.thirdattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.3.licensetype", "GNU GLP V 3"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.3.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.4.title", "Fourth attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.4.website", ""),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.4.sourcecode", "http://www.fourthattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.4.licensetype", "Copyleft"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.4.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.5.title", "Fifth attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.5.website", "http://www.fifthattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.5.sourcecode", ""),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.5.licensetype", "Apache 2.0"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.5.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.6.title", "Sixth attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.6.website", "http://www.sixtattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.6.sourcecode", "http://www.sixthattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.6.licensetype", ""),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.6.licenselocation", "none"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.7.title", "Seventh attribution title"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.7.website", "http://www.seventhattribution.com/"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.7.sourcecode", "http://www.seventhattribution.com/source"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.7.licensetype", "GNU GPL V 2"),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.7.licenselocation", ""),
                    new AbstractMap.SimpleEntry<String, String>("attributions.technology.8.title", "Eight attribution title")
            );

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