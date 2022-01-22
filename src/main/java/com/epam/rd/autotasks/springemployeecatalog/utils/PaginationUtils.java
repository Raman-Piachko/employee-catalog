package com.epam.rd.autotasks.springemployeecatalog.utils;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PaginationUtils {
    private PaginationUtils() {
    }

    public static List<Employee> getPage(List<Employee> employees, int page, int size) {
        Map<Integer, List<Employee>> employeePagesList = doPagination(employees, size);

        return Optional.ofNullable(employeePagesList.get(page))
                .orElse(Collections.emptyList());
    }

    private static Map<Integer, List<Employee>> doPagination(List<Employee> sourceList, int size) {
        return sourceList.stream()
                .collect(Collectors.groupingBy(employee -> (sourceList.indexOf(employee) / size)));
    }
}
