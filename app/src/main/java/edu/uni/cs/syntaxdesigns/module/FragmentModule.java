package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.GroceryListFragment;
import edu.uni.cs.syntaxdesigns.fragment.NewRecipesFragment;
import edu.uni.cs.syntaxdesigns.fragment.filter.NewRecipesFilterFragment;

@Module(injects = {NewRecipesFragment.class,
                   GroceryListFragment.class,
                   NewRecipesFilterFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
