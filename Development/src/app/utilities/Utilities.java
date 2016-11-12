package app.utilities;

import app.constants.Constants;

import java.security.MessageDigest;

/**
 * Utilities that can be used throughout the code base.
 */
public class Utilities {

    /**
     * Generate an MD5 hash for a given word.
     * @param word that is to have md5 hash generated.
     * @return md5 hash of the given word.
     */
    public static String getMD5Hash(String word){
        String md5Hash = "";
        try {
            MessageDigest digest = MessageDigest.getInstance(Constants.MD5_ALGORITHM);
            md5Hash = new String(digest.digest(word.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Hash;
    }
}
