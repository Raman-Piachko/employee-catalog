package com.epam.rd.autotasks.springemployeecatalog.constants;

public class AppConstants {
    public AppConstants() {
    }

    public static final String EMPLOYEE_BY_ID_SELECT = "with recursive  T " +
            "(ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT) " +
            "as (\n" +
            "              select\n" +
            "                ID,\n" +
            "                FIRSTNAME,\n" +
            "               LASTNAME,\n" +
            "               MIDDLENAME,\n" +
            "                POSITION,\n" +
            "                HIREDATE,\n" +
            "                SALARY,\n" +
            "               MANAGER,\n" +
            "                DEPARTMENT\n" +
            "              from\n" +
            "                EMPLOYEE where ID= %s\n" +
            "                union all\n" +
            "                select\n" +
            "                  M.ID,\n" +
            "                  M.FIRSTNAME,\n" +
            "                  M.LASTNAME,\n" +
            "                  M.MIDDLENAME,\n" +
            "                  M.POSITION,\n" +
            "                  M.HIREDATE,\n" +
            "                  M.SALARY,\n" +
            "                  M.MANAGER,\n" +
            "                  M.DEPARTMENT\n" +
            "                from\n" +
            "                  T\n" +
            "                inner join EMPLOYEE as M on T.MANAGER = M.ID\n" +
            "            ) " +
            "select " +
            "T.ID, " +
            "T.FIRSTNAME, " +
            "T.LASTNAME, " +
            "T.MIDDLENAME, " +
            "T.POSITION, " +
            "T.MANAGER, " +
            "T.HIREDATE, " +
            "T.SALARY, "+
            "T.DEPARTMENT, " +
            "D.NAME, " +
            "D.LOCATION " +
            "from T left join DEPARTMENT D on T.DEPARTMENT = D.ID ;";
    public static final String ALL_EMPLOYEE = "with recursive T " +
            "(ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT) " +
            "as (\n" +
            "              select\n" +
            "                ID,\n" +
            "                FIRSTNAME,\n" +
            "               LASTNAME,\n" +
            "               MIDDLENAME,\n" +
            "                POSITION,\n" +
            "                HIREDATE,\n" +
            "                SALARY,\n" +
            "               MANAGER,\n" +
            "                DEPARTMENT\n" +
            "              from\n" +
            "                EMPLOYEE \n" +
            "                union all\n" +
            "                select\n" +
            "                  M.ID,\n" +
            "                  M.FIRSTNAME,\n" +
            "                  M.LASTNAME,\n" +
            "                  M.MIDDLENAME,\n" +
            "                  M.POSITION,\n" +
            "                  M.HIREDATE,\n" +
            "                  M.SALARY,\n" +
            "                  M.MANAGER,\n" +
            "                  M.DEPARTMENT\n" +
            "                from\n" +
            "                  T\n" +
            "                inner join EMPLOYEE as M on T.MANAGER = M.ID \n" +
            "            ) select distinct " +
            "T.ID, " +
            "T.FIRSTNAME, " +
            "T.LASTNAME, " +
            "T.MIDDLENAME, " +
            "T.POSITION, " +
            "T.MANAGER, " +
            "T.HIREDATE, " +
            "T.SALARY, "+
            "T.DEPARTMENT, " +
            "D.NAME, " +
            "D.LOCATION " +
            "from " +
            "T " +
            "left join DEPARTMENT D on T.DEPARTMENT = D.ID ";

    public static final String LIMIT_OFFSET = " LIMIT %d OFFSET %d";

    public static final String LASTNAME_CASE = "lastName";
    public static final String HIREDATE_CASE = "hired";
    public static final String POSITION_CASE = "position";
    public static final String DIGIT_REGEX = "\\d+";
}
