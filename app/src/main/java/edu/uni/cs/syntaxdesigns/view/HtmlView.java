package edu.uni.cs.syntaxdesigns.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ScrollView;

public class HtmlView extends ScrollView {

    private WebView mWebView;

    public HtmlView(Context context) {
        this(context, null);
    }

    public HtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inititalize(context);
    }

    public HtmlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        inititalize(context);
    }

    private void inititalize(Context context) {
        mWebView = new WebView(context);
        mWebView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        addView(mWebView);
    }

    public void setHtml(String html) {
        mWebView.loadUrl(html);
    }

}
