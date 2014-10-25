package edu.uni.cs.syntaxdesigns.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.uni.cs.syntaxdesigns.R;

public class FilterDrawerFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, Fragment filterFragment) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        setFilterFragment(filterFragment);
    }

    public void setFilterFragment(Fragment filter) {
        setFilter(filter);
    }

    private void setFilter(Fragment filter) {
        getActivity().getSupportFragmentManager()
                     .beginTransaction()
                     .replace(R.id.filter_content, filter)
                     .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_drawer, container, true);
    }

}
