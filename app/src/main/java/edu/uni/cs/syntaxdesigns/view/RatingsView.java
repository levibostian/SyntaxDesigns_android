package edu.uni.cs.syntaxdesigns.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import edu.uni.cs.syntaxdesigns.R;

public class RatingsView extends LinearLayout {

    private ImageView mOneStar;
    private ImageView mTwoStar;
    private ImageView mThreeStar;
    private ImageView mFourStar;
    private ImageView mFiveStar;

    public RatingsView(Context context) {
        super(context);

        initialize(context);
    }

    public RatingsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    public RatingsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_ratings, this);

        mOneStar = (ImageView) findViewById(R.id.one_star);
        mTwoStar = (ImageView) findViewById(R.id.two_star);
        mThreeStar = (ImageView) findViewById(R.id.three_star);
        mFourStar = (ImageView) findViewById(R.id.four_star);
        mFiveStar = (ImageView) findViewById(R.id.five_star);
    }

    public void setRating(int rating) {
        int emptyStar = R.drawable.empty_star;
        int fullStar = R.drawable.full_star;

        mOneStar.setImageResource(rating >= 1 ? fullStar : emptyStar);
        mTwoStar.setImageResource(rating >= 2 ? fullStar : emptyStar);
        mThreeStar.setImageResource(rating >= 3 ? fullStar : emptyStar);
        mFourStar.setImageResource(rating >= 4 ? fullStar : emptyStar);
        mFiveStar.setImageResource(rating >= 5 ? fullStar : emptyStar);
    }

}
