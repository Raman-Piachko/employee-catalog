package com.epam.rd.autotasks.springemployeecatalog.constants;

public enum ColumnEnum {
    employeeId("ID"),
    firstname("FIRSTNAME"),
    lastname("LASTNAME"),
    middlename("MIDDLENAME"),
    hiredate("HIREDATE"),
    position("POSITION"),
    salary("SALARY"),
    manager("MANAGER"),
    department("department"),
    name("NAME"),
    location("LOCATION");

    private final String column;

    private ColumnEnum(String columnName) {
        column = columnName;
    }

    public boolean equalsName(String otherName) {
        return column.equals(otherName);
    }

    public String toString() {
        return this.column;
    }
}
