package com.epam.rd.autotasks.catalog.repository;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import com.epam.rd.autotasks.catalog.domain.Employee;
import com.epam.rd.autotasks.catalog.extractor.ExtractorFactory;
import com.epam.rd.autotasks.catalog.extractor.ExtractorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.DIGIT_REGEX;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.LIMIT_OFFSET;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.SELECT_ALL_EMPLOYEES;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.SELECT_EMPLOYEE_BY_ID;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.WHERE_BY_DEPARTMENT_ID;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.WHERE_BY_DEPARTMENT_NAME;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.WHERE_BY_ID;
import static com.epam.rd.autotasks.catalog.constant.RepositoryConstants.WHERE_BY_MANAGER;
import static com.epam.rd.autotasks.catalog.util.StatementUtils.getPreparedStatement;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ExtractorFactory factory;
    private final Converter converter;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, ExtractorFactoryImpl factory, Converter converter) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
        this.converter = converter;
    }

    public Employee getEmployeeById(Long id, boolean withChain) {
        String finalQuery;
        if (withChain) {
            finalQuery = SELECT_EMPLOYEE_BY_ID;
        } else {
            finalQuery = SELECT_ALL_EMPLOYEES + WHERE_BY_ID;
        }

        return jdbcTemplate.query(connection -> {
                    PreparedStatement statement = getPreparedStatement(finalQuery, connection);
                    statement.setLong(1, id);
                    return statement;
                }, factory.getExtractor(withChain))
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("EMPLOYEE DOESN'T EXIST"));
    }

    public List<Employee> getAllEmployees(Long page, Long size, String sort) {
        if (page != null) {
            return getPageOfEmployees(page, size, sort);
        } else {
            return getEmployees(sort);
        }
    }

    public List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort) {
        String finalQuery = SELECT_ALL_EMPLOYEES + WHERE_BY_MANAGER + getOrder(sort) + LIMIT_OFFSET;
        int offset = Math.toIntExact(page * size);
        Object[] params = {managerId, Math.toIntExact(size), offset};

        return jdbcTemplate.query(finalQuery, params, factory.getExtractor(false));
    }

    public List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort) {
        String finalQuery;
        if (departmentParameter.matches(DIGIT_REGEX)) {
            finalQuery = SELECT_ALL_EMPLOYEES + WHERE_BY_DEPARTMENT_ID + getOrder(sort) + LIMIT_OFFSET;
        } else {
            finalQuery = SELECT_ALL_EMPLOYEES + WHERE_BY_DEPARTMENT_NAME + getOrder(sort) + LIMIT_OFFSET;
        }
        int offset = Math.toIntExact(page * size);
        Object[] params = {departmentParameter, Math.toIntExact(size), offset};

        return jdbcTemplate.query(finalQuery, params, factory.getExtractor(false));
    }

    private List<Employee> getEmployees(String sort) {
        String finalQuery = SELECT_ALL_EMPLOYEES + getOrder(sort);

        return jdbcTemplate.query(finalQuery, factory.getExtractor(false));
    }

    private List<Employee> getPageOfEmployees(Long page, Long size, String sort) {
        String finalQuery = SELECT_ALL_EMPLOYEES + getOrder(sort) + LIMIT_OFFSET;
        int offset = Math.toIntExact(page * size);
        Object[] params = {Math.toIntExact(size), offset};

        return jdbcTemplate.query(finalQuery, params, factory.getExtractor(false));
    }

    private String getOrder(String sort) {
        SortEnum type = (SortEnum) converter.convert(sort);
        if (type != null) {
            return type.getSortName();
        } else {
            return SortEnum.LASTNAME.getSortName();
        }
    }
}