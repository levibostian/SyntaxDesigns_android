package edu.uni.cs.syntaxdesigns.module;

import android.content.Context;
import dagger.Module;
import edu.uni.cs.syntaxdesigns.database.SyntaxDesignsSQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module(library = true, complete = false)
public class DatabaseModule {

    private Context mContext;

    public DatabaseModule(Context context) {
        mContext = context;
    }

    @Inject
    @Singleton
    SyntaxDesignsSQLiteOpenHelper provideSyntaxDesignsSQLiteOpenHelper() {
        return new SyntaxDesignsSQLiteOpenHelper(mContext);
    }

}
