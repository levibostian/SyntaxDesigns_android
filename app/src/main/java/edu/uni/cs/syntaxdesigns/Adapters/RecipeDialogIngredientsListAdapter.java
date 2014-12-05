package edu.uni.cs.syntaxdesigns.Adapters;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.adapter.BaseArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDialogIngredientsListAdapter extends BaseArrayAdapter {

    private List<String> mIngredients;
    private List<String> mCheckedItems;

    public RecipeDialogIngredientsListAdapter(Context context, List<String> ingredients) {
        super(context, 0, ingredients);

        mIngredients = ingredients;
        mCheckedItems = new ArrayList<String>();
        mInflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        CheckBox checkbox;
        TextView ingredient;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recipe_dialog_ingredient_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.ingredient = (TextView) convertView.findViewById(R.id.ingredient_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkbox.isChecked()) {
                    addCheckedItem(position);
                } else {
                    removeCheckedItem(position);
                }
            }
        });

        viewHolder.ingredient.setText(mIngredients.get(position));

        return convertView;
    }

    private void addCheckedItem(int position) {
        if(!mCheckedItems.contains(mIngredients.get(position))) {
            mCheckedItems.add(mIngredients.get(position));
        }

    }

    private void removeCheckedItem(int position) {
        if(mCheckedItems.contains(mIngredients.get(position))) {
            mCheckedItems.remove(mIngredients.get(position));
        }
    }

    public List<String> getCheckedItems() {
        return mCheckedItems;
    }
}
