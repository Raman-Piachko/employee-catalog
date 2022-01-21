package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.EmployeeWithChainExtractor;
import com.epam.rd.autotasks.springemployeecatalog.extractors.SimpleEmployeeExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;
    private SimpleEmployeeExtractor extractor;
    private EmployeeWithChainExtractor withChainExtractor;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, SimpleEmployeeExtractor extractor, EmployeeWithChainExtractor withChainExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.extractor = extractor;
        this.withChainExtractor = withChainExtractor;
    }

    public List<Employee> getAllEmployees(String query) {
        return jdbcTemplate.query(query, extractor);
    }
}