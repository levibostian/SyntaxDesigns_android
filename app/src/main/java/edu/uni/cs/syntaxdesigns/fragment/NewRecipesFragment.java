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
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.fragment.filter.NewRecipesFilterFragment;
import edu.uni.cs.syntaxdesigns.util.ImageUtil;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

public class NewRecipesFragment extends FilteringFragment {

    private NewRecipesFilterFragment mFilterFragment;
    private ListView mListView;
    private SearchRecipesAdapter mAdapter;

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

        mFilterFragment = NewRecipesFilterFragment.newInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_recipes, container, false);

        mListView = (ListView) rootView.findViewById(R.id.test_list_view);

        initializeListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO - start a dialog
            }
        });

        return rootView;
    }

    private void initializeListView() {
        String mainDishQuery = "course^course-Main Dishes";
        mYummlyApi.searchCourse(YummlyUtil.getApplicationId(getActivity()), YummlyUtil.getApplicationKey(getActivity()), mainDishQuery,new Callback<SearchByPhraseVo>() {
            @Override
            public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                initializeListViewAdapter(searchByPhraseVo);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), R.string.yummly_error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }

    private void initializeListViewAdapter(SearchByPhraseVo searchByPhraseResults) {
        SearchRecipesAdapter mAdapter = new SearchRecipesAdapter(getActivity(), searchByPhraseResults.getPhraseResults());
        mListView.setAdapter(mAdapter);
    }

    @Override
    public Fragment getFilterFragment() {
        return mFilterFragment;
    }
}
