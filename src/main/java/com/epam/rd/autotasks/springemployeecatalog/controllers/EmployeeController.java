package com.epam.rd.autotasks.springemployeecatalog.controllers;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.services.EmployeeService;
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
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "page", required = false) String page,
                                          @RequestParam(value = "size", required = false) String size,
                                          @RequestParam(value = "sort", required = false) String sort,
                                          boolean withChain) {
        return employeeService.getAll(page, size, sort, withChain);
    }

    @GetMapping("/{employee_id}")
    public Employee getEmployeeById(@PathVariable Long employee_id, @RequestParam(value = "full_chain", defaultValue = "false") Boolean withChain) {
        return employeeService.getById(employee_id, withChain);
    }

    @GetMapping("/by_manager/{manager_id}")
    public List<Employee> getEmployeesByManager(@PathVariable Long manager_id,
                                                @RequestParam("page") String page,
                                                @RequestParam("size") String size,
                                                @RequestParam("sort") String sort) {

        return employeeService.getByManagerId(manager_id, page, size, sort);
    }

    @GetMapping("/by_department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department,
                                                   @RequestParam("page") String page,
                                                   @RequestParam("size") String size,
                                                   @RequestParam("sort") String sort) {
        return employeeService.getByDepartment(department, page, size, sort);
    }
}