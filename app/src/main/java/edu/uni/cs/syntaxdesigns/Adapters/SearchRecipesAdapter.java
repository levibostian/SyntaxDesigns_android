package edu.uni.cs.syntaxdesigns.Adapters;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import org.w3c.dom.Text;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchRecipesAdapter extends BaseArrayAdapter {

    private List<PhraseResults> mResults;

    public SearchRecipesAdapter(Context context, List<PhraseResults> results) {
        super(context, 0, results);

        mResults = results;

        mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView numberOfIngredients;
        TextView recipeName;
        TextView rating;
        TextView timeToCook;
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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhraseResults results = mResults.get(position);

        viewHolder.recipeName.setText(results.recipeName);
        viewHolder.rating.setText(" " + Integer.toString(results.rating));
        viewHolder.numberOfIngredients.setText(" " + Integer.toString(results.ingredients.size()));
        viewHolder.timeToCook.setText(" " + Integer.toString(results.totalTimeInSeconds / 60) + " ");

        return convertView;
    }
}
