package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DIGIT_REGEX;

@Repository
public class EmployeeRepository {

    private JdbcTemplate jdbcTemplate;
    private ExtractorFactory factory;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ExtractorFactory factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public List<Employee> getAllEmployees(String query, boolean withChain) {
        return jdbcTemplate.query(query, factory.getExtractor(withChain));
    }


    public Employee getEmployeeById(String query, Long employeeId, boolean withChain) {
        return getAllEmployees(query, withChain).stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findAny()
                .orElse(null);
    }

    public List<Employee> getByManagerId(String query, Long manager_id) {
        return getAllEmployees(query, false)
                .stream()
                .filter(employee -> employee.getManager() != null && employee.getManager().getId().equals(manager_id))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByDepartment(String query, String department) {
        if (department.matches(DIGIT_REGEX)) {
            return getAllEmployees(query, false)
                    .stream()
                    .filter(employee -> employee.getDepartment() != null &&
                            employee.getDepartment().getId().equals(Long.parseLong(department)))
                    .collect(Collectors.toList());
        } else {
            return getAllEmployees(query, false)
                    .stream()
                    .filter(employee -> employee.getDepartment() != null &&
                            employee.getDepartment().getName().equals(department))
                    .collect(Collectors.toList());
        }
    }

}