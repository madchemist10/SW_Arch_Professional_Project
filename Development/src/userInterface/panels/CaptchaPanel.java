package userInterface.panels;

import userInterface.GUIConstants;

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
    }
}
