package com.tayfunisik.firebaseauthlogin;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    public static boolean isEmailValid(String email) { //mail format� kontrol eder
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static String randomString(int lenght){
        char[] chars = "abcdefghijklmnoprstuvyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i<lenght; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

}
