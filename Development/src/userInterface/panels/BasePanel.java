package userInterface.panels;

import app.Application;

import javax.swing.*;
import java.awt.*;
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
    /**Each panel needs set of constraints so each component can
     * be placed into the panel.*/
    final GridBagConstraints constraints;

    /**
     * Constructor to create a new panel and ensure
     * the application exists on program startup.
     * Each base panel will use a grid bag layout for component placement.
     * @param panelIdentifier unique name identifier for this panel.
     */
    BasePanel(String panelIdentifier){
        super();
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
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
     * Custom override for adding a component to the base panel
     * parent structure. This allows the base panel parent class
     * to handle the underlying structure of how to organize the panel.
     * @param component that is to be added to the panel.
     */
    void addComponent(Component component){
        add(component, constraints);
    }

    /**
     * Validate user input for a given input.
     * Default is to not allow empty strings.
     * The values to be validated are assumed to be not null
     * and pulled straight from text field.
     * @param userInput to be validated.
     * @return true if valid, false otherwise.
     */
    boolean validateUserInput(String userInput){
        return !"".equals(userInput);
    }

    /**
     * Generate the panel from the panel's local
     * elements.
     */
    abstract void buildPanel();

}
