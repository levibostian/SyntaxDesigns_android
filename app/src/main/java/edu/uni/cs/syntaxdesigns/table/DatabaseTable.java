package edu.uni.cs.syntaxdesigns.table;

public abstract class DatabaseTable {

    private static String mTableName;

    public DatabaseTable(String tableName) {
        mTableName = tableName;
    }

    public static final String getTableName() {
        return mTableName;
    }

    public static final String getDeleteQuery() {
        return "DELETE TABLE IF EXISTS " + mTableName;
    }

}
