package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import edu.uni.cs.syntaxdesigns.Adapters.IngredientsListAdapter;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.util.FilterSearchUtil;
import edu.uni.cs.syntaxdesigns.view.ExpandedListView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class NewRecipesFilterFragment extends Fragment implements IngredientsListAdapter.IngredientsGridListener {

    private static final String EMPTY_STRING = "";

    private EditText mWithIngredients;
    private EditText mWithoutIngredients;
    private Button mAddWithIngredient;
    private Button mAddWithoutIngredient;
    private EditText mTime;
    private Button mAddTime;
    private Button mClearFilters;
    private ExpandedListView mWithIngredientsList;
    private ExpandedListView mWithoutIngredientsList;
    private IngredientsListAdapter mWithIngredientsAdapter;
    private IngredientsListAdapter mWithoutIngredientsAdapter;

    private Callbacks mCallback;

    @Inject FilterSearchUtil mFilterSearchUtil;

    public static NewRecipesFilterFragment newInstance() {
        NewRecipesFilterFragment fragment = new NewRecipesFilterFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipes_filter, container, false);

        mWithIngredients = (EditText) view.findViewById(R.id.with_ingredient_edit_text);
        mWithoutIngredients = (EditText) view.findViewById(R.id.without_ingredient_edit_text);
        mAddWithIngredient = (Button) view.findViewById(R.id.with_ingredient_button);
        mAddWithoutIngredient = (Button) view.findViewById(R.id.without_ingredient_button);
        mTime = (EditText) view.findViewById(R.id.time_in_minutes);
        mClearFilters = (Button) view.findViewById(R.id.clear_filters);
        mAddTime = (Button) view.findViewById(R.id.add_time);
        mWithIngredientsList = (ExpandedListView) view.findViewById(R.id.with_ingredients_list);
        mWithoutIngredientsList = (ExpandedListView) view.findViewById(R.id.without_ingredients_list);

        SyntaxDesignsApplication.inject(this);

        initializeOnClickHandlers();

        return view;
    }

    public void setCallback(Callbacks callback) {
        mCallback = callback;
    }

    private void initializeOnClickHandlers() {
        mAddWithIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mWithIngredients.getText().toString().matches(EMPTY_STRING)) {
                    mFilterSearchUtil.withIngredient(mWithIngredients.getText().toString().toLowerCase());

                    if (mWithIngredientsAdapter != null) {
                        mWithIngredientsAdapter.notifyDataSetChanged();
                    } else {
                        initializeWithIngredientsGrid(mFilterSearchUtil.getWithIngredients());
                    }

                    mWithIngredients.setText(EMPTY_STRING);

                    updateNewRecipeFragment();
                }
            }
        });

        mAddWithoutIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mWithoutIngredients.getText().toString().matches(EMPTY_STRING)) {
                    mFilterSearchUtil.withoutIngredient(mWithoutIngredients.getText().toString().toLowerCase());

                    if(mWithoutIngredientsAdapter != null) {
                        mWithoutIngredientsAdapter.notifyDataSetChanged();
                    } else {
                        initializeWithoutIngredientsGrid(mFilterSearchUtil.getWithoutIngredients());
                    }

                    mWithoutIngredients.setText(EMPTY_STRING);

                    updateNewRecipeFragment();
                }
            }
        });

        mAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTime.getText().toString().matches(EMPTY_STRING)) {
                    mFilterSearchUtil.withTime(mTime.getText().toString());
                    updateNewRecipeFragment();
                }
            }
        });

        mClearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWithIngredients.setText(EMPTY_STRING);
                mWithoutIngredients.setText(EMPTY_STRING);
                mTime.setText(EMPTY_STRING);
                mFilterSearchUtil.clearFilters();
                if (mWithIngredientsAdapter != null) {
                    mWithIngredientsAdapter.notifyDataSetChanged();
                }

                if (mWithoutIngredientsAdapter != null) {
                    mWithoutIngredientsAdapter.notifyDataSetChanged();
                }
                updateNewRecipeFragment();
            }
        });
    }

    private void initializeWithIngredientsGrid(List<String> ingredients) {
        mWithIngredientsAdapter = new IngredientsListAdapter(getActivity(), ingredients, true);
        mWithIngredientsAdapter.setCallbacks(this);

        mWithIngredientsList.setAdapter(mWithIngredientsAdapter);
    }

    private void initializeWithoutIngredientsGrid(List<String> ingredients) {
        mWithoutIngredientsAdapter = new IngredientsListAdapter(getActivity(), ingredients, false);
        mWithoutIngredientsAdapter.setCallbacks(this);

        mWithoutIngredientsList.setAdapter(mWithoutIngredientsAdapter);
    }

    private void updateNewRecipeFragment() {
        mCallback.updateNewRecipeSearch(mFilterSearchUtil.getWithIngredients(), mFilterSearchUtil.getWithoutIngredients(),mFilterSearchUtil.getWithCourse(), mFilterSearchUtil.getTime());
    }

    @Override
    public void removeWithIngredientFromFilterUtil(String ingredient) {
        mFilterSearchUtil.removeWithIngredient(ingredient);
        updateNewRecipeFragment();
    }

    @Override
    public void removeWithoutIngredientFromtFilterUtil(String ingredient) {
        mFilterSearchUtil.removeWithoutIngredient(ingredient);
    }

    public interface Callbacks {
        void updateNewRecipeSearch(ArrayList<String> withIngredients,
                                   ArrayList<String> withoutIngredients,
                                   ArrayList<String> withCourses,
                                   String withTime);
    }

}
