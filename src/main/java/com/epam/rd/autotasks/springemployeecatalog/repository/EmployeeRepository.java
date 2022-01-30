package com.epam.rd.autotasks.springemployeecatalog.repository;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactory;
import com.epam.rd.autotasks.springemployeecatalog.extractors.ExtractorFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_BY_DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_BY_DEPARTMENT_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_BY_MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.ALL_EMPLOYEE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DIGIT_REGEX;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_BY_ID_SELECT;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LIMIT_OFFSET;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION_CASE;
import static com.epam.rd.autotasks.springemployeecatalog.utils.PaginationUtils.getPage;
import static com.epam.rd.autotasks.springemployeecatalog.utils.StatementUtils.getPreparedStatementCreator;

@Repository
public class EmployeeRepository {
    public static final String ORDER_BY_LASTNAME = " ORDER BY LASTNAME";
    public static final String ORDER_BY_HIREDATE = " ORDER BY HIREDATE";
    public static final String ORDER_BY_POSITION = " ORDER BY POSITION";
    public static final String ORDER_BY_SALARY = " ORDER BY SALARY";
    private final JdbcTemplate jdbcTemplate;
    private final ExtractorFactory factory;

    @Autowired
    public EmployeeRepository(JdbcTemplate jdbcTemplate, ExtractorFactoryImpl factory) {
        this.jdbcTemplate = jdbcTemplate;
        this.factory = factory;
    }

    public Employee getEmployeeById(Long id, boolean withChain) {
        return Objects.requireNonNull(jdbcTemplate.query(
                        getPreparedStatementCreator(EMPLOYEE_BY_ID_SELECT, id),
                        factory.getExtractor(withChain)))
                .get(0);
    }

    public List<Employee> getAllEmployees(Long page, Long size, String sort, boolean withChain) {
        if (page == null) {
            return jdbcTemplate.query(getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_LASTNAME), factory.getExtractor(withChain));
        } else {
            List<Employee> employees;
            switch (sort) {
                case LASTNAME_CASE:
                    employees = jdbcTemplate.query(
                            getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_LASTNAME),
                            factory.getExtractor(withChain));
                    return getPage(employees, page.intValue(), size.intValue());
                case HIREDATE_CASE:
                    employees = jdbcTemplate.query(
                            getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_HIREDATE),
                            factory.getExtractor(withChain));
                    return getPage(employees, page.intValue(), size.intValue());
                case POSITION_CASE:
                    employees = jdbcTemplate.query(
                            getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_POSITION),
                            factory.getExtractor(withChain));
                    return getPage(employees, page.intValue(), size.intValue());
                default:
                    employees = jdbcTemplate.query(
                            getPreparedStatementCreator(ALL_EMPLOYEE + ORDER_BY_SALARY),
                            factory.getExtractor(withChain));
                    return getPage(employees, page.intValue(), size.intValue());
            }
        }
    }

    public List<Employee> getByManagerId(Long managerId, Long page, Long size, String sort) {
        if (page == null) {
            return jdbcTemplate.query(
                            getPreparedStatementCreator(String.format(ALL_BY_MANAGER, managerId) + ORDER_BY_LASTNAME),
                            factory.getExtractor(false)).stream()
                    .filter(employee -> employee.getManager() != null && managerId.equals(employee.getManager().getId()))
                    .collect(Collectors.toList());
        } else {
            List<Employee> employees;
            switch (sort) {
                case LASTNAME_CASE:
                    employees = jdbcTemplate.query(
                                    getPreparedStatementCreator(String.format(ALL_BY_MANAGER, managerId) + ORDER_BY_LASTNAME),
                                    factory.getExtractor(false)).stream()
                            .filter(employee -> employee.getManager() != null && managerId.equals(employee.getManager().getId()))
                            .collect(Collectors.toList());
                    return getPage(employees, page.intValue(), size.intValue());
                case HIREDATE_CASE:
                    employees = jdbcTemplate.query(
                                    getPreparedStatementCreator(String.format(ALL_BY_MANAGER, managerId) + ORDER_BY_HIREDATE),
                                    factory.getExtractor(false)).stream()
                            .filter(employee -> employee.getManager() != null && managerId.equals(employee.getManager().getId()))
                            .collect(Collectors.toList());
                    return getPage(employees, page.intValue(), size.intValue());
                case POSITION_CASE:
                    employees = jdbcTemplate.query(
                                    getPreparedStatementCreator(String.format(ALL_BY_MANAGER, managerId) + ORDER_BY_POSITION),
                                    factory.getExtractor(false)).stream()
                            .filter(employee -> employee.getManager() != null && managerId.equals(employee.getManager().getId()))
                            .collect(Collectors.toList());
                    return getPage(employees, page.intValue(), size.intValue());
                default:
                    employees = jdbcTemplate.query(
                                    getPreparedStatementCreator(String.format(ALL_BY_MANAGER, managerId) + ORDER_BY_SALARY),
                                    factory.getExtractor(false)).stream()
                            .filter(employee -> employee.getManager() != null && managerId.equals(employee.getManager().getId()))
                            .collect(Collectors.toList());
                    return getPage(employees, page.intValue(), size.intValue());
            }
        }
    }

    public List<Employee> getEmployeesByDepartment(String departmentParameter, Long page, Long size, String sort) {
        if (departmentParameter.matches(DIGIT_REGEX)) {
            Long departmentId = Long.valueOf(departmentParameter);
            if (page == null) {
                return jdbcTemplate.query(
                                getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentId) + ORDER_BY_LASTNAME),
                                factory.getExtractor(false)).stream()
                        .filter(employee -> employee.getDepartment() != null && departmentId.equals(employee.getDepartment().getId()))
                        .collect(Collectors.toList());
            } else {
                List<Employee> employees;
                switch (sort) {
                    case LASTNAME_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentId) + ORDER_BY_LASTNAME),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentId.equals(employee.getDepartment().getId()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    case HIREDATE_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentId) + ORDER_BY_HIREDATE),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentId.equals(employee.getDepartment().getId()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    case POSITION_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentId) + ORDER_BY_POSITION),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentId.equals(employee.getDepartment().getId()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    default:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentId) + ORDER_BY_SALARY),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentId.equals(employee.getDepartment().getId()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                }
            }
        } else {
            if (page == null) {
                return jdbcTemplate.query(
                                getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_ID, departmentParameter) + ORDER_BY_HIREDATE),
                                factory.getExtractor(false)).stream()
                        .filter(employee -> employee.getDepartment() != null && departmentParameter.equalsIgnoreCase(employee.getDepartment().getName()))
                        .collect(Collectors.toList());
            } else {
                List<Employee> employees;
                switch (sort) {
                    case LASTNAME_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_NAME, departmentParameter) + ORDER_BY_LASTNAME),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentParameter.equalsIgnoreCase(employee.getDepartment().getName()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    case HIREDATE_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_NAME, departmentParameter) + ORDER_BY_HIREDATE),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentParameter.equalsIgnoreCase(employee.getDepartment().getName()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    case POSITION_CASE:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_NAME, departmentParameter) + ORDER_BY_POSITION),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentParameter.equalsIgnoreCase(employee.getDepartment().getName()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                    default:
                        employees = jdbcTemplate.query(
                                        getPreparedStatementCreator(String.format(ALL_BY_DEPARTMENT_NAME, departmentParameter) + ORDER_BY_SALARY),
                                        factory.getExtractor(false)).stream()
                                .filter(employee -> employee.getDepartment() != null && departmentParameter.equalsIgnoreCase(employee.getDepartment().getName()))
                                .collect(Collectors.toList());
                        return getPage(employees, page.intValue(), size.intValue());
                }
            }
        }
    }
}