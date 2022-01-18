package com.epam.rd.autotasks.springemployeecatalog;

public class AppConstants {
    public static final String ALL_FROM_EMPLOYEE = "select\n" +
            " e.ID as ID_e,\n" +
            " e.FIRSTNAME as FIRSTNAME_e,\n" +
            " e.LASTNAME as LASTNAME_e,\n" +
            " e.MIDDLENAME as MIDDLENAME_e,\n" +
            " e.POSITION as POSITION_e,\n" +
            " e.MANAGER as MANAGER_e,\n" +
            " e.HIREDATE as HIREDATE_e,\n" +
            " e.SALARY as SALARY_e,\n" +
            " d1.ID as ID_d_e,\n" +
            " d1.NAME as NAME_d_e,\n" +
            " d1.LOCATION as LOCATION_d_e,\n" +
            " m.ID as ID_m,\n" +
            " m.FIRSTNAME as FIRSTNAME_m,\n" +
            " m.LASTNAME as LASTNAME_m,\n" +
            " m.MIDDLENAME as MIDDLENAME_m,\n" +
            " m.POSITION as POSITION_m,\n" +
            " m.MANAGER as MANAGER_m,\n" +
            " m.HIREDATE as HIREDATE_m,\n" +
            " m.SALARY as SALARY_m,\n" +
            " m.DEPARTMENT as DEPARTMENT_m,\n" +
            " d2.ID as ID_d_m,\n" +
            " d2.NAME as NAME_d_m,\n" +
            " d2.LOCATION as LOCATION_d_m\n" +
            "from\n" +
            " employee e\n" +
            "left join employee m on\n" +
            " e.manager = m.id\n" +
            "left join DEPARTMENT d1 on\n" +
            " e.DEPARTMENT = d1.id\n" +
            "left join DEPARTMENT d2 on\n" +
            " m.DEPARTMENT = d2.id\n"
            ;


    public static final String EMPLOYEE_BY_ID = "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department \" +\n" +
            "                    \"FROM employee WHERE ID = %d";
    public static final String EMPLOYEE_BY_DEP = "SELECT * FROM employee WHERE department = ?";
    public static final String EMPLOYEE_BY_MANAGER = "SELECT * FROM employee WHERE manager = ?";
    public static final String PAGEABLE_FORMAT_QUERY =
            "SELECT id, firstName, lastName, middleName, position, manager, hireDate AS hired, salary, department " +
                    "FROM employee ORDER BY %s %s LIMIT %d OFFSET %d";
    public static final String ALL_FROM_DEPARTMENT = "SELECT * FROM department";
    public static final String COUNT_EMPLOYEE = "select count(*) from employee";
    public static final String ID = "id";
    public static final String MANAGER = "manager";
    public static final String POSITION = "position";
    public static final String HIRE_DATE = "hireDate";
    public static final String SALARY = "salary";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String DEPARTMENT = "department";
    public static final String NAME = "name";
    public static final String LOCATION = "location";
}
