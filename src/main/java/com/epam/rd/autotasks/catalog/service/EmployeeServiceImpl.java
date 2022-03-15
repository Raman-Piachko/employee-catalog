package com.epam.rd.autotasks.catalog.service;

import com.epam.rd.autotasks.catalog.constant.SortEnum;
import com.epam.rd.autotasks.catalog.domain.Employee;
import com.epam.rd.autotasks.catalog.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll(Long page, Long size, SortEnum sort) {
        return employeeRepository.getAllEmployees(page, size, sort);
    }

    @Override
    public Employee getById(Long employeeId, Boolean withChain) {
        return employeeRepository.getEmployeeById(employeeId, withChain);
    }

    @Override
    public List<Employee> getByManagerId(Long managerId, Long page, Long size, SortEnum sort) {
        return employeeRepository.getByManagerId(managerId, page, size, sort);
    }

    public List<Employee> getByDepartment(String departmentParameter, Long page, Long size, SortEnum sort) {
        return employeeRepository.getEmployeesByDepartment(departmentParameter, page, size, sort);
    }
}