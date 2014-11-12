package edu.uni.cs.syntaxdesigns.adapter;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipesAdapter extends BaseArrayAdapter {

    private ArrayList<RecipeIdVo> mRecipes;
    private Resources mResources;

    public SavedRecipesAdapter(Context context, ArrayList<RecipeIdVo> recipes) {
        super(context, 0, recipes);

        mRecipes = recipes;
        mResources = context.getResources();

        mInflater = LayoutInflater.from(context);
    }

    private static final class ViewHolder {
        TextView recipeName;
        TextView numberOfIngredients;
        TextView rating;
        TextView timeToCook;
        ImageView recipeImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.new_recipes_list_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.recipeName = (TextView) convertView.findViewById(R.id.recipe_name);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.rating);
            viewHolder.numberOfIngredients = (TextView) convertView.findViewById(R.id.number_of_ingredients);
            viewHolder.timeToCook = (TextView) convertView.findViewById(R.id.time_to_cook);
            viewHolder.recipeImage = (ImageView) convertView.findViewById(R.id.recipe_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RecipeIdVo recipe = mRecipes.get(position);

        viewHolder.recipeName.setText(recipe.name);
        viewHolder.numberOfIngredients.setText(" " + recipe.ingredientLines.size());
        viewHolder.timeToCook.setText(" " + recipe.totalTime);
        viewHolder.rating.setText(" " + Integer.toString(recipe.rating) + " " + mResources.getString(R.string.stars));

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
}
