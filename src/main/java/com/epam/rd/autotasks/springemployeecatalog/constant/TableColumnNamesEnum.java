package com.epam.rd.autotasks.springemployeecatalog.constant;

public enum TableColumnNamesEnum {
    ID("ID"),
    ID_E("ID_e"),
    EMPLOYEE_FIRSTNAME("FIRSTNAME"),
    FIRSTNAME("FIRSTNAME_e"),
    MANAGER_FIRSTNAME("FIRSTNAME_m"),
    EMPLOYEE_LASTNAME("LASTNAME"),
    LASTNAME("LASTNAME_e"),
    MANAGER_LASTNAME("LASTNAME_m"),
    EMPLOYEE_MIDDLENAME("MIDDLENAME"),
    MIDDLENAME("MIDDLENAME_e"),
    MANAGER_MIDDLENAME("MIDDLENAME_m"),
    EMPLOYEE_HIREDATE("HIREDATE"),
    HIREDATE("HIREDATE_e"),
    MANAGER_HIREDATE("HIREDATE_m"),
    EMPLOYEE_POSITION("POSITION"),
    POSITION("POSITION_e"),
    MANAGER_POSITION("POSITION_m"),
    EMPLOYEE_SALARY("SALARY"),
    SALARY("SALARY_e"),
    MANAGER_SALARY("SALARY_m"),
    EMPLOYEE_MANAGER("MANAGER"),
    MANAGER("MANAGER_e"),
    DEPARTMENT("department"),
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