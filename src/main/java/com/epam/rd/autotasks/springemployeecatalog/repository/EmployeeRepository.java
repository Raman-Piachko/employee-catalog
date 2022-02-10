package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee getEmployeeById(Long id, boolean withChain);

    List<Employee> getAllEmployees(Long page, Long size, String sort);

    List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort);

    List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort);
}
