package edu.uni.cs.syntaxdesigns.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.uni.cs.syntaxdesigns.database.SyntaxDesignsSQLiteOpenHelper;
import edu.uni.cs.syntaxdesigns.database.cursor.RecipeCursor;
import edu.uni.cs.syntaxdesigns.table.RecipeTable;

import javax.inject.Inject;

public class RecipeDao {

    private SQLiteDatabase mWritableDatabase;
    private SQLiteDatabase mReadableDatabase;

    @Inject
    public RecipeDao(SyntaxDesignsSQLiteOpenHelper database) {
        mWritableDatabase = database.getWritableDatabase();
        mReadableDatabase = database.getReadableDatabase();
    }

    public long insertRecipe(String name, String yummlyUrl) {
        ContentValues values = new ContentValues();
        values.put(RecipeTable.Columns.COLUMN_NAME.name, name);
        values.put(RecipeTable.Columns.COLUMN_YUMMLY_URL.name, yummlyUrl);

        return mWritableDatabase.insert(RecipeTable.TABLE_NAME, "null", values);
    }

    public RecipeCursor readRecipes() {
        String[] columns = {RecipeTable.Columns._ID,
                            RecipeTable.Columns.COLUMN_NAME.name,
                            RecipeTable.Columns.COLUMN_FAVORITE.name,
                            RecipeTable.Columns.COLUMN_YUMMLY_URL.name};

        return new RecipeCursor(mReadableDatabase.query(RecipeTable.TABLE_NAME,
                                      columns,
                                      null,
                                      null,
                                      null,
                                      null,
                                      "_id ASC"));
    }

    public RecipeCursor readRecipeByRowId(long rowId) {
        String[] columns = {RecipeTable.Columns._ID,
                            RecipeTable.Columns.COLUMN_NAME.name,
                            RecipeTable.Columns.COLUMN_FAVORITE.name};

        return new RecipeCursor(mReadableDatabase.query(RecipeTable.TABLE_NAME,
                                                      columns,
                                                      RecipeTable.Columns._ID + " = ?",
                                                      new String[]{String.valueOf(rowId)},
                                                      null,
                                                      null,
                                                      "_id ASC"));
    }

    public void deleteRecipe(long rowId) {
        mWritableDatabase.delete(RecipeTable.TABLE_NAME,
                                "_id = ?",
                                new String[] {String.valueOf(rowId)});
    }

    public void favoriteRecipe(long rowId, boolean isFavorite) {
        ContentValues values = new ContentValues();
        values.put(RecipeTable.Columns.COLUMN_FAVORITE.name, isFavorite ? 1 : 0);

        mWritableDatabase.update(RecipeTable.TABLE_NAME,
                                values,
                                "_id = ?",
                                new String[] {String.valueOf(rowId)});
    }

}
