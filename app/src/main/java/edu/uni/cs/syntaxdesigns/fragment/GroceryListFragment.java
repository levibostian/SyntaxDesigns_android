package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.fragment.filter.GroceryListFilterFragment;

public class GroceryListFragment extends FilteringFragment {

    private static final String ARG_SECTION_NUMBER = "groceryList.section_number";

    public static GroceryListFragment newInstance(int sectionNumber) {
        GroceryListFragment fragment = new GroceryListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        return rootView;
    }

    @Override
    public Fragment getFilterFragment() {
        return GroceryListFilterFragment.newInstance();
    }
}
