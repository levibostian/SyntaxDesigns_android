package edu.uni.cs.syntaxdesigns.Adapters;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.adapter.BaseArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class IngredientsGridAdapter extends BaseArrayAdapter {

    private List<String> mIngredients;

    private IngredientsGridListener mCallback;
    private boolean mIsWithIngredientList;

    public IngredientsGridAdapter(Context context, List<String> ingredients, boolean isWithIngredientList) {
        super(context, 0, ingredients);

        mIngredients = ingredients;
        mIsWithIngredientList = isWithIngredientList;

        mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView ingredient;
        ImageView removeIngredient;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.filter_ingredient_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.ingredient = (TextView) convertView.findViewById(R.id.ingredient_name);
            viewHolder.removeIngredient = (ImageView) convertView.findViewById(R.id.delete_ingredient);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ingredient.setText(mIngredients.get(position));

        viewHolder.removeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsWithIngredientList) {
                    mCallback.removeWithIngredientFromFilterUtil(mIngredients.get(position));
                } else {
                    mCallback.removeWithoutIngredientFromtFilterUtil(mIngredients.get(position));
                }

                notifyDataSetChanged();
            }
        });

        return convertView;

    }

    public void setCallbacks(IngredientsGridListener callback) {
        mCallback = callback;
    }

    public interface IngredientsGridListener {
        void removeWithIngredientFromFilterUtil(String ingredient);
        void removeWithoutIngredientFromtFilterUtil(String ingredient);
    }
}
