package com.andeptrai.doantotnghiep.library_code;

import java.util.regex.Pattern;

public class CheckInfoSignUp {

    private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static boolean isEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    public static String checkPass(String password){
        if (password.length() < 8){
            return "Length litter 8 characters";
        }
        else{
            return "Right";
        }
    }

    public static boolean inputYet(String s){
        if (s.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }

    public static boolean checkRePass(String pass1, String pass2){
        if(pass1.equals(pass2)){
            return true;
        }
        else{
            return false;
        }
    }
}
