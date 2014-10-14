package edu.uni.cs.syntaxdesigns.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class YummlyUtil {

    private static final String YUMMLY_CREDS_FILE_NAME = "yummly-creds.txt";

    public static String getApplicationId(Context context) {
        try {
            BufferedReader assetsFile = new BufferedReader(new InputStreamReader(context.getAssets().open(YUMMLY_CREDS_FILE_NAME)));
            return assetsFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException(YUMMLY_CREDS_FILE_NAME + " needs to have application ID on first line and application Key on second line");
    }

    public static String getApplicationKey(Context context) {
        try {
            BufferedReader assetsFile = new BufferedReader(new InputStreamReader(context.getAssets().open(YUMMLY_CREDS_FILE_NAME)));
            assetsFile.readLine();
            return assetsFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException(YUMMLY_CREDS_FILE_NAME + " needs to have application ID on first line and application Key on second line");
    }
}
