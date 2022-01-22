package com.epam.rd.autotasks.springemployeecatalog.constants;

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
    public static final String ORDER_BY_LASTNAME = " ORDER BY E.LASTNAME";
    public static final String ORDER_BY_HIREDATE = " ORDER BY E.HIREDATE";
    public static final String ORDER_BY_POSITION = " ORDER BY E.POSITION";
    public static final String ORDER_BY_SALARY = " ORDER BY E.SALARY";
    public static final String LASTNAME_CASE = "lastName";
    public static final String HIREDATE_CASE = "hired";
    public static final String POSITION_CASE = "position";
    public static final String DIGIT_REGEX = "\\d+";
}
