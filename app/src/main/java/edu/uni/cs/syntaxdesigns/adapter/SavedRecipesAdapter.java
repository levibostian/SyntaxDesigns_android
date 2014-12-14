package edu.uni.cs.syntaxdesigns.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import edu.uni.cs.syntaxdesigns.VOs.SavedRecipeVo;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.view.RatingsView;

import java.util.ArrayList;

public class SavedRecipesAdapter extends BaseArrayAdapter {

    private ArrayList<RecipeIdVo> mRecipes;
    private ArrayList<SavedRecipeVo> mSavedRecipeVos;
    private Resources mResources;
    private SavedRecipesListListener mSavedRecipesListListener;
    private IngredientsDao mIngredientsDao;

    public SavedRecipesAdapter(Context context, ArrayList<RecipeIdVo> recipes, ArrayList<SavedRecipeVo> savedRecipeVos, IngredientsDao ingredientsDao) {
        super(context, 0, recipes);

        mRecipes = recipes;
        mResources = context.getResources();
        mSavedRecipeVos = savedRecipeVos;
        mIngredientsDao = ingredientsDao;

        mInflater = LayoutInflater.from(context);
    }

    private static final class ViewHolder {
        TextView recipeName;
        TextView numberOfIngredients;
        RatingsView rating;
        TextView timeToCook;
        ImageView recipeImage;
        CheckBox star;
        LinearLayout favorite;
        LinearLayout delete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.saved_recipe_list_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.recipeName = (TextView) convertView.findViewById(R.id.recipe_name);
            viewHolder.rating = (RatingsView) convertView.findViewById(R.id.saved_recipes_rating);
            viewHolder.numberOfIngredients = (TextView) convertView.findViewById(R.id.number_of_ingredients);
            viewHolder.timeToCook = (TextView) convertView.findViewById(R.id.time_to_cook);
            viewHolder.recipeImage = (ImageView) convertView.findViewById(R.id.recipe_image);
            viewHolder.favorite = (LinearLayout) convertView.findViewById(R.id.favorite);
            viewHolder.star = (CheckBox) convertView.findViewById(R.id.favorite_star);
            viewHolder.delete = (LinearLayout) convertView.findViewById(R.id.delete_recipe);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RecipeIdVo recipe = mRecipes.get(position);

        viewHolder.recipeName.setText(recipe.name);
        viewHolder.numberOfIngredients.setText(" " + getNumberOfIngredientsHave(mSavedRecipeVos.get(position).rowId) + "/" + recipe.ingredientLines.size());
        colorIngredientCount(viewHolder.numberOfIngredients, mSavedRecipeVos.get(position).rowId, recipe);
        viewHolder.timeToCook.setText(" " + recipe.totalTime);
        viewHolder.rating.setRating(recipe.rating);
        viewHolder.star.setChecked(mSavedRecipeVos.get(position).isFavorite);

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star.setChecked(!viewHolder.star.isChecked());
                mSavedRecipeVos.get(position).isFavorite = viewHolder.star.isChecked();
                mSavedRecipesListListener.onRecipeDaoUpdate(position + 1, viewHolder.star.isChecked());
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecipes.remove(position);
                mSavedRecipesListListener.onRecipeDeleted(mSavedRecipeVos.get(position).rowId, position);
            }
        });

        Picasso.with(getContext()).load(recipe.images.get(0).hostedMediumUrl)
               .placeholder(R.drawable.ic_launcher)
               .error(R.drawable.ic_launcher)
               .fit()
               .centerCrop()
               .into(viewHolder.recipeImage);

        return convertView;
    }

    private void colorIngredientCount(TextView numberOfIngredients, long rowId, RecipeIdVo recipe) {
        Resources resources = getContext().getResources();

        if (getNumberOfIngredientsHave(rowId) == recipe.ingredientLines.size()) {
            numberOfIngredients.setTextColor(resources.getColor(R.color.all_ingredients));
        } else if (getNumberOfIngredientsHave(rowId) == 0) {
            numberOfIngredients.setTextColor(resources.getColor(R.color.no_ingredients));
        } else {
            numberOfIngredients.setTextColor(resources.getColor(android.R.color.black));
        }
    }

    private int getNumberOfIngredientsHave(long rowId) {
        IngredientsCursor cursor = mIngredientsDao.readIngredientsForRecipe(rowId);

        int numberHave = 0;
        if (cursor.moveToFirst()) {
            do {
                if (cursor.isHaveIt()) {
                    numberHave += 1;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        return numberHave;
    }

    @Override
    public RecipeIdVo getItem(int position) {
        return mRecipes.get(position);
    }

    public void setListener(SavedRecipesListListener listener) {
        mSavedRecipesListListener = listener;
    }

    public interface SavedRecipesListListener {
        void onRecipeDaoUpdate(long id, boolean isFavorite);
        void onRecipeDeleted(long rowId, int listPos);
    }
}
