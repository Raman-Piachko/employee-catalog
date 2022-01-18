package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_DEPARTMENT;
@Repository
public class DepartmentRepository {

    private JdbcTemplate jdbcTemplate;
    private ResultSetExtractor departmentExtractor;

    @Autowired
    public DepartmentRepository(JdbcTemplate jdbcTemplate, ResultSetExtractor departmentExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.departmentExtractor = departmentExtractor;
    }

    public Map<Integer, Department> getDepartments() {
        return (Map<Integer, Department>) jdbcTemplate.query(ALL_FROM_DEPARTMENT, departmentExtractor);
    }
}
