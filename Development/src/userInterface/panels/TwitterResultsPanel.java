package userInterface.panels;

import twitter4j.Status;

import javax.swing.*;

/**
 */
class TwitterResultsPanel extends JPanel {

    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    TwitterResultsPanel(){
        buildPanel();
    }

    private void buildPanel(){
    }


    void addEntryToResults(Status tweet){
        listModel.addElement(tweet.getText());

    }
}
