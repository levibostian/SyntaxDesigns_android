package edu.uni.cs.syntaxdesigns.table;

import android.provider.BaseColumns;
import edu.uni.cs.syntaxdesigns.table.column.ColumnType;
import edu.uni.cs.syntaxdesigns.table.column.TableColumn;

import javax.inject.Inject;

public final class RecipeTable extends DatabaseTable {

    private static final String TABLE_NAME = "recipes";

    @Inject
    public RecipeTable() {
        super(TABLE_NAME);
    }

    public static final class Columns implements BaseColumns {
        private static final TableColumn COLUMN_NAME = new TableColumn("name", ColumnType.TEXT, true);
        private static final TableColumn COLUMN_YUMMLY_URL = new TableColumn("yummlyUrl", ColumnType.TEXT);
        private static final TableColumn COLUMN_FAVORITE = new TableColumn("favorite", ColumnType.INTEGER, false, "0");
    }

    public static final String getCreateQuery() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
               Columns._ID + " INTEGER PRIMARY KEY," +
               Columns.COLUMN_NAME + "," +
               Columns.COLUMN_YUMMLY_URL + "," +
               Columns.COLUMN_FAVORITE + ")";
    }

}
