package com.epam.rd.autotasks.springemployeecatalog.constants;

public enum DepartmentEnum {
    managerDepartmentId("ID_d_m"),
    employeeDepartmentId("ID_d_e"),
    managerDepartmentName("NAME_d_m"),
    employeeDepartmentName("NAME_d_e"),
    managerDepartmentLocation("LOCATION_d_m"),
    employeeDepartmentLocation("LOCATION_d_e");

    private final String column;

    DepartmentEnum(String columnName) {
        column = columnName;
    }

    public String toString() {
        return this.column;
    }
}