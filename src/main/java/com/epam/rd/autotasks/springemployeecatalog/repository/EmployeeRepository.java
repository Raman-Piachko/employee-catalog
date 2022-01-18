package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ALL_FROM_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.EMPLOYEE_BY_ID;

@Repository
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;
    private ResultSetExtractor employeeExtractor;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ResultSetExtractor employeeExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeExtractor = employeeExtractor;
    }

    public List<Employee> findAll(){
       return (List<Employee>) jdbcTemplate.query(ALL_FROM_EMPLOYEE,employeeExtractor);
    }

    public Employee findById(Long id, boolean full_chain){
        String queryByIdFormat = String.format(EMPLOYEE_BY_ID, id);
        return ((List<Employee>)jdbcTemplate.query(queryByIdFormat,employeeExtractor)).get(0);
    }

}