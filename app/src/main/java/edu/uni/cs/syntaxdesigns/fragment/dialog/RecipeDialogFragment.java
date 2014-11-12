package edu.uni.cs.syntaxdesigns.fragment.dialog;


import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import edu.uni.cs.syntaxdesigns.VOs.SearchByPhraseVo;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import edu.uni.cs.syntaxdesigns.view.HtmlView;
import edu.uni.cs.syntaxdesigns.view.NewRecipeView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.Toast;

import javax.inject.Inject;

import static edu.uni.cs.syntaxdesigns.view.NewRecipeView.DetailsListener;

public class RecipeDialogFragment extends DialogFragment implements DetailsListener {

    private static final String RECIPE_RESULTS = "recipeDialog.recipeResults";

    private PhraseResults mResults;
    private NewRecipeView mNewRecipeView;
    private Dialog mDialog;
    private Resources mResources;
    private HtmlView mHtmlView;

    @Inject YummlyApi mYummlyApi;

    public static RecipeDialogFragment newInstance(PhraseResults results) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RECIPE_RESULTS, results);

        RecipeDialogFragment fragment = new RecipeDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getBundleExtras(savedInstanceState != null ? savedInstanceState : getArguments());

        mResources = getResources();

        SyntaxDesignsApplication.inject(this);

        mNewRecipeView = new NewRecipeView(getActivity(), mResults);
        mHtmlView = new HtmlView(getActivity());

        mNewRecipeView.setCallback(this);

        mDialog = new AlertDialog.Builder(getActivity())
                .setView(mNewRecipeView)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();

        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return mDialog;
    }

    private void getBundleExtras(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mResults = savedInstanceState.getParcelable(RECIPE_RESULTS);
        }
    }

    @Override
    public void startRecipeDetials(final String recipeId) {
        mYummlyApi.searchByRecipeId(recipeId,
                                    YummlyUtil.getApplicationId(getActivity()),
                                    YummlyUtil.getApplicationKey(getActivity()),
                                    new Callback<RecipeIdVo>() {
                                        @Override
                                        public void success(RecipeIdVo recipeIdVo, Response response) {
                                            mHtmlView.setHtml(recipeIdVo.source.sourceRecipeUrl);
                                            mDialog.setContentView(mHtmlView);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getActivity(),mResources.getString(R.string.yummly_error), Toast.LENGTH_SHORT).show();
                                        }
                                    });
    }
}
