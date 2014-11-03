package edu.uni.cs.syntaxdesigns.fragment.dialog;


import edu.uni.cs.syntaxdesigns.R;
import edu.uni.cs.syntaxdesigns.VOs.PhraseResults;
import edu.uni.cs.syntaxdesigns.view.NewRecipeView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;

public class RecipeDialogFragment extends DialogFragment {

    private static final String RECIPE_RESULTS = "recipeDialog.recipeResults";

    private PhraseResults mResults;

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

        NewRecipeView recipeView = new NewRecipeView(getActivity(), mResults);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(recipeView)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                }).create();

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    private void getBundleExtras(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mResults = savedInstanceState.getParcelable(RECIPE_RESULTS);
        }
    }
}
