package userInterface.panels;

import userInterface.GUIConstants;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Panel Manager is the frame that all panels
 * are included in.
 * This handles each of the panels and the swapping between them.
 */
public class PanelManager extends JFrame implements PropertyChangeListener{

    /**Tabbed pane that is responsible for displaying the correct tabs to the user.*/
    private final JTabbedPane tabbedPane = new JTabbedPane();

    /**
     * Allow the creation of new Panel Manager.
     */
    public PanelManager(){
        super();
        this.setTitle(GUIConstants.TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buildFrame();
    }

    /**
     * Construct the parent frame that houses all sub class panels.
     */
    private void buildFrame(){
        /*Add the Login Panel.*/
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.addPropertyListener(this);
        tabbedPane.add(loginPanel, loginPanel.getPanelIdentifier());
        this.add(tabbedPane);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
