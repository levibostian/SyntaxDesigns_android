package edu.uni.cs.syntaxdesigns.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import edu.uni.cs.syntaxdesigns.util.FilterSearchUtil;
import edu.uni.cs.syntaxdesigns.util.ImageUtil;

@Module(library = true)
public class UtilModule {

    private Context mContext;

    // TODO currently, UtilModule gets context from application.
    // if it needs context from activity, then we need another way
    // to get it.
    public UtilModule(Context context) {
        mContext = context;
    }

    @Provides
    ImageUtil provideImageUtil() {
        return new ImageUtil(mContext);
    }

    @Provides
    FilterSearchUtil provideFilterSearchUtil() {
        return new FilterSearchUtil(mContext);
    }
}
