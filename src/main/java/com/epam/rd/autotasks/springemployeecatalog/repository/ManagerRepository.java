package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_EMPLOYEE;

@Repository
public class ManagerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ResultSetExtractor managerExtractor;

    public Map<Long, Employee> getManagers() {
        return (Map<Long, Employee>) jdbcTemplate.query(ALL_FROM_EMPLOYEE, managerExtractor);
    }

}
