package com.epam.rd.autotasks.catalog.constant;

public enum TableColumnNamesEnum {
    ID("ID"),
    FIRSTNAME("FIRSTNAME"),
    LASTNAME("LASTNAME"),
    MIDDLENAME("MIDDLENAME"),
    HIREDATE("HIREDATE"),
    POSITION("POSITION"),
    SALARY("SALARY"),
    MANAGER("MANAGER"),
    DEPARTMENT("DEPARTMENT"),
    NAME("NAME"),
    LOCATION("LOCATION");

    private final String columnName;

    TableColumnNamesEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}