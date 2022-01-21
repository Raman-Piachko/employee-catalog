package com.epam.rd.autotasks.springemployeecatalog;

public final class AppConstants {
    private AppConstants() {
    }

    public static final String EMPLOYEE_ID = "EMPLOYEE.ID";
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String LASTNAME = "LASTNAME";
    public static final String MIDDLENAME = "MIDDLENAME";
    public static final String POSITION = "POSITION";
    public static final String HIREDATE = "HIREDATE";
    public static final String SALARY = "SALARY";
    public static final String DEPARTMENT_ID = "DEPARTMENT.ID";
    public static final String NAME = "NAME";
    public static final String LOCATION = "LOCATION";
    public static final String MANAGER = "MANAGER";
    public static final String DEFAULT_SELECT = "SELECT * FROM EMPLOYEE E LEFT JOIN DEPARTMENT D ON E.DEPARTMENT = D.ID";
    public static final String ORDER_BY_LASTNAME = "ORDER BY E.LASTNAME";
}
