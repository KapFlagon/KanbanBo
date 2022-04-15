package persistence.services;

import domain.Attribution;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AttributionServiceTest {

    private AttributionService attributionService;
    private ResourceBundle resourceBundle;
    private List<Attribution> attributionList;
    private String stringPrefix = "attributions.technology.";


    @BeforeEach
    void setup() {
        resourceBundle = ResourceBundle.getBundle("/java/resources/attributionservicedata.properties", Locale.getDefault());
        attributionService = new AttributionService(resourceBundle);
        attributionList = attributionService.getAttributions(stringPrefix);
    }

    @Test
    void countOfAttributions() {
        assertEquals(7, attributionList.size());
    }

    @Test
    void checkAttributionTitles() {
        assertAll(
                () -> assertEquals(resourceBundle.getString("attributions.technology.1.title"), attributionList.get(0).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.2.title"), attributionList.get(1).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.3.title"), attributionList.get(2).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.4.title"), attributionList.get(3).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.5.title"), attributionList.get(4).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.6.title"), attributionList.get(5).getTitle()),
                () -> assertEquals(resourceBundle.getString("attributions.technology.7.title"), attributionList.get(6).getTitle())
        );
    }

}