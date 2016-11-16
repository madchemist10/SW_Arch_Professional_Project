package app.utilities.apiHandlers;

import app.Application;
import app.exception.BaseException;

/**
 * This abstract APIHandler is responsible for ensuring
 * each API functionality is the same on the surface for
 * the application when it executes methods on each
 * subclass of AAPIHandler.
 */
abstract class AAPIHandler implements IAPIHandler {

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
    public Object executeAPIRequest(String request) throws BaseException {
        return null;
    }
}
