package edu.uni.cs.syntaxdesigns.fragment.dialog;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.view.HtmlView;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

public class WebViewDialogFragment extends DialogFragment {

    private static final String RECIPE_SOURCE_URL = "webView.sourceUrl";

    private HtmlView mHtmlView;

    private String mSourceUrl;

    public static WebViewDialogFragment newInstance(String sourceUrl) {
        Bundle bundle = new Bundle();
        bundle.putString(RECIPE_SOURCE_URL, sourceUrl);

        WebViewDialogFragment fragment = new WebViewDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getBundleExtras(savedInstanceState != null ? savedInstanceState : getArguments());

        mHtmlView = new HtmlView(getActivity());
        mHtmlView.setHtml(mSourceUrl);

        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.abc_ic_clear_normal);
        imageView.setColorFilter(getResources().getColor(android.R.color.black));

        final Dialog dialog = new AlertDialog.Builder(getActivity())
                                .setView(mHtmlView).setCustomTitle(imageView).create();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    private void getBundleExtras(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSourceUrl = savedInstanceState.getString(RECIPE_SOURCE_URL);
        }
    }
}
