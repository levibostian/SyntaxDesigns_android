package edu.uni.cs.syntaxdesigns.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import edu.uni.cs.syntaxdesigns.database.SyntaxDesignsSQLiteOpenHelper;
import edu.uni.cs.syntaxdesigns.database.cursor.IngredientsCursor;
import edu.uni.cs.syntaxdesigns.table.IngredientsTable;

import javax.inject.Inject;

public class IngredientsDao {

    private static final int INSERT_ERROR_CODE = -1;

    private SQLiteDatabase mWritableDatabase;
    private SQLiteDatabase mReadableDatabase;

    @Inject
    public IngredientsDao(SyntaxDesignsSQLiteOpenHelper database) {
        mWritableDatabase = database.getWritableDatabase();
        mReadableDatabase = database.getReadableDatabase();
    }

    public long insertIngredient(String name, boolean haveIt, long recipeId) {
        ContentValues values = new ContentValues();
        values.put(IngredientsTable.Columns.COLUMN_NAME.name, name);
        values.put(IngredientsTable.Columns.COLUMN_HAVE_IT.name, haveIt ? 1 : 0);
        values.put(IngredientsTable.Columns.RECIPE_ID.name, recipeId);

        long rowId = mWritableDatabase.insert(IngredientsTable.TABLE_NAME, "null", values);

        if (rowId == INSERT_ERROR_CODE) {
            throw new IllegalStateException("Error inserting ingredient. Maybe a foreign key constraint?");
        }

        return rowId;
    }

    public IngredientsCursor readIngredients() {
        return new IngredientsCursor(mReadableDatabase.query(IngredientsTable.TABLE_NAME,
                                                             new String[]{"*"},
                                                             null,
                                                             null,
                                                             null,
                                                             null,
                                                             null,
                                                             null));
    }

    public IngredientsCursor readIngredientsForRecipe(long recipeId) {
        String[] columns = {IngredientsTable.Columns._ID,
        IngredientsTable.Columns.COLUMN_NAME.name,
        IngredientsTable.Columns.COLUMN_HAVE_IT.name,
        IngredientsTable.Columns.RECIPE_ID.name};

        return new IngredientsCursor(mReadableDatabase.query(IngredientsTable.TABLE_NAME,
                                                           columns,
                                                           IngredientsTable.Columns.RECIPE_ID.name + " = ?",
                                                           new String[] {String.valueOf(recipeId)},
                                                           null,
                                                           null,
                                                           "_id ASC"));
    }

    public void updateHaveIt(long rowId, boolean haveIt) {
        ContentValues values = new ContentValues();
        values.put(IngredientsTable.Columns.COLUMN_HAVE_IT.name, haveIt ? 1 : 0);

        mWritableDatabase.update(IngredientsTable.TABLE_NAME,
                                 values,
                                 "_id = ?",
                                 new String[]{String.valueOf(rowId)});
    }

    public void deleteIngredientsForRecipe(long recipeRowId) {
        mWritableDatabase.delete(IngredientsTable.TABLE_NAME,
                                 "recipeId = ?",
                                 new String[]{String.valueOf(recipeRowId)});
    }

}
