package app.utilities.apiHandlers;

/**
 * This interface maintains the all functionality that
 * should be present outside of this package for use
 * within the application.
 */
public interface IAPIHandler {

    /**
     * Allows each subclass to build a specific APIRequest with
     * a given set of inputs.
     * @param inputs that are to be handled in each sub APIHandler.
     * @return String of the request that should be executed.
     */
    String buildAPIRequest(String[] inputs);

    /**
     * Allows each subclass to execute a specific APIRequest with
     * a given set of inputs.
     * @param request that is to be sent to the external API.
     * @return that is received after the request has been made.
     */
    String executeAPIRequest(String request);
}
