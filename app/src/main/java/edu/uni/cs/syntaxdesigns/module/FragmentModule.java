package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.fragment.MainFragment;

@Module(injects = {MainFragment.class}, complete = false)
public class FragmentModule {

    public FragmentModule() {
    }
}
