package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.fragment.filter.SavedRecipesFilterFragment;

public class SavedRecipesFragment extends FilteringFragment {

    private SavedRecipesFilterFragment mFilterFragment;

    public static SavedRecipesFragment newInstance() {
        SavedRecipesFragment fragment = new SavedRecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFilterFragment = SavedRecipesFilterFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_recipes, container, false);

        return rootView;
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }
}
