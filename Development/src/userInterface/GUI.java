package userInterface;

import userInterface.panels.PanelManager;

/**
 * Launcher for the GUI for the user to interact with.
 */
public class GUI {

    /**
     * Start execution of this GUI Application by launching
     * the main in this file.
     * @param args not used
     */
    public static void main(String[] args) {
        PanelManager panelManager = new PanelManager();
        panelManager.setVisible(true); // enable the panel manager
    }
}
