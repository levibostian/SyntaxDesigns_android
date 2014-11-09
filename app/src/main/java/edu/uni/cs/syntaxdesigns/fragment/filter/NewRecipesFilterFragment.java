package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.util.FilterSearchUtil;

import javax.inject.Inject;
import java.util.ArrayList;

public class NewRecipesFilterFragment extends Fragment {

    private EditText mWithIngredients;
    private EditText mWithoutIngredients;
    private Button mAddWithIngredient;
    private Button mAddWithoutIngredient;
    private EditText mTime;
    private Button mAddTime;
    private CheckBox mMainDishCheckbox;
    private CheckBox mLunchAndSnackCheckbox;
    private CheckBox mBreakfastAndBrunchCheckbox;
    private CheckBox mAppetizers;
    private CheckBox mDesserts;
    private CheckBox mSideDishes;
    private CheckBox mSalad;
    private Button mClearFilters;

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
        mMainDishCheckbox = (CheckBox) view.findViewById(R.id.main_dish);
        mLunchAndSnackCheckbox = (CheckBox) view.findViewById(R.id.lunch_and_snack);
        mBreakfastAndBrunchCheckbox = (CheckBox) view.findViewById(R.id.breakfast_and_brunch);
        mAppetizers = (CheckBox) view.findViewById(R.id.appetizers);
        mDesserts = (CheckBox) view.findViewById(R.id.desserts);
        mSideDishes = (CheckBox) view.findViewById(R.id.side_dishes);
        mSalad = (CheckBox) view.findViewById(R.id.salad);
        mClearFilters = (Button) view.findViewById(R.id.clear_filters);
        mAddTime = (Button) view.findViewById(R.id.add_time);

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
                mFilterSearchUtil.withIngredient(mWithIngredients.getText().toString().toLowerCase());
                updateNewRecipeFragment();
            }
        });

        mAddWithoutIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterSearchUtil.withoutIngredient(mWithoutIngredients.getText().toString());
                updateNewRecipeFragment();
            }
        });

        mAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterSearchUtil.withTime(mTime.getText().toString());
                updateNewRecipeFragment();
            }
        });


        mClearFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterSearchUtil.clearFilters();
                updateNewRecipeFragment();
            }
        });
    }

    private void updateNewRecipeFragment() {
        mCallback.updateNewRecipeSearch(mFilterSearchUtil.getWithIngredients(), mFilterSearchUtil.getWithoutIngredients(),mFilterSearchUtil.getWithCourse(), mFilterSearchUtil.getTime());
    }

    public interface Callbacks {
        void updateNewRecipeSearch(ArrayList<String> withIngredients,
                                   ArrayList<String> withoutIngredients,
                                   ArrayList<String> withCourses,
                                   String withTime);
    }

}
