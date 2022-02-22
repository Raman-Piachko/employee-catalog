package com.epam.rd.autotasks.catalog.constant;

public final class RepositoryConstants {
    private RepositoryConstants() {
    }

    public static final String SELECT_EMPLOYEE_BY_ID = "with recursive  T " +
            " (ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT) " +
            " as " +
            " (select" +
            "   ID AS ID_e," +
            "   FIRSTNAME," +
            "   LASTNAME," +
            "   MIDDLENAME," +
            "   POSITION," +
            "   HIREDATE," +
            "   SALARY," +
            "   MANAGER," +
            "   DEPARTMENT" +
            " from" +
            "   EMPLOYEE where ID= ?" +
            " union all" +
            " select" +
            "   M.ID," +
            "   M.FIRSTNAME," +
            "   M.LASTNAME," +
            "   M.MIDDLENAME," +
            "   M.POSITION," +
            "   M.HIREDATE," +
            "   M.SALARY," +
            "   M.MANAGER," +
            "   M.DEPARTMENT" +
            " from" +
            "   T" +
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

    public static final String SELECT_ALL_EMPLOYEES = "select" +
            "    e.ID as ID_e," +
            "    e.FIRSTNAME as FIRSTNAME_e," +
            "    e.LASTNAME as LASTNAME_e," +
            "    e.MIDDLENAME as MIDDLENAME_e," +
            "    e.POSITION as POSITION_e," +
            "    e.MANAGER as MANAGER_e," +
            "    e.HIREDATE as HIREDATE_e," +
            "    e.SALARY as SALARY_e," +
            "    d1.ID as ID_d_e," +
            "    d1.NAME as NAME_d_e," +
            "    d1.LOCATION as LOCATION_d_e," +
            "    m.ID as ID_m," +
            "    m.FIRSTNAME as FIRSTNAME_m," +
            "    m.LASTNAME as LASTNAME_m," +
            "    m.MIDDLENAME as MIDDLENAME_m," +
            "    m.POSITION as POSITION_m," +
            "    m.MANAGER as MANAGER_m," +
            "    m.HIREDATE as HIREDATE_m," +
            "    m.SALARY as SALARY_m," +
            "    m.DEPARTMENT as DEPARTMENT_m," +
            "    d2.ID as ID_d_m," +
            "    d2.NAME as NAME_d_m," +
            "    d2.LOCATION as LOCATION_d_m" +
            " from" +
            "    employee e" +
            " left join employee m on e.manager = m.id" +
            " left join DEPARTMENT d1 on e.DEPARTMENT = d1.id" +
            " left join DEPARTMENT d2 on m.DEPARTMENT = d2.id";

    public static final String WHERE_BY_ID = " where e.ID = ?";
    public static final String WHERE_BY_MANAGER = " where e.MANAGER = ?";
    public static final String WHERE_BY_DEPARTMENT_ID = " where d1.ID = ?";
    public static final String WHERE_BY_DEPARTMENT_NAME = " where d1.NAME = ?";

    public static final String DIGIT_REGEX = "\\d+";

    public static final String LIMIT_OFFSET = " LIMIT ? OFFSET ?";
}