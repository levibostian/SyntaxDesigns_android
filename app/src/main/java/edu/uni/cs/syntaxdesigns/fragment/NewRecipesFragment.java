package edu.uni.cs.syntaxdesigns.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import edu.uni.cs.syntaxdesigns.Adapters.SearchRecipesAdapter;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.SearchByPhraseVo;
import edu.uni.cs.syntaxdesigns.activity.MainActivity;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.fragment.dialog.RecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.NewRecipesFilterFragment;
import edu.uni.cs.syntaxdesigns.util.ImageUtil;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedString;

import javax.inject.Inject;
import java.util.ArrayList;

public class NewRecipesFragment extends FilteringFragment implements NewRecipesFilterFragment.Callbacks {

    private static final String DEFAULT_NEW_RECIPES_SEARCH = "popular";

    private NewRecipesFilterFragment mFilterFragment;
    private ListView mListView;
    private SearchRecipesAdapter mAdapter;
    private String mSearchPhrase;

    private static final String RECIPE_DIALOG = "newRecipes.recipeDialog";

    @Inject ImageUtil mImageUtil;
    @Inject YummlyApi mYummlyApi;

    public static NewRecipesFragment newInstance() {
        NewRecipesFragment fragment = new NewRecipesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        mFilterFragment = (NewRecipesFilterFragment) ((MainActivity) getActivity()).getFilterFragment();
        mFilterFragment.setCallback(this);
    }

    public void startSearchByPhrase(String searchPhrase) {
        mSearchPhrase = searchPhrase;
        mYummlyApi.searchByPhrase(YummlyUtil.getApplicationId(getActivity()),
                                  YummlyUtil.getApplicationKey(getActivity()),
                                  searchPhrase,
                                  new Callback<SearchByPhraseVo>() {
                                      @Override
                                      public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                                          initializeListViewAdapter(searchByPhraseVo);
                                      }

                                      @Override
                                      public void failure(RetrofitError error) {
                                          Toast.makeText(getActivity(), R.string.yummly_error, Toast.LENGTH_SHORT).show();
                                      }
                                  });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_recipes, container, false);

        mListView = (ListView) rootView.findViewById(R.id.new_recipe_list_view);

        mSearchPhrase = DEFAULT_NEW_RECIPES_SEARCH;

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
        mYummlyApi.searchByPhrase(YummlyUtil.getApplicationId(getActivity()),
                                  YummlyUtil.getApplicationKey(getActivity()),
                                  mSearchPhrase,
                                  new Callback<SearchByPhraseVo>() {
                                      @Override
                                      public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                                          initializeListViewAdapter(searchByPhraseVo);
                                      }

                                      @Override
                                      public void failure(RetrofitError error) {
                                          Toast.makeText(getActivity(), R.string.yummly_error, Toast.LENGTH_SHORT).show();
                                      }
                                  });
    }

    private void initializeListViewAdapter(SearchByPhraseVo searchByPhraseResults) {
        mAdapter = new SearchRecipesAdapter(getActivity(), searchByPhraseResults.getPhraseResults());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }

    @Override
    public void updateNewRecipeSearch(ArrayList<String> withIngredients, ArrayList<String> withoutIngredients, ArrayList<String> withCourses, String withTime) {
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
                                            Toast.makeText(getActivity(), R.string.yummly_error, Toast.LENGTH_SHORT).show();
                                        }
                                    });
    }
}
