package edu.uni.cs.syntaxdesigns.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.SearchByPhraseVo;
import edu.uni.cs.syntaxdesigns.activity.MainActivity;
import edu.uni.cs.syntaxdesigns.adapter.BrowseRecipesListAdapter;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.fragment.dialog.RecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.BrowseRecipesFilterFragment;
import edu.uni.cs.syntaxdesigns.util.ImageUtil;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import edu.uni.cs.syntaxdesigns.view.EmptyView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;
import java.util.ArrayList;

public class BrowseRecipesFragment extends FilteringFragment implements BrowseRecipesFilterFragment.Callbacks {

    private static final String DEFAULT_NEW_RECIPES_SEARCH = "popular";

    private BrowseRecipesFilterFragment mFilterFragment;
    private ListView mListView;
    private BrowseRecipesListAdapter mAdapter;
    private EmptyView mEmptyView;
    private Button mRetry;
    private Resources mResources;
    private TextView mCurrentSearch;
    private Button mClearSearch;

    private String mSearchPhrase;

    private static final String RECIPE_DIALOG = "browseRecipes.recipeDialog";

    @Inject ImageUtil mImageUtil;
    @Inject YummlyApi mYummlyApi;

    public static BrowseRecipesFragment newInstance() {
        BrowseRecipesFragment fragment = new BrowseRecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        mFilterFragment = (BrowseRecipesFilterFragment) ((MainActivity) getActivity()).getFilterFragment();
        mFilterFragment.setCallback(this);
    }

    public void startSearchByPhrase(final String searchPhrase) {
        initializeEmptyView(mResources.getString(R.string.loading), View.VISIBLE);

        mSearchPhrase = searchPhrase;
        mYummlyApi.searchByPhrase(YummlyUtil.getApplicationId(getActivity()),
                                  YummlyUtil.getApplicationKey(getActivity()),
                                  searchPhrase,
                                  new Callback<SearchByPhraseVo>() {
                                      @Override
                                      public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                                          if (!mSearchPhrase.matches(DEFAULT_NEW_RECIPES_SEARCH)) {
                                              mClearSearch.setVisibility(View.VISIBLE);
                                          } else {
                                              mClearSearch.setVisibility(View.INVISIBLE);
                                          }

                                          mCurrentSearch.setText(getString(R.string.current_search) + " " + searchPhrase);
                                          initializeListViewAdapter(searchByPhraseVo);
                                      }

                                      @Override
                                      public void failure(RetrofitError error) {
                                          initializeEmptyView(mResources.getString(R.string.yummly_error), View.INVISIBLE);
                                          mRetry.setVisibility(View.VISIBLE);
                                          mRetry.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mRetry.setVisibility(View.INVISIBLE);
                                                  startSearchByPhrase(mSearchPhrase);
                                              }
                                          });
                                      }
                                  });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse_recipes, container, false);

        mListView = (ListView) rootView.findViewById(R.id.new_recipe_list_view);
        mEmptyView = (EmptyView) rootView.findViewById(R.id.new_recipes_empty_view);
        mRetry = (Button) rootView.findViewById(R.id.retry);
        mCurrentSearch = (TextView) rootView.findViewById(R.id.current_search);
        mClearSearch = (Button) rootView.findViewById(R.id.clear_search);
        mClearSearch.setVisibility(View.INVISIBLE);
        mResources = getResources();

        mSearchPhrase = DEFAULT_NEW_RECIPES_SEARCH;

        mClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchByPhrase(DEFAULT_NEW_RECIPES_SEARCH);
            }
        });

        initializeListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDialogFragment.newInstance(mAdapter.getItem(position)).show(getFragmentManager(), RECIPE_DIALOG);
            }
        });

        return rootView;
    }

    private void initializeListView() {
        initializeEmptyView(mResources.getString(R.string.loading), View.VISIBLE);

        mYummlyApi.searchByPhrase(YummlyUtil.getApplicationId(getActivity()),
                                  YummlyUtil.getApplicationKey(getActivity()),
                                  mSearchPhrase,
                                  new Callback<SearchByPhraseVo>() {
                                      @Override
                                      public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                                          mCurrentSearch.setText(getString(R.string.current_search) + " " + mSearchPhrase);
                                          initializeListViewAdapter(searchByPhraseVo);
                                      }

                                      @Override
                                      public void failure(RetrofitError error) {
                                          initializeEmptyView(mResources.getString(R.string.yummly_error), View.INVISIBLE);
                                          mRetry.setVisibility(View.VISIBLE);
                                          mRetry.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  mRetry.setVisibility(View.INVISIBLE);
                                                  initializeListView();
                                              }
                                          });
                                      }
                                  });
    }

    private void initializeListViewAdapter(SearchByPhraseVo searchByPhraseResults) {
        mAdapter = new BrowseRecipesListAdapter(getActivity(), searchByPhraseResults.getPhraseResults());
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);

        mEmptyView.setText(mResources.getString(R.string.no_results), View.INVISIBLE);
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }

    @Override
    public void updateNewRecipeSearch(final ArrayList<String> withIngredients, final ArrayList<String> withoutIngredients, final ArrayList<String> withCourses, final String withTime) {
        initializeEmptyView(mResources.getString(R.string.loading), View.VISIBLE);
        mYummlyApi.searchWithFilter(YummlyUtil.getApplicationId(getActivity()),
                                    YummlyUtil.getApplicationKey(getActivity()),
                                    mSearchPhrase.matches(DEFAULT_NEW_RECIPES_SEARCH) ? DEFAULT_NEW_RECIPES_SEARCH : mSearchPhrase,
                                    withIngredients,
                                    withoutIngredients,
                                    withCourses,
                                    withTime,
                                    new Callback<SearchByPhraseVo>() {
                                        @Override
                                        public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                                            initializeListViewAdapter(searchByPhraseVo);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                        }
                                    });
    }

    private void initializeEmptyView(String message, int progressVisibility) {
        mEmptyView.setText(message, progressVisibility);
        mEmptyView.setVisibility(View.VISIBLE);
    }
}
