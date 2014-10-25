package edu.uni.cs.syntaxdesigns.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import edu.uni.cs.syntaxdesigns.database.SyntaxDesignsSQLiteOpenHelper;
import edu.uni.cs.syntaxdesigns.database.dao.IngredientsDao;
import edu.uni.cs.syntaxdesigns.database.dao.RecipeDao;

import javax.inject.Singleton;

@Module(injects = {RecipeDao.class}, library = true, complete = false)
public class DatabaseModule {

    private Context mContext;

    public DatabaseModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    SyntaxDesignsSQLiteOpenHelper provideSyntaxDesignsSQLiteOpenHelper() {
        return new SyntaxDesignsSQLiteOpenHelper(mContext);
    }

    @Provides
    @Singleton
    RecipeDao provideRecipeDao(SyntaxDesignsSQLiteOpenHelper openHelper) {
        return new RecipeDao(openHelper);
    }

    @Provides
    @Singleton
    IngredientsDao provideIngredientsDao(SyntaxDesignsSQLiteOpenHelper openHelper) {
        return new IngredientsDao(openHelper);
    }

}
