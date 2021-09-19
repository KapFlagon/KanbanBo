package framework;


import com.airhacks.afterburner.views.FXMLView;

import java.util.ResourceBundle;


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
        this.bundle = bundle;
    }


    // Getters and Setters


    // Initialisation methods


    // Other methods

}
