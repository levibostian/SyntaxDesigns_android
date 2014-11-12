package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.GroceryListFragment;
import edu.uni.cs.syntaxdesigns.fragment.NewRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.SavedRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.dialog.RecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.dialog.SavedRecipeDialogFragment;

@Module(injects = {NewRecipesFragment.class,
                   GroceryListFragment.class,
                   RecipeDialogFragment.class,
                   SavedRecipesFragment.class,
                   SavedRecipeDialogFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
