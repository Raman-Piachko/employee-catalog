package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactory;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_EMPLOYEES;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.BY_DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.BY_DEPARTMENT_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.BY_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.BY_MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DIGIT_REGEX;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_BY_ID_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LIMIT_OFFSET;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.utils.StatementUtils.getPreparedStatementCreator;

@Repository
public class EmployeeRepository {
    public static final String ORDER_BY_LASTNAME = " ORDER BY LASTNAME_e";
    public static final String ORDER_BY_HIREDATE = " ORDER BY HIREDATE_e";
    public static final String ORDER_BY_POSITION = " ORDER BY POSITION_e";
    public static final String ORDER_BY_SALARY = " ORDER BY SALARY_e";
    private final JdbcTemplate jdbcTemplate;
    private final ExtractorFactory factory;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ExtractorFactoryImpl factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public Employee getEmployeeById(Long id, boolean withChain) {

        String finalQuery;
        if (withChain) {
            finalQuery = String.format(EMPLOYEE_BY_ID_SELECT, id);
        } else {
            finalQuery = String.format(ALL_EMPLOYEES + BY_ID, id);
        }

        return Objects.requireNonNull(jdbcTemplate.query(
                        getPreparedStatementCreator(finalQuery),
                        factory.getExtractor(withChain)))
                .get(0);
    }

    public List<Employee> getAllEmployees(Long page, Long size, String sort) {
        return getEmployeeList(page, size, sort, ALL_EMPLOYEES);
    }

    public List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort) {
        String finalQuery = getFinalQuery(BY_MANAGER, String.valueOf(managerId));

        return getEmployeeList(page, size, sort, finalQuery);
    }

    public List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort) {
        String finalQuery;
        if (departmentParameter.matches(DIGIT_REGEX)) {
            finalQuery = getFinalQuery(BY_DEPARTMENT_ID, departmentParameter);
        } else {
            finalQuery = getFinalQuery(BY_DEPARTMENT_NAME, departmentParameter);
        }

        return getEmployeeList(page, size, sort, finalQuery);
    }

    private String getFinalQuery(String filterParameter, String parameter) {
        return String.format(ALL_EMPLOYEES + filterParameter, parameter);
    }

    private List<Employee> getEmployees(String query) {
        return jdbcTemplate.query(
                getPreparedStatementCreator(query + ORDER_BY_LASTNAME),
                factory.getExtractor(false));
    }

    private List<Employee> getEmployeesPage(String query, String order, String limitOffset) {
        return jdbcTemplate.query(
                getPreparedStatementCreator(query + order + limitOffset),
                factory.getExtractor(false));
    }

    private List<Employee> getEmployeeList(Long page, Long size, String sort, String finalQuery) {
        if (page == null) {
            return getEmployees(finalQuery);
        } else {
            String order = getOrder(sort);
            int offset = Math.toIntExact(page * size);
            String limitOffset = String.format(LIMIT_OFFSET, size, offset);
            return getEmployeesPage(finalQuery, order, limitOffset);
        }
    }

    private String getOrder(String sort) {
        String order;
        if (LASTNAME_CASE.equals(sort)) {
            order = ORDER_BY_LASTNAME;
        } else if (HIREDATE_CASE.equals(sort)) {
            order = ORDER_BY_HIREDATE;
        } else if (POSITION_CASE.equals(sort)) {
            order = ORDER_BY_POSITION;
        } else {
            order = ORDER_BY_SALARY;
        }
        return order;
    }
}