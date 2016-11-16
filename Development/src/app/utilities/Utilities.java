package app.utilities;

import app.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.HashMap;

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

    /**
     * Check if File Exists
     * @param filePath to be checked for existence.
     * @return true if exists, false if not exists.
     */
    public static boolean fileExists(String filePath){
        return new File(filePath).exists();
    }

    /**
     * Generate hashMap that has all the loaded settings for
     * this application.
     * @param filepath of the file to load settings from.
     * @return Map of key to value pairs for settings denoted by the user.
     */
    public static HashMap<String, String> loadSettingsFile(String filepath){
        if(!Utilities.fileExists(filepath)){
            return null;
        }
        HashMap<String, String> settingsMap = new HashMap<>();
        String line;
        String delimiter = ": ";
        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null){
                if(!line.startsWith("###")) {
                    String[] new_data = line.split(delimiter);
                    String key = new_data[0];
                    String value = "";
                    //we have valid key and value
                    if(new_data.length == 2){
                        value = new_data[1];
                        settingsMap.put(key,value);
                    }
                    //we only have a key
                    if(new_data.length == 1){
                        settingsMap.put(key,value);
                    }
                }
            }
            br.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return settingsMap;
    }
}
