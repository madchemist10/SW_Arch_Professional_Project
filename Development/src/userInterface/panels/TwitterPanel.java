package userInterface.panels;

import userInterface.GUIConstants;

/**
 * This panel is required for interaction with
 * the {@link app.utilities.apiHandlers.TwitterAPIHandler}.
 */
public class TwitterPanel extends BasePanel{

    /**
     * Create a new {@link TwitterPanel}.
     */
    TwitterPanel(){
        super(GUIConstants.TWITTER_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {

    }
}
