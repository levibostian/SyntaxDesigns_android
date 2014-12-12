package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.BrowseRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.GroceryListFragment;
import edu.uni.cs.syntaxdesigns.fragment.SavedRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.dialog.RecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.dialog.SavedRecipeDialogFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.BrowseRecipesFilterFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.GroceryListFilterFragment;

@Module(injects = {BrowseRecipesFragment.class,
                   GroceryListFragment.class,
                   SavedRecipesFragment.class,
                   GroceryListFilterFragment.class,
                   BrowseRecipesFilterFragment.class,
                   RecipeDialogFragment.class,
                   SavedRecipeDialogFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
