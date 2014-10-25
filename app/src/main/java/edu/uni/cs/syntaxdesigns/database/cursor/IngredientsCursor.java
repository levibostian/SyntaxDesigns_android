package edu.uni.cs.syntaxdesigns.database.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;
import edu.uni.cs.syntaxdesigns.table.IngredientsTable;

public class IngredientsCursor extends CursorWrapper {

    public IngredientsCursor(Cursor cursor) {
        super(cursor);
    }

    public String readName() {
        return getString(getColumnIndexOrThrow(IngredientsTable.Columns.COLUMN_NAME.name));
    }

    public boolean isHaveIt() {
        return getInt(getColumnIndexOrThrow(IngredientsTable.Columns.COLUMN_HAVE_IT.name)) == 1;
    }

    public long readRecipeId() {
        return getLong(getColumnIndexOrThrow(IngredientsTable.Columns.RECIPE_ID.name));
    }

}
