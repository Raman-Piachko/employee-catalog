package com.epam.rd.autotasks.springemployeecatalog.constants;

public class AppConstants {
    public AppConstants() {
    }

    public static final String EMPLOYEE_BY_ID_SELECT = "with recursive  T " +
            " (ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT) " +
            " as \n" +
            " (select\n" +
            "   ID AS ID_e,\n" +
            "   FIRSTNAME,\n" +
            "   LASTNAME,\n" +
            "   MIDDLENAME,\n" +
            "   POSITION,\n" +
            "   HIREDATE,\n" +
            "   SALARY,\n" +
            "   MANAGER,\n" +
            "   DEPARTMENT\n" +
            " from\n" +
            "   EMPLOYEE where ID= %s\n" +
            " union all\n" +
            " select\n" +
            "   M.ID,\n" +
            "   M.FIRSTNAME,\n" +
            "   M.LASTNAME,\n" +
            "   M.MIDDLENAME,\n" +
            "   M.POSITION,\n" +
            "   M.HIREDATE,\n" +
            "   M.SALARY,\n" +
            "   M.MANAGER,\n" +
            "   M.DEPARTMENT\n" +
            " from\n" +
            "   T\n" +
            " inner join EMPLOYEE as M on T.MANAGER = M.ID) " +
            " select " +
            "   T.ID, " +
            "   T.FIRSTNAME, " +
            "   T.LASTNAME, " +
            "   T.MIDDLENAME, " +
            "   T.POSITION, " +
            "   T.MANAGER, " +
            "   T.HIREDATE, " +
            "   T.SALARY, " +
            "   T.DEPARTMENT, " +
            "   D.NAME, " +
            "   D.LOCATION " +
            " from" +
            "   T" +
            " left join DEPARTMENT D on T.DEPARTMENT = D.ID ;";

    public static final String ALL_EMPLOYEES = "select\n" +
            "    e.ID as ID_e,\n" +
            "    e.FIRSTNAME as FIRSTNAME_e,\n" +
            "    e.LASTNAME as LASTNAME_e,\n" +
            "    e.MIDDLENAME as MIDDLENAME_e,\n" +
            "    e.POSITION as POSITION_e,\n" +
            "    e.MANAGER as MANAGER_e,\n" +
            "    e.HIREDATE as HIREDATE_e,\n" +
            "    e.SALARY as SALARY_e,\n" +
            "    d1.ID as ID_d_e,\n" +
            "    d1.NAME as NAME_d_e,\n" +
            "    d1.LOCATION as LOCATION_d_e,\n" +
            "    m.ID as ID_m,\n" +
            "    m.FIRSTNAME as FIRSTNAME_m,\n" +
            "    m.LASTNAME as LASTNAME_m,\n" +
            "    m.MIDDLENAME as MIDDLENAME_m,\n" +
            "    m.POSITION as POSITION_m,\n" +
            "    m.MANAGER as MANAGER_m,\n" +
            "    m.HIREDATE as HIREDATE_m,\n" +
            "    m.SALARY as SALARY_m,\n" +
            "    m.DEPARTMENT as DEPARTMENT_m,\n" +
            "    d2.ID as ID_d_m,\n" +
            "    d2.NAME as NAME_d_m,\n" +
            "    d2.LOCATION as LOCATION_d_m\n" +
            "            from\n" +
            "    employee e\n" +
            "    left join employee m on\n" +
            "    e.manager = m.id\n" +
            "    left join DEPARTMENT d1 on\n" +
            "    e.DEPARTMENT = d1.id\n" +
            "    left join DEPARTMENT d2 on\n" +
            "    m.DEPARTMENT = d2.id";

    public static final String BY_ID = " where e.ID = %s";
    public static final String BY_MANAGER = " where e.MANAGER = %s";
    public static final String BY_DEPARTMENT_ID = " where d1.ID = %s";
    public static final String BY_DEPARTMENT_NAME = " where d1.NAME = '%s'";

    public static final String LASTNAME_CASE = "lastName";
    public static final String HIREDATE_CASE = "hired";
    public static final String POSITION_CASE = "position";
    public static final String DIGIT_REGEX = "\\d+";

    public static final String LIMIT_OFFSET = " LIMIT %d OFFSET %d";
}
