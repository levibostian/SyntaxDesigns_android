package edu.uni.cs.syntaxdesigns.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.RecipeVo;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GroceryListFilterRecipesAdapter extends BaseArrayAdapter<RecipeVo> {

    @Inject RecipeDao mRecipeDao;
    private ArrayList<RecipeVo> mRecipes;

    private RecipesListener mListener;
    private Context mContext;

    public interface RecipesListener {
        public void onRecipeListChanged();
    }

    public GroceryListFilterRecipesAdapter(Context context, List<RecipeVo> recipes) {
        super(context, 0, recipes);

        SyntaxDesignsApplication.inject(this);
        mRecipes = (ArrayList<RecipeVo>) recipes;
        mContext = context;
    }

    public void setListener(RecipesListener listener) {
        mListener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grocery_list_filter_row, parent, false);
        }

        final RecipeVo recipe = mRecipes.get(position);

        ((TextView) convertView.findViewById(R.id.filter_recipe_name)).setText(recipe.name);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.filter_recipe_checkbox);
        checkBox.setChecked(recipe.isEnabledInGroceryList);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipe.isEnabledInGroceryList = checkBox.isChecked();
                updateRecipeEnabledInGroceryListStatus(position + 1, checkBox.isChecked());
            }
        });

        return convertView;
    }

    private void updateRecipeEnabledInGroceryListStatus(int id, boolean isEnabled) {
        if (mListener == null) {
            throw new IllegalStateException("You forgot to set the listener in " + getClass().getSimpleName());
        }

        mRecipeDao.enabledInGroceryList(id, isEnabled);
        mListener.onRecipeListChanged();
    }
}
