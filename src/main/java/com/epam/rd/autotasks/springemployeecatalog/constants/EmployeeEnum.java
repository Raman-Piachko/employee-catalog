package com.epam.rd.autotasks.springemployeecatalog.constants;

public enum EmployeeEnum {
    employeeId("ID"),
    id("ID_e"),
    employeeFirstname("FIRSTNAME"),
    firstname("FIRSTNAME_e"),
    managerFirstname("FIRSTNAME_m"),
    employeeLastname("LASTNAME"),
    lastname("LASTNAME_e"),
    managerLastname("LASTNAME_m"),
    employeeMiddlename("MIDDLENAME"),
    middlename("MIDDLENAME_e"),
    managerMiddlename("MIDDLENAME_m"),
    employeeHiredate("HIREDATE"),
    hiredate("HIREDATE_e"),
    managerHiredate("HIREDATE_m"),
    employeePosition("POSITION"),
    position("POSITION_e"),
    managerPosition("POSITION_m"),
    employeeSalary("SALARY"),
    salary("SALARY_e"),
    managerSalary("SALARY_m"),
    employeeManager("MANAGER"),
    manager("MANAGER_e"),
    department("department"),
    name("NAME"),
    location("LOCATION");

    private final String column;

    EmployeeEnum(String columnName) {
        column = columnName;
    }

    public String toString() {
        return this.column;
    }
}