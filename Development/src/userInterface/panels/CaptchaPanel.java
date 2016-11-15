package userInterface.panels;

import app.exception.BaseException;
import app.utilities.apiHandlers.APIHandles;
import app.utilities.apiHandlers.IAPIHandler;
import userInterface.GUIConstants;

import javax.swing.*;

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
        IAPIHandler captchaAPI = app.getAPIHandler(APIHandles.CAPTCHA);
        String request = captchaAPI.buildAPIRequest(null);
        String[] setOfRequestParams = request.split(" ");
        String src = null;
        for(String str: setOfRequestParams){
            if(str.contains("src=")){
                src = str;
                break;
            }
        }
        if(src != null) {
            src = src.replaceFirst("src=\"", "");
            if(src.startsWith("http:")){
                src = src.replaceAll("\"></script>\r\n","");
            }
        }
        try {
            Object returnVal = captchaAPI.executeAPIRequest(src);
            if(returnVal == null){
                return;
            }
            if(returnVal instanceof String){
                String htmlReturn = (String) returnVal;
                add(new JLabel(htmlReturn));
            }
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }
}
