package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;
    private ExtractorFactory factory;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ExtractorFactory factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public List<Employee> getAllEmployees(String query) {
        return jdbcTemplate.query(query, factory.getExtractor(false));
    }
}