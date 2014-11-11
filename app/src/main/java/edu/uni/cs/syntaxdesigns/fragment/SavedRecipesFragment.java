package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.database.cursor.RecipeCursor;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;
import edu.uni.cs.syntaxdesigns.fragment.filter.SavedRecipesFilterFragment;

import javax.inject.Inject;

public class SavedRecipesFragment extends FilteringFragment {

    private SavedRecipesFilterFragment mFilterFragment;

    private EditText mRecipeName;
    private EditText mIngredientName;
    private CheckBox mHaveIngredient;
    private CheckBox mFavoriteRecipe;
    private Button mAddDataButton;
    private TextView mRecipeNameRead;
    private TextView mIngredientNameRead;
    private TextView mHaveIngredientRead;
    private TextView mIsFavoriteRead;
    private Button mReadDataButton;
    private EditText mRecipeRowId;

    @Inject RecipeDao mRecipeDao;
    @Inject IngredientsDao mIngredientsDao;

    public static SavedRecipesFragment newInstance() {
        SavedRecipesFragment fragment = new SavedRecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        mFilterFragment = SavedRecipesFilterFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        mRecipeName = (EditText) rootView.findViewById(R.id.recipe_name);
        mIngredientName = (EditText) rootView.findViewById(R.id.ingredient_name);
        mHaveIngredient = (CheckBox) rootView.findViewById(R.id.have_ingredient);
        mFavoriteRecipe = (CheckBox) rootView.findViewById(R.id.favorite_recipe);
        mAddDataButton = (Button) rootView.findViewById(R.id.add_data_button);
        mRecipeNameRead = (TextView) rootView.findViewById(R.id.recipe_name_read);
        mIngredientNameRead = (TextView) rootView.findViewById(R.id.ingredient_name_read);
        mHaveIngredientRead = (TextView) rootView.findViewById(R.id.have_ingredient_read);
        mIsFavoriteRead = (TextView) rootView.findViewById(R.id.is_favorite_read);
        mReadDataButton = (Button) rootView.findViewById(R.id.read_data_button);
        mRecipeRowId = (EditText) rootView.findViewById(R.id.recipe_row_id);

        mAddDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToDatabase();
            }
        });

        mReadDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecipeRowId.getText() != null) {
                    readDataFromDatabase();
                } else {
                    Toast.makeText(getActivity(), "Enter recipe rowId to read", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void addDataToDatabase() {
        long recipeRowId = mRecipeDao.insertRecipe(String.valueOf(mRecipeName.getText()), "http:www.yummly.com/" + mRecipeName);

        mIngredientsDao.insertIngredient(String.valueOf(mIngredientName.getText()), mHaveIngredient.isChecked(), recipeRowId);
        mRecipeDao.favoriteRecipe(recipeRowId, mFavoriteRecipe.isChecked());

        Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
    }

    private void readDataFromDatabase() {
        RecipeCursor recipeCursor = mRecipeDao.readRecipeByRowId(Long.valueOf(String.valueOf(mRecipeRowId.getText())));
        recipeCursor.moveToFirst();
        IngredientsCursor ingredientsCursor = mIngredientsDao.readIngredientsForRecipe(recipeCursor.readRowId());
        ingredientsCursor.moveToFirst();

        mRecipeNameRead.setText(recipeCursor.readName());
        mIngredientNameRead.setText(ingredientsCursor.readName());
        mHaveIngredientRead.setText(ingredientsCursor.isHaveIt() ? "has it" : "does not have it");
        mIsFavoriteRead.setText(recipeCursor.isFavorite() ? "is favorite" : "not favorite");

        recipeCursor.close();
        ingredientsCursor.close();
    }


    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }
}
