package app.utilities.apiHandlers;

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
}