package edu.uni.cs.syntaxdesigns.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import edu.uni.cs.syntaxdesigns.Adapters.SearchByPhraseAdapter;
import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.SearchByPhraseVo;
import edu.uni.cs.syntaxdesigns.activity.MainActivity;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.util.ImageUtil;
import edu.uni.cs.syntaxdesigns.util.LogUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

public class MainFragment extends Fragment {

    private ListView mListView;
    private SearchByPhraseAdapter mAdapter;

    @Inject ImageUtil mImageUtil;
    @Inject YummlyApi mYummlyApi;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyntaxDesignsApplication.inject(this);

        LogUtil.d(mImageUtil.testDaggerWorks());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mListView = (ListView) rootView.findViewById(R.id.test_list_view);

        initializeListView();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void initializeListView() {
        Resources resources = getActivity().getResources();
        String testQuery = "onion soup";
        mYummlyApi.searchByPhrase(resources.getString(R.string.app_id), resources.getString(R.string.app_key), testQuery,new Callback<SearchByPhraseVo>() {
            @Override
            public void success(SearchByPhraseVo searchByPhraseVo, Response response) {
                initializeListViewAdapter(searchByPhraseVo);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"Error searching for recipes, try the search again", Toast.LENGTH_SHORT);
                error.printStackTrace();
            }
        });
    }

    private void initializeListViewAdapter(SearchByPhraseVo searchByPhraseResults) {
        SearchByPhraseAdapter mAdapter = new SearchByPhraseAdapter(getActivity(), searchByPhraseResults.getPhraseResults());
        mListView.setAdapter(mAdapter);
    }
}
