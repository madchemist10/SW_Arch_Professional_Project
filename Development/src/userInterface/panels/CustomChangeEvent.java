package userInterface.panels;

import java.beans.PropertyChangeEvent;

/**
 * This is the panels' custom event that is generated when
 * one panel needs to send a message back to the {@link PanelManager}.
 */
class CustomChangeEvent extends PropertyChangeEvent {

    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * @param source       the bean that fired the event
     * @param propertyName the programmatic name of the property that was changed
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    CustomChangeEvent(Object source, String propertyName) {
        super(source, propertyName, null, null);
    }
}
