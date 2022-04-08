package framework;


import com.airhacks.afterburner.views.FXMLView;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static java.util.ResourceBundle.getBundle;


public abstract class CustomFXMLView extends FXMLView {

    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("systemtexts");


    // Variables


    // Constructors
    public CustomFXMLView() {
        super();
        this.bundle = RESOURCE_BUNDLE;
    }

    public CustomFXMLView(ResourceBundle bundle) {
        super();
        this.bundle = RESOURCE_BUNDLE;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods

}
