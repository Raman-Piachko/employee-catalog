package com.epam.rd.autotasks.springemployeecatalog.services;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.DEFAULT_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ORDER_BY_LASTNAME;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_LASTNAME);
    }
}