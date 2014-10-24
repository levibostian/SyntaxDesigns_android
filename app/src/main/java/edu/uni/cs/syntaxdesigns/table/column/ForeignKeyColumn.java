package edu.uni.cs.syntaxdesigns.table.column;

public class ForeignKeyColumn extends TableColumn {

    public String referenceTableName;
    public String referenceColumnName;
    public boolean deleteCascade;
    public boolean updateCascade;

    public ForeignKeyColumn(String name, ColumnType type, String referenceTableName, String referenceColumnName) {
        this(name, type, referenceTableName, referenceColumnName, false, false);
    }

    public ForeignKeyColumn(String name, ColumnType type, String referenceTableName, String referenceColumnName, boolean deleteCascade, boolean updateCascade) {
        super(name, type);

        this.referenceTableName = referenceTableName;
        this.referenceColumnName = referenceColumnName;
        this.deleteCascade = deleteCascade;
        this.updateCascade = updateCascade;
    }

    @Override
    public String toString() {
        return name + " "
               + columnType.getType() + " "
               + (notNull ? "NOT NULL" : "") + " "
               + (defaultValue != null ? ("DEFAULT " + defaultValue) : "") + " "
               + "REFERENCES " + referenceTableName + "(" + referenceColumnName + ")" + " "
               + (deleteCascade ? "ON DELETE CASCADE" : "") + " "
               + (updateCascade ? "ON UPDATE CASCADE" : "");
    }

}
