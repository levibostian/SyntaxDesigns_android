package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.fragment.filter.GroceryListFilterFragment;

public class GroceryListFragment extends FilteringFragment {

    private GroceryListFilterFragment mFilterFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFilterFragment = GroceryListFilterFragment.newInstance();
    }

    public static GroceryListFragment newInstance() {
        GroceryListFragment fragment = new GroceryListFragment();
        Bundle args = new Bundle();
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
        return mFilterFragment;
    }
}
