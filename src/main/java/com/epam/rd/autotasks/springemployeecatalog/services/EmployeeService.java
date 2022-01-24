package com.epam.rd.autotasks.springemployeecatalog.services;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DEFAULT_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_BY_ID_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ORDER_BY_HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ORDER_BY_LASTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ORDER_BY_POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ORDER_BY_SALARY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.utils.PaginationUtils.getPage;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll(String page, String size, String sort, boolean withChain) {
        if (page == null) {
            return employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_LASTNAME, withChain);
        } else {
            int pageNum = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);
            List<Employee> employees;
            switch (sort) {
                case LASTNAME_CASE:
                    employees = employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_LASTNAME, withChain);
                    return getPage(employees, pageNum, pageSize);
                case HIREDATE_CASE:
                    employees = employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_HIREDATE, withChain);
                    return getPage(employees, pageNum, pageSize);
                case POSITION_CASE:
                    employees = employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_POSITION, withChain);
                    return getPage(employees, pageNum, pageSize);
                default:
                    employees = employeeRepository.getAllEmployees(DEFAULT_SELECT + ORDER_BY_SALARY, withChain);
                    return getPage(employees, pageNum, pageSize);
            }
        }
    }

    public Employee getById(Long id, boolean withChain) {
        return employeeRepository.getEmployeeById(DEFAULT_SELECT+"WHERE e.ID = ?", id, withChain);
    }

    public List<Employee> getByManagerId(Long manager_id, String page, String size, String sort) {
        int pageNum = Integer.parseInt(page);
        int pageSize = Integer.parseInt(size);
        List<Employee> employees;
        switch (sort) {
            case LASTNAME_CASE:
                employees = employeeRepository.getByManagerId(DEFAULT_SELECT + ORDER_BY_LASTNAME, manager_id);
                return getPage(employees, pageNum, pageSize);
            case HIREDATE_CASE:
                employees = employeeRepository.getByManagerId(DEFAULT_SELECT + ORDER_BY_HIREDATE, manager_id);
                return getPage(employees, pageNum, pageSize);
            case POSITION_CASE:
                employees = employeeRepository.getByManagerId(DEFAULT_SELECT + ORDER_BY_POSITION, manager_id);
                return getPage(employees, pageNum, pageSize);
            default:
                employees = employeeRepository.getByManagerId(DEFAULT_SELECT + ORDER_BY_SALARY, manager_id);
                return getPage(employees, pageNum, pageSize);
        }
    }

    public List<Employee> getByDepartment(String departmentParameter, String page, String size, String sort) {
        int pageNum = Integer.parseInt(page);
        int pageSize = Integer.parseInt(size);
        List<Employee> employees;
        switch (sort) {
            case LASTNAME_CASE:
                employees = employeeRepository.getEmployeesByDepartment(DEFAULT_SELECT + ORDER_BY_LASTNAME, departmentParameter);
                return getPage(employees, pageNum, pageSize);
            case HIREDATE_CASE:
                employees = employeeRepository.getEmployeesByDepartment(DEFAULT_SELECT + ORDER_BY_HIREDATE, departmentParameter);
                return getPage(employees, pageNum, pageSize);
            case POSITION_CASE:
                employees = employeeRepository.getEmployeesByDepartment(DEFAULT_SELECT + ORDER_BY_POSITION, departmentParameter);
                return getPage(employees, pageNum, pageSize);
            default:
                employees = employeeRepository.getEmployeesByDepartment(DEFAULT_SELECT + ORDER_BY_SALARY, departmentParameter);
                return getPage(employees, pageNum, pageSize);
        }
    }
}