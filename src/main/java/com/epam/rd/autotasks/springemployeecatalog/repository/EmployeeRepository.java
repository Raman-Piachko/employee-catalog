package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.EMPLOYEE_BY_ID;

@Repository
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;
    private ResultSetExtractor<List<Employee>> employeeExtractor;
    private Connection connection;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<Employee>> employeeExtractor) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeExtractor = employeeExtractor;
        connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    }

    public List<Employee> findAll() {

        return jdbcTemplate.query(connection -> connection.prepareCall(ALL_FROM_EMPLOYEE, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE), employeeExtractor);
    }

    public Employee findById(Long id) {
        String queryByIdFormat = String.format(EMPLOYEE_BY_ID, id);
        return Objects.requireNonNull(jdbcTemplate.query(queryByIdFormat, employeeExtractor)).get(0);
    }

    public Employee findByIdWithFullChain(long id) {
        return null;
    }
}