package userInterface.panels.twitter;

import twitter4j.Status;
import userInterface.panels.BasicPanel;

import javax.swing.*;

/**
 */
public class TweetEntry extends JPanel {

    private final Status tweet;

    private final JLabel bodyLabel = new JLabel();

    public TweetEntry(Status tweet){
        super();
        this.tweet = tweet;
        buildPanel();
    }

    private void buildPanel(){
        bodyLabel.setText(tweet.getText());
        add(new BasicPanel(bodyLabel));
    }
}
