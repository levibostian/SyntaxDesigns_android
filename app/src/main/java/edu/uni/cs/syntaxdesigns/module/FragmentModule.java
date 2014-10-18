package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.NewRecipesFragment;

@Module(injects = {NewRecipesFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
