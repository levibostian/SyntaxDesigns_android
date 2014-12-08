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

import java.util.ArrayList;

public class SavedRecipesAdapter extends BaseArrayAdapter {

    private ArrayList<RecipeIdVo> mRecipes;
    private ArrayList<SavedRecipeVo> mSavedRecipeVos;
    private Resources mResources;
    private SavedRecipesListListener mSavedRecipesListListener;

    public SavedRecipesAdapter(Context context, ArrayList<RecipeIdVo> recipes, ArrayList<SavedRecipeVo> savedRecipeVos) {
        super(context, 0, recipes);

        mRecipes = recipes;
        mResources = context.getResources();
        mSavedRecipeVos = savedRecipeVos;

        mInflater = LayoutInflater.from(context);
    }

    private static final class ViewHolder {
        TextView recipeName;
        TextView numberOfIngredients;
        TextView rating;
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
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rating);
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
        viewHolder.numberOfIngredients.setText(" " + recipe.ingredientLines.size());
        viewHolder.timeToCook.setText(" " + recipe.totalTime);
        viewHolder.rating.setText(" " + Integer.toString(recipe.rating) + " " + mResources.getString(R.string.stars));
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
