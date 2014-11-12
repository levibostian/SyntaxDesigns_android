package edu.uni.cs.syntaxdesigns.application;

import android.app.Application;
import dagger.ObjectGraph;
import edu.uni.cs.syntaxdesigns.module.AdapterModule;
import edu.uni.cs.syntaxdesigns.module.DatabaseModule;
import edu.uni.cs.syntaxdesigns.module.FragmentModule;
import edu.uni.cs.syntaxdesigns.module.UtilModule;
import edu.uni.cs.syntaxdesigns.module.ViewModule;
import edu.uni.cs.syntaxdesigns.module.YummlyModule;

import java.util.Arrays;
import java.util.List;

public class SyntaxDesignsApplication extends Application {

    private static ObjectGraph sObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        sObjectGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
            new AdapterModule(),
            new UtilModule(this),
            new FragmentModule(),
            new YummlyModule(this),
            new DatabaseModule(this),
            new ViewModule()
        );
    }

    public static void inject(Object object) {
        sObjectGraph.inject(object);
    }
}
