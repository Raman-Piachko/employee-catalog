package com.epam.rd.autotasks.springemployeecatalog.service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll(Long page, Long size, String sort);

    Employee getById(Long employeeId, Boolean withChain);

    List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort);

    List<Employee> getByDepartment(String department, Long page, Long size, String sort);
}