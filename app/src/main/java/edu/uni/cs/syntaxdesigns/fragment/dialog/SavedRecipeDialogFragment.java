package edu.uni.cs.syntaxdesigns.fragment.dialog;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import edu.uni.cs.syntaxdesigns.util.YummlyUtil;
import edu.uni.cs.syntaxdesigns.view.SavedRecipeView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import javax.inject.Inject;

public class SavedRecipeDialogFragment extends DialogFragment implements SavedRecipeView.SavedRecipeViewListener {

    private static final String SAVED_RECIPE = "savedRecipeDialog.savedRecipe";
    private static final String WEB_VIEW_DIALOG = "savedRecipeDialog.webView";

    private Dialog mDialog;
    private Resources mResources;
    private RecipeIdVo mRecipe;
    private SavedRecipeView mSavedRecipeView;

    @Inject YummlyApi mYummlyApi;

    public static SavedRecipeDialogFragment newInstance(RecipeIdVo recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_RECIPE, recipe);

        SavedRecipeDialogFragment fragment = new SavedRecipeDialogFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        getBundleExtras(savedInstanceState != null ? savedInstanceState : getArguments());

        mResources = getResources();

        SyntaxDesignsApplication.inject(this);

        mSavedRecipeView = new SavedRecipeView(getActivity(), mRecipe);
        mSavedRecipeView.setListener(this);

        mDialog = new AlertDialog.Builder(getActivity())
                          .setView(mSavedRecipeView)
                          .setNegativeButton(mResources.getString(R.string.cancel), new DialogInterface.OnClickListener() {
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
            mRecipe = savedInstanceState.getParcelable(SAVED_RECIPE);
        }
    }

    @Override
    public void startRecipeDetails() {
        mYummlyApi.searchByRecipeId(mRecipe.id,
                                    YummlyUtil.getApplicationId(getActivity()),
                                    YummlyUtil.getApplicationKey(getActivity()),
                                    new Callback<RecipeIdVo>() {
                                        @Override
                                        public void success(RecipeIdVo recipeIdVo, Response response) {
                                            WebViewDialogFragment.newInstance(recipeIdVo.source.sourceRecipeUrl).show(getActivity().getFragmentManager(),
                                                                                                                      WEB_VIEW_DIALOG);
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Toast.makeText(getActivity(), mResources.getString(R.string.yummly_error), Toast.LENGTH_SHORT).show();
                                        }
                                    });
    }
}
