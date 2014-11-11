package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.IngredientVo;
import edu.uni.cs.syntaxdesigns.adapter.GroceryListAdapter;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.event.DatabaseUpdateEvent;
import edu.uni.cs.syntaxdesigns.fragment.filter.GroceryListFilterFragment;
import edu.uni.cs.syntaxdesigns.util.LogUtil;

import javax.inject.Inject;
import java.util.ArrayList;

public class GroceryListFragment extends FilteringFragment {

    private GroceryListFilterFragment mFilterFragment;

    private ListView mGroceryList;

    @Inject IngredientsDao mIngredientsDao;
    @Inject Bus mBus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        mBus.register(this);
        mFilterFragment = GroceryListFilterFragment.newInstance();
    }

    public static GroceryListFragment newInstance() {
        GroceryListFragment fragment = new GroceryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();

        mBus.unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        mGroceryList = (ListView) rootView.findViewById(R.id.grocery_list_list);
        populate();

        return rootView;
    }

    @Subscribe
    public void onDatabaseUpdate(DatabaseUpdateEvent event) {
        populate();
    }

    public void populate() {
        LogUtil.d("populating");
        ArrayList<IngredientVo> ingredients = getIngredients();
        GroceryListAdapter groceryListAdapter = new GroceryListAdapter(getActivity(), ingredients);
        mGroceryList.setAdapter(groceryListAdapter);
    }

    private ArrayList<IngredientVo> getIngredients() {
        ArrayList<IngredientVo> ingredients = new ArrayList<IngredientVo>();
        IngredientsCursor cursor = mIngredientsDao.readIngredients();

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

        return ingredients;
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }
}
