package userInterface.finalGUI.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Wrapper around flow layout panels.
 */
class BasicFlowPanel extends JPanel {

    /**Default is Left Justified*/
    BasicFlowPanel(JComponent myComponent){
        this(myComponent, FlowLayout.LEFT);
    }

    /**
     * Place component in its unique panel with given FlowLayout.
     * @param myComponent that is to be added to the panel.
     * @param alignment for the component in this panel.
     */
    private BasicFlowPanel(JComponent myComponent, int alignment){
        setLayout(new FlowLayout(alignment));
        add(myComponent);
    }
}
