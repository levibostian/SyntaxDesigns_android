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

    private static final class Info implements BaseColumns {
        private static final TableColumn COLUMN_NAME = new TableColumn("name", ColumnType.TEXT, true);
        private static final TableColumn COLUMN_HAVE_IT = new TableColumn("haveIt", ColumnType.INTEGER, false, "0");
        private static final ForeignKeyColumn RECIPE_ID = new ForeignKeyColumn("recipeId", ColumnType.INTEGER, RecipeTable.getTableName(), RecipeTable.Columns._ID, true, true);
    }

    @Override
    public final String getCreateQuery() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
               Info._ID + " INTEGER PRIMARY KEY," +
               Info.COLUMN_NAME + "," +
               Info.COLUMN_HAVE_IT + "," +
               Info.RECIPE_ID + ")";
    }

}
