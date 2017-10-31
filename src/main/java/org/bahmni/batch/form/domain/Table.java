package org.bahmni.batch.form.domain;

/**
 * Created by rajashrk on 10/31/17.
 */
public class Table {
    private String columnName;
    private String dataType;

    public Table(String columnName, String dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }

    public Table() {
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
