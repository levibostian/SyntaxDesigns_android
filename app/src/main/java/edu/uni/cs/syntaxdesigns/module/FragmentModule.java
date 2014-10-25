package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.GroceryListFragment;
import edu.uni.cs.syntaxdesigns.fragment.NewRecipesFragment;

@Module(injects = {NewRecipesFragment.class, GroceryListFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
