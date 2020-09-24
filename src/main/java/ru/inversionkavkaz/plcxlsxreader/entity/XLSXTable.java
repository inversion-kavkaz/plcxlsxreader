package ru.inversionkavkaz.plcxlsxreader.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "xlsxTable")
public class XLSXTable {
    String name;
    private Integer importStartRowIndex;
    private Map<Integer, TableColumn> tableColumns;

    public Map<Integer, TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(Map<Integer, TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getName() {
        return name;
    }

    public Integer getImportStartRowIndex() {
        return importStartRowIndex;
    }

    public String getInsertSQL(){
        StringBuilder sqlStr = new StringBuilder("insert into " + name + " (id, cusrlogname,");
        for(TableColumn tc : tableColumns.values()){
            sqlStr.append(tc.name).append(",");
        }
        sqlStr = new StringBuilder(sqlStr.substring(0, sqlStr.length() - 1) + ") values(?,?,");
        for(TableColumn tc : tableColumns.values()){
            if((tc.getColumnType().equals(ColumnType.Date)||tc.getColumnType().equals(ColumnType.TimeStamp))&&tc.getFormat()!=null&&!tc.getFormat().isEmpty()){
                sqlStr.append("to_").append(tc.getColumnType()).append("(?,'").append(tc.getFormat()).append("'),");
            }else
            sqlStr.append("?,");
        }
        return sqlStr.substring(0, sqlStr.length()-1)+")";
    }


    public XLSXTable() {
    }

    public XLSXTable(String name, Integer importStartRowIndex, Map<Integer, TableColumn> tableColumns) {
        this.name = name;
        this.importStartRowIndex = importStartRowIndex;
        this.tableColumns = tableColumns;
    }
}


