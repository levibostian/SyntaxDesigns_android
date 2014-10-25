package edu.uni.cs.syntaxdesigns.table;

import android.provider.BaseColumns;
import edu.uni.cs.syntaxdesigns.table.column.ColumnType;
import edu.uni.cs.syntaxdesigns.table.column.ForeignKeyColumn;
import edu.uni.cs.syntaxdesigns.table.column.TableColumn;

import javax.inject.Inject;

public final class IngredientsTable extends DatabaseTable {

    public static final String TABLE_NAME = "ingredients";

    @Inject
    public IngredientsTable() {
        super(TABLE_NAME);
    }

    public static final class Columns implements BaseColumns {
        public static final TableColumn COLUMN_NAME = new TableColumn("name", ColumnType.TEXT, true);
        public static final TableColumn COLUMN_HAVE_IT = new TableColumn("haveIt", ColumnType.INTEGER, false, "0");
        public static final ForeignKeyColumn RECIPE_ID = new ForeignKeyColumn("recipeId", ColumnType.INTEGER, RecipeTable.getTableName(), RecipeTable.Columns._ID, true, true);
    }

    public static final String getCreateQuery() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
               Columns._ID + " INTEGER PRIMARY KEY," +
               Columns.COLUMN_NAME + "," +
               Columns.COLUMN_HAVE_IT + "," +
               Columns.RECIPE_ID + ")";
    }

}
