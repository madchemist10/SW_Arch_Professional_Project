package app.utilities.apiHandlers;

import app.Application;

/**
 * This abstract APIHandler is responsible for ensuring
 * each API functionality is the same on the surface for
 * the application when it executes methods on each
 * subclass of AAPIHandler.
 */
abstract class AAPIHandler implements IAPIHandler {

    /**Local static reference to the AAPIHandler that
     * handles the request from the application.*/
    static AAPIHandler instance = null;
    /**Reference to the Application to gain access to the settings file.*/
    static Application app;

    /**
     * Create a new {@link AAPIHandler}.
     * Retrieve the instance of the {@link Application}.
     */
    AAPIHandler(){
        app = Application.getInstance();
    }

    /**
     * Allows for a default execution of any request
     * and receiving of a return value.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) {
        return null;
    }
}
