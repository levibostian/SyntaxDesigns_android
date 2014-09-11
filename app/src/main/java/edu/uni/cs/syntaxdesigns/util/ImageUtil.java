package edu.uni.cs.syntaxdesigns.util;

import android.content.Context;

import javax.inject.Inject;

public class ImageUtil {

    private Context mContext;

    @Inject
    public ImageUtil(Context context) {
        mContext = context;
    }

    public String testDaggerWorks() {
        return "dagger works";
    }
}
