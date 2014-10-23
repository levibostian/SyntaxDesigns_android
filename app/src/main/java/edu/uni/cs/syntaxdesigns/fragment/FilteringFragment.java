package edu.uni.cs.syntaxdesigns.fragment;

import android.support.v4.app.Fragment;

public abstract class FilteringFragment extends Fragment {

    private FilterFragmentListener mListener;

    public abstract Fragment getFilterFragment();

    public interface FilterFragmentListener {
        public void setFilterFragment(Fragment filter);
    }

    public void setFilterFragmentListener(FilterFragmentListener listener) {
        mListener = listener;

        mListener.setFilterFragment(getFilterFragment());
    }

}
