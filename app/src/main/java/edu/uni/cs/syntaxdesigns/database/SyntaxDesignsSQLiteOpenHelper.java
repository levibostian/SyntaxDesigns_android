package edu.uni.cs.syntaxdesigns.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.uni.cs.syntaxdesigns.table.IngredientsTable;
import edu.uni.cs.syntaxdesigns.table.RecipeTable;

public class SyntaxDesignsSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SyntaxDesigns.db";

    public SyntaxDesignsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, SyntaxDesignsDatabaseVersion.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        sqLiteDatabase.execSQL(RecipeTable.getCreateQuery());
        sqLiteDatabase.execSQL(IngredientsTable.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(RecipeTable.getDeleteQuery());
        sqLiteDatabase.execSQL(IngredientsTable.getDeleteQuery());
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
