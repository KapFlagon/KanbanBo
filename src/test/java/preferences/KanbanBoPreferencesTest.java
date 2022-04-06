package preferences;

import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.*;


class KanbanBoPreferencesTest {

    @Test
    void testChildNodeForApplication() {
        String childNodeString = "test";
        String expectedAbsolutePath = "/kanbanbo/" + childNodeString;
        KanbanBoPreferences kanbanBoPreferences = new KanbanBoPreferences();
        Preferences childNode = kanbanBoPreferences.kanbanboPreferencesChildNode(childNodeString);

        assertEquals(expectedAbsolutePath, childNode.absolutePath());
    }

}