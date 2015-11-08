package com.example.mrx.twitterapp.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by ivor.aguado on 20/08/2015.
 */
public class UIUtils {

    private static long lastToastShowed;

    public static void toastThis(Context context, String message) {
        if (System.currentTimeMillis() > lastToastShowed) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            lastToastShowed += Toast.LENGTH_LONG + System.currentTimeMillis();
        }
    }

    public static void toastThis(Context context, int message) {
        if (System.currentTimeMillis() > lastToastShowed) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            lastToastShowed += Toast.LENGTH_LONG + System.currentTimeMillis();
        }
    }

    public static void hideSoftKeyBoard(@NonNull Activity activity) {
        try {
            InputMethodManager input = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
        }
    }
}
