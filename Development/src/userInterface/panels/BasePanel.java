package userInterface.panels;

import app.Application;

import javax.swing.*;

/**
 */
abstract class BasePanel extends JPanel{

    /**Local reference to the application for execution
     * of callbacks from each of the gui panels.*/
    static Application app = null;

    /**
     * Constructor to create a new panel and ensure
     * the application exists on program startup.
     */
    BasePanel(){
        super();
        getApp();
    }

    /**
     * Retrieve an instance of the Application so
     * that each of the subclass panels can execute application
     * code.
     * @return instance of Application.
     */
    private static Application getApp(){
        if(app == null){
            app = Application.getInstance();
        }
        return app;
    }
}
