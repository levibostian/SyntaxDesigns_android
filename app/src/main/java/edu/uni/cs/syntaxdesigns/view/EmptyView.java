package edu.uni.cs.syntaxdesigns.view;

import edu.uni.cs.syntaxdesigns.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EmptyView extends LinearLayout {

    private TextView mEmptyTextView;
    private ProgressBar mProgressBar;

    public EmptyView(Context context) {
        super(context);

        initialize(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_empty, this);

        mEmptyTextView = (TextView) findViewById(R.id.message);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
    }

    public void setText(String message, int progressVisibility) {
        mEmptyTextView.setText(message);
        mProgressBar.setVisibility(progressVisibility);
    }
}
