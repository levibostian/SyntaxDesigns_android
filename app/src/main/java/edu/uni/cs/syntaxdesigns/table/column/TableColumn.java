package edu.uni.cs.syntaxdesigns.table.column;

public class TableColumn {

    public String name;
    public ColumnType columnType;
    public boolean notNull;
    public String defaultValue;

    public TableColumn(String name, ColumnType type) {
        this(name, type, false);
    }

    public TableColumn(String name, ColumnType type, boolean notNull) {
        this(name, type, notNull, null);
    }

    public TableColumn(String name, ColumnType type, boolean notNull, String defaultValue) {
        this.name = name;
        this.columnType = type;
        this.notNull = notNull;
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return name + " "
               + columnType.getType() + " "
               + (notNull ? "NOT NULL" : "")
               + (defaultValue != null ? ("DEFAULT " + defaultValue) : "");
    }

}
