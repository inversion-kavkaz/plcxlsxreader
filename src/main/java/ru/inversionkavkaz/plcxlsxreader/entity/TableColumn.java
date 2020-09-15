package ru.inversionkavkaz.plcxlsxreader.entity;

import java.io.Serializable;

;
public class TableColumn implements Serializable {
    ColumnType columnType;
    String name;
    String format;
    Boolean notNull;

    public String getName() {
        return name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public String getFormat() {
        return format;
    }

    public Boolean getNotNull() {
        return notNull;
    }

    public TableColumn(ColumnType columnType, String name, String format, Boolean notNull) {
        this.columnType = columnType;
        this.name = name;
        this.format = format;
        this.notNull = notNull;
    }

    public TableColumn(ColumnType columnType,  String name) {
        this.columnType = columnType;
        this.name = name;
        this.notNull = false;
    }

}
