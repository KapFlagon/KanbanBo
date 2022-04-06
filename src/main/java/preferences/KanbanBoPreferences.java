package preferences;

import java.util.prefs.Preferences;

public class KanbanBoPreferences {


    // Variables
    private Preferences userRootNode;
    private Preferences kanbanboPreferences;


    // Constructors


    // Getters and Setters


    // Initialisation methods


    // Other methods
    public Preferences kanbanboPreferencesChildNode(String childNode) {
        userRootNode = Preferences.userRoot();
        kanbanboPreferences = userRootNode.node("kanbanbo");
        return kanbanboPreferences.node(childNode);
    }

}
