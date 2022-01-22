package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.FIRSTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MIDDLENAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.SALARY;
import static com.epam.rd.autotasks.springemployeecatalog.utils.ExtractorUtils.createEmployeeWithManager;

@Component
public class EmployeeWithChainExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, Long> managerMap = new HashMap<>();
        List<Employee> intermediateEmployeeList = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        initEmployeeListWithManagerMap(resultSet, intermediateEmployeeList, managerMap);

        while (true) {
            addMangerToEmployee(employees, intermediateEmployeeList, managerMap);
            if (intermediateEmployeeList.equals(employees)) break;
            intermediateEmployeeList.clear();
            intermediateEmployeeList.addAll(employees);
            employees.clear();
        }

        return employees;
    }

    private void initEmployeeListWithManagerMap(ResultSet resultSet, List<Employee> intermediateEmployeeList, Map<Long, Long> managerMap) throws SQLException {
        while (resultSet.next()) {
            Long id = resultSet.getLong(EMPLOYEE_ID);
            String firstName = resultSet.getString(FIRSTNAME);
            String lastName = resultSet.getString(LASTNAME);
            String middleName = resultSet.getString(MIDDLENAME);
            Position position = Position.valueOf(resultSet.getString(POSITION));
            LocalDate hired = resultSet.getDate(HIREDATE).toLocalDate();
            BigDecimal salary = resultSet.getBigDecimal(SALARY);
            long departmentId = resultSet.getLong(DEPARTMENT_ID);
            String name = resultSet.getString(NAME);
            String location = resultSet.getString(LOCATION);
            Long managerId = resultSet.getLong(MANAGER);
            managerMap.put(id, managerId);

            intermediateEmployeeList.add(new Employee(
                    id,
                    new FullName(firstName, lastName, middleName),
                    position,
                    hired,
                    salary,
                    null,
                    departmentId != 0L ? new Department(departmentId, name, location) : null
            ));
        }
    }

    private void addMangerToEmployee(List<Employee> employees, List<Employee> intermediateEmployeeList, Map<Long, Long> managerMap) {
        intermediateEmployeeList.stream()
                .map(employee -> createEmployeeWithManager(intermediateEmployeeList, managerMap, employee))
                .forEach(employees::add);
    }
}