WITH RECURSIVE T (ID,FIRSTNAME,LASTNAME, MIDDLENAME, POSITION, HIREDATE, SALARY, MANAGER, DEPARTMENT ) AS (
              SELECT
                e.id,
                e.firstname,
                e.lastname,
                e.middlename,
                e.position,
                e.hiredate,
                e.salary,
                e.manager,
                e.department
              FROM
                EMPLOYEE e where id= ?
                UNION ALL(
                SELECT
                  m.id,
                  m.firstname,
                  m.lastname,
                  m.middlename,
                  m.position,
                  m.hiredate,
                  m.salary,
                  m.manager,
                  m.department
                FROM
                  T
                INNER JOIN EMPLOYEE AS m ON T.manager= m.id )
            ) SELECT T.ID, T.FIRSTNAME, T.LASTNAME,T.MIDDLENAME,T.POSITION, T.HIREDATE, T.SALARY,T.MANAGER,T.DEPARTMENT, D.NAME, D.LOCATION FROM T LEFT JOIN DEPARTMENT D ON T.DEPARTMENT = D.ID ;