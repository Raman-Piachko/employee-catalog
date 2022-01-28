package com.epam.rd.autotasks.springemployeecatalog.constants;

public class AppConstants {
    public AppConstants() {
    }

    public static final String EMPLOYEE_ID = "ID_e";
    public static final String FIRSTNAME = "FIRSTNAME_e";
    public static final String LASTNAME = "LASTNAME_e";
    public static final String MIDDLENAME = "MIDDLENAME_e";
    public static final String POSITION = "POSITION_e";
    public static final String HIREDATE = "HIREDATE_e";
    public static final String SALARY = "SALARY_e";
    public static final String DEPARTMENT_ID = "ID_d_e";
    public static final String NAME = "NAME_d_e";
    public static final String LOCATION = "LOCATION_d_e";
    public static final String MANAGER = "MANAGER_e";

    public static final String MANAGER_ID = "ID_m";
    public static final String MANAGER_FIRSTNAME = "FIRSTNAME_m";
    public static final String MANAGER_LASTNAME = "LASTNAME_m";
    public static final String MANAGER_MIDDLENAME = "MIDDLENAME_m";
    public static final String MANAGER_POSITION = "POSITION_m";
    public static final String MANAGER_HIREDATE = "HIREDATE_m";
    public static final String MANAGER_SALARY = "SALARY_m";
    public static final String MANAGER_MANAGER = "MANAGER_m";
    public static final String MANAGER_DEPARTMENT_ID = "ID_d_m";
    public static final String MANAGER_DEPARTMENT_NAME = "NAME_d_m";
    public static final String MANAGER_DEPARTMENT_LOCATION = "LOCATION_d_m";

    public static final String DEFAULT_SELECT = "select\n" +
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
            " m.DEPARTMENT = d2.id\n" +
            " ";


    public static final String EMPLOYEE_BY_ID_SELECT = "WITH RECURSIVE T(ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT ) AS (\n" +
            "  SELECT\n" +
            "    id,\n" +
            "    firstname,\n" +
            "    lastname,\n" +
            "    middlename,\n" +
            "    position,\n" +
            "    hiredate,\n" +
            "    salary,\n" +
            "    manager,\n" +
            "    department\n" +
            "  FROM\n" +
            "    EMPLOYEE  where id= 7654\n" +
            "    UNION all\n" +
            "    SELECT\n" +
            "      m.id,\n" +
            "      m.firstname,\n" +
            "      m.lastname,\n" +
            "      m.middlename,\n" +
            "      m.position,\n" +
            "      m.hiredate,\n" +
            "      m.salary,\n" +
            "      m.manager,\n" +
            "      m.department\n" +
            "    FROM\n" +
            "      T \n" +
            "    INNER JOIN employee AS m ON T.manager= m.id \n" +
            ") SELECT T.ID, T.FIRSTNAME, T.LASTNAME,T.MIDDLENAME,T.POSITION, T.HIREDATE, T.SALARY,T.MANAGER,T.DEPARTMENT, D.NAME, D.LOCATION FROM T LEFT JOIN DEPARTMENT D ON T.DEPARTMENT = D.ID ;";
    public static final String ORDER_BY_LASTNAME = " ORDER BY E.LASTNAME";
    public static final String ORDER_BY_HIREDATE = " ORDER BY E.HIREDATE";
    public static final String ORDER_BY_POSITION = " ORDER BY E.POSITION";
    public static final String ORDER_BY_SALARY = " ORDER BY E.SALARY";
    public static final String LASTNAME_CASE = "lastName";
    public static final String HIREDATE_CASE = "hired";
    public static final String POSITION_CASE = "position";
    public static final String DIGIT_REGEX = "\\d+";
}
