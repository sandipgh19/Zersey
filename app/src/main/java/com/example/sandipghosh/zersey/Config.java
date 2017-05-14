package com.example.sandipghosh.zersey;

import android.graphics.Bitmap;

/**
 * Created by sandipghosh on 09/05/17.
 */

public class Config {

    public static String[] title;
    public static String[] category;
    public static Bitmap[] bitmaps;
    public static String[] description;
    public static String[] urls;

    public static final String GET_URL = "http://sandipgh19.000webhostapp.com/zersey/getData.php";
    public static final String TAG_IMAGE_TITLE = "title";
    public static final String TAG_IMAGE_CATEGORY = "category";
    public static final String TAG_IMAGE_DESCRIPTION = "description";
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_JSON_ARRAY="result";

    public static final String URL_REQUEST_SMS = "http://192.168.0.101/android_sms/msg91/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://192.168.0.101/android_sms/msg91/verify_otp.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "ANHIVE";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";

    public Config(int i){
        title = new String[i];
        category = new String[i];
        bitmaps = new Bitmap[i];
        description = new String[i];
        urls = new String[i];
    }
}
