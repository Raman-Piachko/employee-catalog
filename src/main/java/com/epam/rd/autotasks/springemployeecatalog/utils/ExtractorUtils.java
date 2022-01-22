package com.epam.rd.autotasks.springemployeecatalog.utils;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public final class ExtractorUtils {

    private ExtractorUtils() {
    }

    public static boolean isEqualsId(Map<Long, Long> managerMap, Employee employee, Employee manager) {
        return managerMap.get(employee.getId()).equals(manager.getId());
    }

    public static FullName createFullName(String firstName, String lastName, String middleName) {
        return new FullName(firstName, lastName, middleName);
    }


    public static Employee createSimpleEmployee(Long id, FullName fullName, Position position, LocalDate hired, BigDecimal salary,
                                                Long departmentId, String name, String location) {
        return new Employee(
                id,
                fullName,
                position,
                hired,
                salary,
                null,
                departmentId != 0 ? new Department(departmentId, name, location) : null
        );
    }

    public static Employee createEmployeeWithManager(List<Employee> intermediateEmployeeList, Map<Long, Long> managerMap, Employee employee) {
        return new Employee(
                employee.getId(),
                employee.getFullName(),
                employee.getPosition(),
                employee.getHired(),
                employee.getSalary(),
                intermediateEmployeeList.stream()
                        .filter(manager -> isEqualsId(managerMap, employee, manager))
                        .findAny()
                        .orElse(null),
                employee.getDepartment()
        );
    }
}