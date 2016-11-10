package userInterface.panels;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
class BasicPanel extends JPanel {

    /**Default is Left Justified*/
    BasicPanel(JComponent myComponent){
        this(myComponent, FlowLayout.LEFT);
    }

    private BasicPanel(JComponent myComponent, int alignment){
        setLayout(new FlowLayout(alignment));
        add(myComponent);
    }
}
