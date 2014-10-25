package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;

public class GroceryListFilterFragment extends Fragment {

    public static GroceryListFilterFragment newInstance() {
        GroceryListFilterFragment fragment = new GroceryListFilterFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_list_filter, container, false);
    }

}
