package edu.uni.cs.syntaxdesigns.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.squareup.otto.Bus;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.IngredientVo;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.event.GroceryListUpdateEvent;

import javax.inject.Inject;
import java.util.ArrayList;

public class GroceryListAdapter extends ArrayAdapter<IngredientVo> {

    @Inject IngredientsDao mIngredientsDao;
    @Inject Bus mBus;

    private Context mContext;
    private ArrayList<IngredientVo> mIngredients;

    public GroceryListAdapter(Context context, ArrayList<IngredientVo> ingredients) {
        super(context, 0, ingredients);

        SyntaxDesignsApplication.inject(this);

        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grocery_list_row, parent, false);
        }

        final IngredientVo ingredient = mIngredients.get(position);

        ((TextView) convertView.findViewById(R.id.ingredient_name)).setText(ingredient.name);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.ingredient_checkbox);
        checkBox.setChecked(ingredient.haveIt);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredient.haveIt = checkBox.isChecked();
                updateIngredientStatus(position + 1, checkBox.isChecked());

                mBus.post(new GroceryListUpdateEvent());
            }
        });

        return convertView;
    }

    private void updateIngredientStatus(int id, boolean haveIt) {
        mIngredientsDao.updateHaveIt(id, haveIt);
    }

}
