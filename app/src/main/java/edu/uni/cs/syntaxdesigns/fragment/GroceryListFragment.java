package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.IngredientVo;
import edu.uni.cs.syntaxdesigns.adapter.GroceryListAdapter;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.database.cursor.RecipeCursor;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;
import edu.uni.cs.syntaxdesigns.event.DatabaseUpdateEvent;
import edu.uni.cs.syntaxdesigns.event.GroceryListFilterRecipesChangedEvent;
import edu.uni.cs.syntaxdesigns.fragment.filter.GroceryListFilterFragment;

import javax.inject.Inject;
import java.util.ArrayList;

public class GroceryListFragment extends FilteringFragment {

    private GroceryListFilterFragment mFilterFragment;

    private ListView mGroceryList;
    private LinearLayout mEmptyView;
    private TextView mEmptyViewText;

    @Inject IngredientsDao mIngredientsDao;
    @Inject RecipeDao mRecipeDao;
    @Inject Bus mBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        mFilterFragment = GroceryListFilterFragment.newInstance();
    }

    public static GroceryListFragment newInstance() {
        GroceryListFragment fragment = new GroceryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        mEmptyView = (LinearLayout) rootView.findViewById(R.id.grocery_list_empty_view);
        mEmptyViewText = (TextView) rootView.findViewById(R.id.grocery_list_empty_view_text);
        mGroceryList = (ListView) rootView.findViewById(R.id.grocery_list_list);
        populate();

        return rootView;
    }

    @Subscribe
    public void onDatabaseUpdate(DatabaseUpdateEvent event) {
        populate();
    }

    @Subscribe
    public void onGroceryListFilterUpdate(GroceryListFilterRecipesChangedEvent event) {
        populate();
    }

    public void populate() {
        ArrayList<IngredientVo> ingredients = new ArrayList<IngredientVo>();

        int recipeCount = 0;
        RecipeCursor recipeCursor = mRecipeDao.readRecipes();
        if (recipeCursor.moveToFirst()) {
            recipeCount = recipeCursor.getCount();
            do {
                if (recipeCursor.isEnabledInGroceryList()) {
                    readRecipes(ingredients, recipeCursor.readRowId());
                }
            } while (recipeCursor.moveToNext());
        }

        if (ingredients.size() == 0) {
            if (recipeCount == 0) {
                mEmptyViewText.setText(getResources().getString(R.string.empty_grocery_list));
            } else {
                mEmptyViewText.setText(getResources().getString(R.string.no_selected_recipes_grocery_list));
            }
            showEmptyView();
        } else {
            GroceryListAdapter groceryListAdapter = new GroceryListAdapter(getActivity(), ingredients);
            mGroceryList.setAdapter(groceryListAdapter);

            mEmptyView.setVisibility(View.GONE);
            mGroceryList.setVisibility(View.VISIBLE);
        }
    }

    private void showEmptyView() {
        mGroceryList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
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

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }
}
