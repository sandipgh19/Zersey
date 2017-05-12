package com.example.sandipghosh.zersey;

import android.content.SharedPreferences;

/**
 * Created by sandipghosh on 11/05/17.
 */

public class User {
    private String Email;
    private String Password;
    private String Name;



    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";


    public User() {
        Email = "";
        Password = "";
        Name = "";
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }




    public static boolean emailValid(String email){
        String EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (email.matches(EMAIL_REGEX)){
            return true;
        }else {
            return false;
        }
    }
    public static boolean fieldValid(String name){
        if (name.length()>0){
            return true;
        } else {
            return false;
        }
    }
    public static boolean passwordValid(String password){
        if (password.length()>=6){
            return true;
        } else {
            return false;
        }
    }

    public static void saveLoginCredentials(SharedPreferences sharedPreferences,
                                            String email,
                                            String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, email);
        editor.putString(NAME, name);
        editor.commit();
    }

    public static void removeLoginCredentials(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, "");
        editor.putString(NAME, "");
        editor.commit();
    }

    public static void setDetailsToUser(User user, SharedPreferences sharedPreferences){
        user.setEmail(sharedPreferences.getString("email", ""));
        user.setName(sharedPreferences.getString("name", ""));
    }

}