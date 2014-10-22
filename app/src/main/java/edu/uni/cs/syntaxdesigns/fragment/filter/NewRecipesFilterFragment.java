package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;

public class NewRecipesFilterFragment extends Fragment {

    public static NewRecipesFilterFragment newInstance() {
        NewRecipesFilterFragment fragment = new NewRecipesFilterFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_recipes_filter, container, false);
    }

}
