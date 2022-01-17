package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.Extractor;
import com.epam.rd.autotasks.springemployeecatalog.rowpappers.DepartmentRowMapper;
import com.epam.rd.autotasks.springemployeecatalog.rowpappers.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.EMPLOYEE_BY_ID;

public class DepartmentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Extractor departmentExtractor;
    @Autowired
    private Extractor fullNameExtractor;


    public List<Department> findAll() {
        List<Department> departments = jdbcTemplate.query(
                ALL_FROM_DEPARTMENT,
                new DepartmentRowMapper());

        return departments;
    }

    public Employee findByEmployeeId(Long id) {
        return jdbcTemplate.queryForObject(EMPLOYEE_BY_ID, new Object[]{id}, new EmployeeRowMapper(departmentExtractor, fullNameExtractor));
    }
}
