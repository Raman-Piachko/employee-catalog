package com.epam.rd.autotasks.springemployeecatalog.controllers;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.services.EmployeeService;
import com.epam.rd.autotasks.springemployeecatalog.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "page", required = false) Long page,
                                          @RequestParam(value = "size", required = false) Long size,
                                          @RequestParam(value = "sort", required = false) String sort) {
        return employeeService.getAll(page, size, sort);
    }

    @GetMapping("/{employee_id}")
    public Employee getEmployeeById(@PathVariable Long employee_id, @RequestParam(value = "full_chain", defaultValue = "false") Boolean withChain) {
        return employeeService.getById(employee_id, withChain);
    }

    @GetMapping("/by_manager/{manager_id}")
    public List<Employee> getEmployeesByManager(@PathVariable Long manager_id,
                                                @RequestParam("page") Long page,
                                                @RequestParam("size") Long size,
                                                @RequestParam("sort") String sort) {

        return employeeService.getByManagerId(manager_id, page, size, sort);
    }

    @GetMapping("/by_department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department,
                                                   @RequestParam("page") Long page,
                                                   @RequestParam("size") Long size,
                                                   @RequestParam("sort") String sort) {
        return employeeService.getByDepartment(department, page, size, sort);
    }
}