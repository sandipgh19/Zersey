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

    public static final String GET_URL = "http://simplifiedcoding.16mb.com/CardView/getData.php";
    public static final String TAG_IMAGE_TITLE = "url";
    public static final String TAG_IMAGE_CATEGORY = "name";
    public static final String TAG_IMAGE_DESCRIPTION = "";
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_JSON_ARRAY="result";

    public Config(int i){
        title = new String[i];
        category = new String[i];
        bitmaps = new Bitmap[i];
        description = new String[i];
        urls = new String[i];
    }
}