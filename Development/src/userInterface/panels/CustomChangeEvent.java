package userInterface.panels;

import java.beans.PropertyChangeEvent;

/**
 * This is the panels' custom event that is generated when
 * one panel needs to send a message back to the {@link PanelManager}.
 */
class CustomChangeEvent extends PropertyChangeEvent {

    /**The custom defined event name, defined in {@link AppChangeEvents}*/
    private final AppChangeEvents eventName;

    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * @param source the bean that fired the event
     * @param eventName the name of the property that was changed
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    CustomChangeEvent(Object source, AppChangeEvents eventName) {
        super(source, eventName.toString(), null, null);
        this.eventName = eventName;
    }

    /**
     * Retrieve the event name for this event.
     * @return this event's event name.
     */
    AppChangeEvents getEventName(){
        return eventName;
    }
}
