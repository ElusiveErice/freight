package com.csu.freightbook.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SharedHelper {

    private static final String SHARED_PREFERENCES_NAME = "share_preferences";
    private static final String PATTERN_LOCKER_PASSWORD = "pattern_locker_password";

    private static final String NULL_PASSWORD_STRING = "";

    private Context mContext;

    public SharedHelper(Context context) {
        mContext = context;
    }

    public void savePatternLockerPassword(List<Integer> password) {
        SharedPreferences sp = mContext.getSharedPreferences("share_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        StringBuilder passwordBuilder = new StringBuilder();
        for (Integer integer : password) {
            passwordBuilder.append(integer);
        }
        editor.putString(PATTERN_LOCKER_PASSWORD, passwordBuilder.toString());
        editor.apply();
    }

    public List<Integer> readPatternLockerPassword() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String passwordString = sp.getString(PATTERN_LOCKER_PASSWORD, NULL_PASSWORD_STRING);
        List<Integer> password = new ArrayList<>();
        for (char c : passwordString.toCharArray()) {
            password.add(c - 48);
        }
        return password;
    }
}
