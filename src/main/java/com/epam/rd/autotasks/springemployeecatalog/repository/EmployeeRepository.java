package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.Extractor;
import com.epam.rd.autotasks.springemployeecatalog.rowpappers.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.EMPLOYEE_BY_ID;

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Extractor departmentExtractor;
    @Autowired
    private Extractor fullNameExtractor;


    public List<Employee> findAll() {
        List<Employee> employees = jdbcTemplate.query(
                ALL_FROM_EMPLOYEE,
                new EmployeeRowMapper(departmentExtractor, fullNameExtractor));

        return employees;
    }

    public Employee findByEmployeeId(Long id) {
        return jdbcTemplate.queryForObject(EMPLOYEE_BY_ID, new Object[]{id}, new EmployeeRowMapper(departmentExtractor, fullNameExtractor));
    }
}