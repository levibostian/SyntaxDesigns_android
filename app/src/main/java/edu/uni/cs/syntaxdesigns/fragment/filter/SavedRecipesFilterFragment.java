package edu.uni.cs.syntaxdesigns.fragment.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import edu.uni.cs.syntaxdesigns.R;

public class SavedRecipesFilterFragment extends Fragment {

    private SavedRecipesListener mListener;

    public static SavedRecipesFilterFragment newInstance() {
        SavedRecipesFilterFragment fragment = new SavedRecipesFilterFragment();

        return fragment;
    }

    public void setSavedRecipesListener(SavedRecipesListener listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_recipes_filter, container, false);

        final CheckBox favoriteCheck = (CheckBox) view.findViewById(R.id.favorites_filter);

        favoriteCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.filterByFavorite(favoriteCheck.isEnabled());
            }
        });

        return view;
    }

    public interface SavedRecipesListener {
        void filterByFavorite(boolean favoritesOnly);
    }

}
