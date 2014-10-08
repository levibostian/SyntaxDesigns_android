package edu.uni.cs.syntaxdesigns.application;

import android.app.Application;
import dagger.ObjectGraph;
import edu.uni.cs.syntaxdesigns.module.FragmentModule;
import edu.uni.cs.syntaxdesigns.module.UtilModule;
import edu.uni.cs.syntaxdesigns.module.YummlyModule;
import edu.uni.cs.syntaxdesigns.util.LogUtil;

import javax.inject.Inject;
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
            new UtilModule(this),
            new FragmentModule(),
            new YummlyModule(this)
        );
    }

    public static void inject(Object object) {
        sObjectGraph.inject(object);
    }
}
