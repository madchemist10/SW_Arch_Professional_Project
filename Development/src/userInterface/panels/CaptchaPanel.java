package userInterface.panels;

import app.utilities.apiHandlers.APIHandles;
import userInterface.GUIConstants;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 */
public class CaptchaPanel extends BasePanel {

    public CaptchaPanel(){
        super(GUIConstants.CAPTCHA_PANEL_IDENTIFIER);
        buildPanel();
    }

    @Override
    void buildPanel() {
        addCaptchaImage();
    }

    private void addCaptchaImage(){
        BufferedImage captcha = (BufferedImage) app.getAPIHandler(APIHandles.CAPTCHA).executeAPIRequest("1");
        JLabel captchaLabel = new JLabel(new ImageIcon(captcha));
        add(captchaLabel);
    }
}
