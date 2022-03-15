package com.epam.rd.autotasks.catalog.service;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import com.epam.rd.autotasks.catalog.domain.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll(Long page, Long size, SortEnum sort);

    Employee getById(Long employeeId, Boolean withChain);

    List<Employee> getByManagerId(Long managerId, Long page, Long size, SortEnum sort);

    List<Employee> getByDepartment(String department, Long page, Long size, SortEnum sort);
}