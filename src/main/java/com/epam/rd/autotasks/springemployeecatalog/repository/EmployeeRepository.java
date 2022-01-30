package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactory;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DIGIT_REGEX;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_BY_ID_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LIMIT_OFFSET;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION_CASE;

@Repository
public class EmployeeRepository {
    public static final String ORDER_BY_LASTNAME = " ORDER BY LASTNAME";
    public static final String ORDER_BY_HIREDATE = " ORDER BY HIREDATE";
    public static final String ORDER_BY_POSITION = " ORDER BY POSITION";
    public static final String ORDER_BY_SALARY = " ORDER BY SALARY";
    private final JdbcTemplate jdbcTemplate;
    private final ExtractorFactory factory;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ExtractorFactoryImpl factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public List<Employee> getAllEmployees(Long page, Long size, String sort, boolean withChain) {
        if (page == null) {
            return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_LASTNAME), factory.getExtractor(withChain));
        } else {
            int offset = Math.toIntExact((size * page) - size);
            if (offset < 0) {
                offset = 0;
            }
            String limitOffset = String.format(LIMIT_OFFSET, size, offset);

            switch (sort) {
                case LASTNAME_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_LASTNAME), factory.getExtractor(withChain));
                case HIREDATE_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_HIREDATE + limitOffset), factory.getExtractor(withChain));
                case POSITION_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE+ ORDER_BY_POSITION + limitOffset), factory.getExtractor(withChain));
                default:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE+ ORDER_BY_SALARY + limitOffset), factory.getExtractor(withChain));

            }
        }
    }

    public Employee getEmployeeById(Long id, boolean withChain) {
        return Objects.requireNonNull(jdbcTemplate.query(getPreparedStatementCreator(EMPLOYEE_BY_ID_SELECT, id),
                        factory.getExtractor(withChain)))
                .get(0);
    }

    private PreparedStatementCreator getPreparedStatementCreator(String query, Long id) {
        String finalQuery = String.format(query, id);
        return connection -> connection.prepareStatement(finalQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private PreparedStatementCreator getPreparedStatementCreator(String query) {
        return connection -> connection.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public List<Employee> getByManagerId(Long managerId, Long page, long size, String sort) {
        if (page == null) {
            return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE MANAGER = " + managerId+ ORDER_BY_LASTNAME), factory.getExtractor(false));
        } else {
            int offset = Math.toIntExact((size * page) - size);
            if (offset < 0) {
                offset = 0;
            }
            String limitOffset = String.format(LIMIT_OFFSET, size, offset);

            switch (sort) {
                case LASTNAME_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE MANAGER = " + managerId+ ORDER_BY_LASTNAME), factory.getExtractor(false));
                case HIREDATE_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE MANAGER = " + managerId+ ORDER_BY_HIREDATE + limitOffset), factory.getExtractor(false));
                case POSITION_CASE:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE MANAGER = " + managerId+ ORDER_BY_POSITION + limitOffset), factory.getExtractor(false));
                default:
                    return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE MANAGER = " + managerId+ ORDER_BY_SALARY + limitOffset), factory.getExtractor(false));

            }
        }
    }

    public List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort) {
        if (departmentParameter.matches(DIGIT_REGEX)) {
            if (page == null) {
                return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE DEPARTMENT = " + departmentParameter+ ORDER_BY_LASTNAME), factory.getExtractor(false));
            } else {
                int offset = Math.toIntExact((size * page) - size);
                if (offset < 0) {
                    offset = 0;
                }
                String limitOffset = String.format(LIMIT_OFFSET, size, offset);

                switch (sort) {
                    case LASTNAME_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE DEPARTMENT = " + departmentParameter+ ORDER_BY_LASTNAME), factory.getExtractor(false));
                    case HIREDATE_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE DEPARTMENT = " + departmentParameter+ ORDER_BY_HIREDATE + limitOffset), factory.getExtractor(false));
                    case POSITION_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE DEPARTMENT = " + departmentParameter+ ORDER_BY_POSITION + limitOffset), factory.getExtractor(false));
                    default:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE DEPARTMENT = " + departmentParameter+ ORDER_BY_SALARY + limitOffset), factory.getExtractor(false));

                }
            }
        } else {
            if (page == null) {
                return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE NAME = " + departmentParameter+ ORDER_BY_LASTNAME), factory.getExtractor(false));
            } else {
                int offset = Math.toIntExact((size * page) - size);
                if (offset < 0) {
                    offset = 0;
                }
                String limitOffset = String.format(LIMIT_OFFSET, size, offset);

                switch (sort) {
                    case LASTNAME_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE NAME = " + departmentParameter+ ORDER_BY_LASTNAME), factory.getExtractor(false));
                    case HIREDATE_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE NAME = " + departmentParameter+ ORDER_BY_HIREDATE + limitOffset), factory.getExtractor(false));
                    case POSITION_CASE:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE NAME = " + departmentParameter+ ORDER_BY_POSITION + limitOffset), factory.getExtractor(false));
                    default:
                        return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + " WHERE NAME = " + departmentParameter+ ORDER_BY_SALARY + limitOffset), factory.getExtractor(false));

                }
            }
        }
    }
}