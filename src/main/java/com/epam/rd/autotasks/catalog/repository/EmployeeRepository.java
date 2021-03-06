package com.epam.rd.autotasks.catalog.repository;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import com.epam.rd.autotasks.catalog.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee getEmployeeById(Long id, boolean withChain);

    List<Employee> getAllEmployees(Long page, Long size, SortEnum sort);

    List<Employee> getByManagerId(Long managerId, Long page, Long size, SortEnum sort);

    List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, SortEnum sort);
}