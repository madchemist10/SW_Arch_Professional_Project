package app.utilities.apiHandlers;

import app.constants.Constants;
import app.exception.BaseException;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

/**
 */
class CaptchaAPIHandler extends AAPIHandler {

    private final ReCaptcha captcha;

    private CaptchaAPIHandler(){
        super();
        String siteKey = app.getValueFromSettings(Constants.CAPTCHA_API_SITE_KEY);
        String secretKey = app.getValueFromSettings(Constants.CAPTCHA_API_SECRET_KEY);
        captcha = ReCaptchaFactory.newReCaptcha(siteKey,secretKey,false);
    }

    /**
     * Retrieve an instance of the TwitterAPIHandler so that
     * the application may have access to the methods within
     * this class.
     * Instance is a singleton reference.
     * @return instance of this TwitterAPIHandler.
     */
    static AAPIHandler getInstance() {
        if(instance == null){
            instance = new CaptchaAPIHandler();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String buildAPIRequest(String[] inputs) {
        return captcha.createRecaptchaHtml(null,null);
    }

    /**
     * Return value is of type {@link String}.
     * Must be cast to be used.
     * {@inheritDoc}
     */
    @Override
    public Object executeAPIRequest(String request) throws BaseException{
        return super.executeAPIRequest(request);
    }
}
