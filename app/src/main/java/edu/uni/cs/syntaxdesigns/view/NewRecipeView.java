package edu.uni.cs.syntaxdesigns.view;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

public class NewRecipeView extends LinearLayout {

    PhraseResults mResults;

    private TextView mRecipeName;
    private Button mSaveRecipe;
    private Context mContext;

    @Inject RecipeDao mRecipeDao;
    @Inject IngredientsDao mIngredientsDao;

    public NewRecipeView(Context context, PhraseResults results) {
        super(context);

        mResults = results;
        initialize(context);
    }

    public NewRecipeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize(context);
    }

    private void initialize(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.recipe_dialog_view, this);

        mContext = context;
        SyntaxDesignsApplication.inject(this);

        mRecipeName = (TextView) findViewById(R.id.recipe_name);
        mRecipeName.setText(mResults.recipeName);

        mSaveRecipe = (Button) findViewById(R.id.save_recipe);
        mSaveRecipe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe(mResults);
            }
        });
    }

    public void saveRecipe(PhraseResults recipe) {

        long recipeId = mRecipeDao.insertRecipe(recipe.recipeName, recipe.id);

        for (String ingredient : recipe.ingredients) {
            mIngredientsDao.insertIngredient(ingredient, false, recipeId);
        }

        Toast.makeText(mContext, "Recipe has been saved", Toast.LENGTH_SHORT).show();
    }
}
