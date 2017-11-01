package org.bahmni.batch.form.domain;

/**
 * Created by rajashrk on 10/31/17.
 */
public class TableColumns {
    private String columnName;
    private String dataType;

    public TableColumns(String columnName, String dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }

    public TableColumns() {
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return columnName + " " + dataType;
    }
}
