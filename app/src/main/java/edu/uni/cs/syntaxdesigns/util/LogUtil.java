package edu.uni.cs.syntaxdesigns.util;

import android.util.Log;

public class LogUtil {

    public static void d(final String message) {
        Log.d("SYNTAX_DESIGNS", message);
    }

    public static void d(final Throwable throwable) {
        Log.d("SYNTAX_DESIGNS", "exception", throwable);
    }
}
