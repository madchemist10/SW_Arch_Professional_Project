package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.*;

/**
 * This panel is required for interaction with
 * the {insert newsAPI handler here}.
 */
public class NewsPanel extends BasePanel {

    /**Panel that contains all of the controls for issuing a request.*/
    private final JPanel controlsPanel = new JPanel();
    /**Grid constraints on where elements should be placed in the controls panel.*/
    private final GridBagConstraints controlsPanelConstraints = new GridBagConstraints();

    /**
     * Create a new {@link NewsPanel}.
     */
    NewsPanel(){
        super(GUIConstants.NEWS_PANEL_IDENTIFIER);
        /*Construct the panel.*/
        buildPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(controlsPanel);    //place at (0,0) [parent]
    }
}
