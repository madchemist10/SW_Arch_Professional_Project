package userInterface.finalGUI;

import userInterface.finalGUI.panels.GUIController;

/**
 * Launcher for TradeNet GUI application.
 */
public class TradeNetGUI {

    /**
     * Execute main program by creating the GUI controller and
     * allowing it to be visible.
     */
    public static void main(String[] args) {
        GUIController controller = new GUIController();
        controller.setVisible(true);
    }
}
