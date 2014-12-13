package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.cocosw.undobar.UndoBarController;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.IngredientVo;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import edu.uni.cs.syntaxdesigns.VOs.SavedRecipeVo;
import edu.uni.cs.syntaxdesigns.adapter.SavedRecipesAdapter;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.database.cursor.RecipeCursor;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;
import edu.uni.cs.syntaxdesigns.event.DatabaseUpdateEvent;
import edu.uni.cs.syntaxdesigns.event.GroceryListUpdateEvent;
import edu.uni.cs.syntaxdesigns.fragment.dialog.SavedRecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.SavedRecipesFilterFragment;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;

public class SavedRecipesFragment extends FilteringFragment implements SavedRecipesAdapter.SavedRecipesListListener {

    private static final String SAVED_RECIPE_DIALOG = "savedRecipeFragment.savedRecipeDialog";

    private SavedRecipesFilterFragment mFilterFragment;
    private ListView mListView;
    private LinearLayout mEmptyView;
    private SavedRecipesAdapter mSavedRecipesAdapter;
    private ArrayList<SavedRecipeVo> mSavedRecipes;

    @Inject RecipeDao mRecipeDao;
    @Inject IngredientsDao mIngredientsDao;
    @Inject YummlyApi mYummlyApi;
    @Inject Bus mBus;

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
    public void onResume() {
        super.onResume();

        mBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        mBus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        mListView = (ListView) rootView.findViewById(R.id.saved_recipes_list);
        mEmptyView = (LinearLayout) rootView.findViewById(R.id.saved_recipes_list_empty_view);

        populate();

        return rootView;
    }

    private void populate() {
        mSavedRecipes = new ArrayList<SavedRecipeVo>();
        mSavedRecipes = getRecipes();
        getRecipesById(mSavedRecipes);
    }

    private void initializeSavedRecipesAdapter(ArrayList<RecipeIdVo> recipes) {
        if (mSavedRecipes.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
        }
        mSavedRecipesAdapter = new SavedRecipesAdapter(getActivity(), recipes, mSavedRecipes, mIngredientsDao);
        mSavedRecipesAdapter.setListener(this);
        mListView.setAdapter(mSavedRecipesAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SavedRecipeDialogFragment.newInstance(mSavedRecipesAdapter.getItem(position), mSavedRecipes.get(position)).show(getActivity().getFragmentManager(), SAVED_RECIPE_DIALOG);
            }
        });
    }

    private void getRecipesById(ArrayList<SavedRecipeVo> savedRecipes) {
        getRecipesByIdHelper(savedRecipes, new ArrayList<RecipeIdVo>());
    }

    private void getRecipesByIdHelper(final ArrayList<SavedRecipeVo> savedRecipes, final ArrayList<RecipeIdVo> recipeIds) {
        if (savedRecipes.size() == recipeIds.size()) {
            initializeSavedRecipesAdapter(recipeIds);
        } else {
            SavedRecipeVo savedRecipe = savedRecipes.get(recipeIds.size());
            mYummlyApi.searchByRecipeId(savedRecipe.yummlyUrl,
                                        YummlyUtil.getApplicationId(getActivity()),
                                        YummlyUtil.getApplicationKey(getActivity()),
                                        new Callback<RecipeIdVo>() {
                                            @Override
                                            public void success(RecipeIdVo recipeIdVo, Response response) {
                                                recipeIds.add(recipeIdVo);
                                                getRecipesByIdHelper(savedRecipes, recipeIds);
                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Toast.makeText(getActivity(), getString(R.string.yummly_error), Toast.LENGTH_SHORT).show();
                                            }
                                        });
        }
    }

    private ArrayList<SavedRecipeVo> getRecipes() {
        ArrayList<SavedRecipeVo> recipes = new ArrayList<SavedRecipeVo>();
        RecipeCursor cursor = mRecipeDao.readRecipes();

        if (cursor.moveToFirst()) {
            do {
                SavedRecipeVo recipe = new SavedRecipeVo();
                recipe.recipeName = cursor.readName();
                recipe.rowId = cursor.readRowId();
                recipe.yummlyUrl = cursor.readYummlyUrl();
                recipe.isFavorite = cursor.isFavorite();

                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return recipes;
    }

    @Subscribe
    public void onDatabaseUpdatedEvent(DatabaseUpdateEvent event) {
        populate();
    }

    @Subscribe
    public void onGroceryListUpdatedEvent(GroceryListUpdateEvent event) {
        populate();
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }

    @Override
    public void onRecipeDaoUpdate(long id, boolean isFavorite) {
        mRecipeDao.favoriteRecipe(id, isFavorite);
    }

    @Override
    public void onRecipeDeleted(long rowId, int listPos) {
        final ArrayList<IngredientVo> ingredients = new ArrayList<IngredientVo>();
        RecipeCursor recipeCursor = mRecipeDao.readRecipeByRowId(rowId);
        if (recipeCursor.moveToFirst()) {
            do {
                if (recipeCursor.isEnabledInGroceryList()) {
                    readRecipes(ingredients, recipeCursor.readRowId());
                }
            } while (recipeCursor.moveToNext());
        }

        mRecipeDao.deleteRecipe(rowId);
        mIngredientsDao.deleteIngredientsForRecipe(rowId);
        final SavedRecipeVo deletedRecipe = mSavedRecipes.remove(listPos);
        mSavedRecipesAdapter.notifyDataSetChanged();

        new UndoBarController.UndoBar(getActivity()).message("Recipe deleted.").listener(new UndoBarController.UndoListener() {
            @Override
            public void onUndo(Parcelable parcelable) {
                mRecipeDao.insertRecipe(deletedRecipe.recipeName, deletedRecipe.yummlyUrl);

                for (IngredientVo ingredient : ingredients) {
                    mIngredientsDao.insertIngredient(ingredient.name, ingredient.haveIt, ingredient.recipeId);
                }

                mSavedRecipes.add(deletedRecipe);

                mBus.post(new DatabaseUpdateEvent());

                populate();
            }
        }).show();

        mBus.post(new DatabaseUpdateEvent());

        populate();
    }

    private void readRecipes(ArrayList<IngredientVo> ingredients, long recipeRowId) {
        IngredientsCursor cursor = mIngredientsDao.readIngredientsForRecipe(recipeRowId);

        if (cursor.moveToFirst()) {
            do {
                IngredientVo ingredient = new IngredientVo();
                ingredient.rowId = cursor.readRowId();
                ingredient.name = cursor.readName();
                ingredient.haveIt = cursor.isHaveIt();
                ingredient.recipeId = cursor.readRecipeId();

                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

}
