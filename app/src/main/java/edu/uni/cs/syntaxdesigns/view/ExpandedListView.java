package edu.uni.cs.syntaxdesigns.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExpandedListView extends ListView {

    public ExpandedListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDividerHeight(0);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
