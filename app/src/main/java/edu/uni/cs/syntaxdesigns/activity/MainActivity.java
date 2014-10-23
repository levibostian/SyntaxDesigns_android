package edu.uni.cs.syntaxdesigns.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.fragment.FilterDrawerFragment;
import edu.uni.cs.syntaxdesigns.fragment.FilteringFragment;
import edu.uni.cs.syntaxdesigns.fragment.GroceryListFragment;
import edu.uni.cs.syntaxdesigns.fragment.NewRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.SavedRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.NewRecipesFilterFragment;

import java.util.Locale;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, FilteringFragment.FilterFragmentListener {

    private FilterDrawerFragment mFilterDrawerFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFilterDrawerFragment = (FilterDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mFilterDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), NewRecipesFilterFragment.newInstance());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                mDrawerLayout.closeDrawer(Gravity.RIGHT);

                ((FilteringFragment) mSectionsPagerAdapter.getItem(position)).setFilterFragmentListener(MainActivity.this);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                                             .setText(mSectionsPagerAdapter.getPageTitle(i))
                                             .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mFilterDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_filter:
                if (mFilterDrawerFragment.isDrawerOpen()) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void setFilterFragment(Fragment filter) {
        mFilterDrawerFragment.setFilterFragment(filter);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private NewRecipesFragment mNewRecipesFragment;
        private GroceryListFragment mGroceryListFragment;
        private SavedRecipesFragment mSavedRecipesFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            mNewRecipesFragment = NewRecipesFragment.newInstance();
            mGroceryListFragment = GroceryListFragment.newInstance();
            mSavedRecipesFragment = SavedRecipesFragment.newInstance();
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return mNewRecipesFragment;
                case 1:
                    return mGroceryListFragment;
                case 2:
                    return mSavedRecipesFragment;
                default:
                    return mNewRecipesFragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.find_recipes).toUpperCase(l);
                case 1:
                    return getString(R.string.grocery_list).toUpperCase(l);
                case 2:
                    return getString(R.string.saved_recipes).toUpperCase(l);
            }
            return null;
        }
    }

}
