package com.example.sandipghosh.zersey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandipghosh on 10/05/17.
 */

public class ParseJSON {

    public static String[] name;
    public static String[] comment;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_COMMENT = "comment";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            name = new String[users.length()];
            comment = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                name[i] = jo.getString(KEY_NAME);
                comment[i] = jo.getString(KEY_COMMENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
