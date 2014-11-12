package edu.uni.cs.syntaxdesigns.fragment.dialog;

import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.RecipeIdVo;
import edu.uni.cs.syntaxdesigns.fragment.SavedRecipesFragment;
import edu.uni.cs.syntaxdesigns.view.SavedRecipeView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;

public class SavedRecipeDialogFragment extends DialogFragment {

    private static String SAVED_RECIPE = "savedRecipeDialog.savedRecipe";

    private Dialog mDialog;
    private Resources mResources;
    private RecipeIdVo mRecipe;
    private SavedRecipeView mSavedRecipeView;

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

        mSavedRecipeView = new SavedRecipeView(getActivity(), mRecipe);

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
}
