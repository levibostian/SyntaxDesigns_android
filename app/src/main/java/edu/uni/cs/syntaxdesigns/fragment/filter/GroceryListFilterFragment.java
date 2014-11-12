package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.squareup.otto.Bus;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.RecipeVo;
import edu.uni.cs.syntaxdesigns.adapter.GroceryListFilterRecipesAdapter;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.cursor.RecipeCursor;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;
import edu.uni.cs.syntaxdesigns.event.GroceryListFilterRecipesChangedEvent;

import javax.inject.Inject;
import java.util.ArrayList;

public class GroceryListFilterFragment extends Fragment implements GroceryListFilterRecipesAdapter.RecipesListener {

    @Inject RecipeDao mRecipeDao;
    @Inject Bus mBus;

    private ListView mRecipesList;

    public static GroceryListFilterFragment newInstance() {
        GroceryListFilterFragment fragment = new GroceryListFilterFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery_list_filter, container, false);

        mRecipesList = (ListView) view.findViewById(R.id.grocery_list_filter_recipes_list);
        populate();

        return view;
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

    private void populate() {
        GroceryListFilterRecipesAdapter adapter = new GroceryListFilterRecipesAdapter(getActivity(), getRecipes());
        adapter.setListener(this);
        mRecipesList.setAdapter(adapter);
    }

    @Override
    public void onRecipeListChanged() {
        mBus.post(new GroceryListFilterRecipesChangedEvent());
    }

    private ArrayList<RecipeVo> getRecipes() {
        ArrayList<RecipeVo> recipes = new ArrayList<RecipeVo>();

        RecipeCursor recipeCursor = mRecipeDao.readRecipes();

        if (recipeCursor.moveToFirst()) {
            do {
                RecipeVo recipe = new RecipeVo();
                recipe.rowId = recipeCursor.readRowId();
                recipe.name = recipeCursor.readName();
                recipe.yummlyUrl = recipeCursor.readYummlyUrl();
                recipe.isFavorite = recipeCursor.isFavorite();
                recipe.isEnabledInGroceryList = recipeCursor.isEnabledInGroceryList();

                recipes.add(recipe);
            } while (recipeCursor.moveToNext());
        }

        recipeCursor.close();

        return recipes;
    }

}
