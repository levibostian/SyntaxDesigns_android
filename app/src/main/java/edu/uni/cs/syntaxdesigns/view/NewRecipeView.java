package edu.uni.cs.syntaxdesigns.view;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewRecipeView extends LinearLayout {

    PhraseResults mResults;

    private TextView mRecipeName;

    public NewRecipeView(Context context, PhraseResults results) {
        super(context);

        mResults = results;
        initialize(context);
    }

    public NewRecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.recipe_dialog_view, this);

        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mRecipeName.setText(mResults.recipeName);
    }
}
