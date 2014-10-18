package edu.uni.cs.syntaxdesigns.fragment;

import edu.uni.cs.syntaxdesigns.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SavedRecipesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "savedRecipes.section_number";

    public static SavedRecipesFragment newInstance(int sectionNumber) {
        SavedRecipesFragment fragment = new SavedRecipesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        return rootView;
    }
}
