package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractor.ExtractorFactory;
import com.epam.rd.autotasks.springemployeecatalog.extractor.ExtractorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.ALL_EMPLOYEES;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.BY_DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.BY_DEPARTMENT_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.BY_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.BY_MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.DIGIT_REGEX;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.EMPLOYEE_BY_ID_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.HIREDATE_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.LASTNAME_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.LIMIT_OFFSET;
import static com.epam.rd.autotasks.springemployeecatalog.constant.RepositoryConstants.POSITION_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.util.StatementUtils.getPreparedStatement;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{
    public static final String ORDER_BY_LASTNAME = " ORDER BY LASTNAME_e";
    public static final String ORDER_BY_HIREDATE = " ORDER BY HIREDATE_e";
    public static final String ORDER_BY_POSITION = " ORDER BY POSITION_e";
    public static final String ORDER_BY_SALARY = " ORDER BY SALARY_e";
    private final JdbcTemplate jdbcTemplate;
    private final ExtractorFactory factory;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, ExtractorFactoryImpl factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public Employee getEmployeeById(Long id, boolean withChain) {
        String finalQuery;
        if (withChain) {
            finalQuery = EMPLOYEE_BY_ID_SELECT;
        } else {
            finalQuery = ALL_EMPLOYEES + BY_ID;
        }
        return jdbcTemplate.query(connection -> {
                    PreparedStatement statement = getPreparedStatement(finalQuery, connection);
                    statement.setLong(1, id);
                    return statement;
                }, factory.getExtractor(withChain))
                .get(0);
    }

    public List<Employee> getAllEmployees(Long page, Long size, String sort) {
        if (page != null) {
            String finalQuery = ALL_EMPLOYEES + getOrder(sort) + LIMIT_OFFSET;
            int offset = Math.toIntExact(page * size);
            return jdbcTemplate.query(connection -> {
                PreparedStatement statement = getPreparedStatement(finalQuery, connection);
                statement.setInt(1, Math.toIntExact(size));
                statement.setInt(2, offset);
                return statement;
            }, factory.getExtractor(false));
        } else {
            String finalQuery = ALL_EMPLOYEES + getOrder(sort);
            return jdbcTemplate.query(
                    connection -> connection.prepareStatement(finalQuery,
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_READ_ONLY),
                    factory.getExtractor(false));
        }
    }


    public List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort) {
        String finalQuery = ALL_EMPLOYEES + BY_MANAGER + getOrder(sort) + LIMIT_OFFSET;
        int offset = Math.toIntExact(page * size);
        return jdbcTemplate.query(connection -> {
            PreparedStatement statement = getPreparedStatement(finalQuery, connection);
            statement.setLong(1, managerId);
            statement.setInt(2, Math.toIntExact(size));
            statement.setInt(3, offset);
            return statement;
        }, factory.getExtractor(false));
    }

    public List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort) {
        String finalQuery;
        if (departmentParameter.matches(DIGIT_REGEX)) {
            finalQuery = ALL_EMPLOYEES + BY_DEPARTMENT_ID + getOrder(sort) + LIMIT_OFFSET;
        } else {
            finalQuery = ALL_EMPLOYEES + BY_DEPARTMENT_NAME + getOrder(sort) + LIMIT_OFFSET;
        }
        int offset = Math.toIntExact(page * size);

        return jdbcTemplate.query(connection -> {
            PreparedStatement statement = getPreparedStatement(finalQuery, connection);
            statement.setString(1, departmentParameter);
            statement.setInt(2, Math.toIntExact(size));
            statement.setInt(3, offset);
            return statement;
        }, factory.getExtractor(false));
    }

    private String getOrder(String sort) {
        if (LASTNAME_CASE.equals(sort)) {
            return ORDER_BY_LASTNAME;
        } else if (HIREDATE_CASE.equals(sort)) {
            return ORDER_BY_HIREDATE;
        } else if (POSITION_CASE.equals(sort)) {
            return ORDER_BY_POSITION;
        } else {
            return ORDER_BY_SALARY;
        }
    }
}