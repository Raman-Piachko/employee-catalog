package com.epam.rd.autotasks.springemployeecatalog.services;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> getAll(Long page, Long size, String sort, boolean withChain);

    Employee getById(Long employeeId, Boolean withChain);

    List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort);

    List<Employee> getByDepartment(String department, Long page, Long size, String sort);
}
