package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import edu.uni.cs.syntaxdesigns.view.NewRecipeView;

@Module(injects = {NewRecipeView.class}, complete = false)
public class ViewModule {

    public ViewModule() {
    }
}
