package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;

/**
 * The Panel Manager is the frame that all panels
 * are included in.
 * This handles each of the panels and the swapping between them.
 */
public class PanelManager extends JFrame{

    /**
     * Allow the creation of new Panel Manager.
     */
    public PanelManager(){
        super();
        this.setTitle(GUIConstants.TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
