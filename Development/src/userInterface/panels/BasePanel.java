package userInterface.panels;

import app.Application;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Base panel that contains all the mandatory functionality for
 * each of the sub class panels.
 */
abstract class BasePanel extends JPanel{

    /**Local reference to the application for execution
     * of callbacks from each of the gui panels.*/
    static Application app = null;
    /**List of all listeners that are associated with this class.*/
    private final List<PropertyChangeListener> listeners = new LinkedList<>();
    /**Unique panel identifier for this Panel.
     * Used for tab names in the {@link PanelManager}*/
    private final String panelIdentifier;

    /**
     * Constructor to create a new panel and ensure
     * the application exists on program startup.
     * @param panelIdentifier unique name identifier for this panel.
     */
    BasePanel(String panelIdentifier){
        super();
        this.panelIdentifier = panelIdentifier;
        getApp();
    }

    /**
     * Retrieve an instance of the Application so
     * that each of the subclass panels can execute application
     * code.
     * @return instance of Application.
     */
    private static Application getApp(){
        if(app == null){
            app = Application.getInstance();
        }
        return app;
    }

    /**
     * Retrieve this panel's unique identifier.
     * @return this panel's name.
     */
    String getPanelIdentifier(){
        return panelIdentifier;
    }

    /**
     * Add a property change listener to this panel for when this
     * panel should notify its listeners of property changes.
     * @param listener that is to listen for events from this panel.
     */
    void addPropertyListener(PropertyChangeListener listener){
        listeners.add(listener);
    }

    /**
     * Notify all listeners that an event has been created.
     * @param event that is to alert all listeners of an event.
     */
    void notifyListeners(CustomChangeEvent event){
        for(PropertyChangeListener listener: listeners){
            listener.propertyChange(event);
        }
    }

    /**
     * Generate the panel from the panel's local
     * elements.
     */
    abstract void buildPanel();
}
