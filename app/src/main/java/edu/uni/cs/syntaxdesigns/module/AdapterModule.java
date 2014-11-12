package edu.uni.cs.syntaxdesigns.module;

import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import edu.uni.cs.syntaxdesigns.adapter.GroceryListAdapter;
import edu.uni.cs.syntaxdesigns.adapter.GroceryListFilterRecipesAdapter;

import javax.inject.Singleton;

@Module(injects = {GroceryListAdapter.class,
                   GroceryListFilterRecipesAdapter.class}, complete = false, library = true)
public class AdapterModule {

    public AdapterModule() {
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}