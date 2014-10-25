package edu.uni.cs.syntaxdesigns.database.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;
import edu.uni.cs.syntaxdesigns.table.RecipeTable;

public class RecipeCursor extends CursorWrapper {

    public RecipeCursor(Cursor cursor) {
        super(cursor);
    }

    public String readName() {
        return getString(getColumnIndexOrThrow(RecipeTable.Columns.COLUMN_NAME.name));
    }

    public String readYummlyUrl() {
        return getString(getColumnIndexOrThrow(RecipeTable.Columns.COLUMN_YUMMLY_URL.name));
    }

    public boolean isFavorite() {
        return getInt(getColumnIndexOrThrow(RecipeTable.Columns.COLUMN_FAVORITE.name)) == 1;
    }

}
