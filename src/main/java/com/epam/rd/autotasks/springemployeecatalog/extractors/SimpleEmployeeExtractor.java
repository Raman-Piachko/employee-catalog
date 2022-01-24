package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.EMPLOYEE_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.FIRSTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LASTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_DEPARTMENT_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_DEPARTMENT_LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_DEPARTMENT_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_FIRSTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_ID;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_LASTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_MIDDLENAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_SALARY;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MIDDLENAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.SALARY;
import static com.epam.rd.autotasks.springemployeecatalog.utils.ExtractorUtils.createFullName;

@Component
public class SimpleEmployeeExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> pairList = initEmployeeListWithManagerMap(resultSet);
        return pairList;
    }

    private List<Employee> initEmployeeListWithManagerMap(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
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
            long managerId = resultSet.getLong(MANAGER);
            Employee manager = getManger(resultSet, managerId);
            Employee employee = new Employee(
                    id,
                    createFullName(firstName, lastName, middleName),
                    position,
                    hired,
                    salary,
                    manager,
                    departmentId != 0L ? new Department(departmentId, name, location) : null);
            ;
            employees.add(employee);
        }
        return employees;
    }

    private Employee getManger(ResultSet resultSet, long managerId) throws SQLException {
        Employee manager = null;
        if (managerId != 0L) {
            manager = new Employee(resultSet.getLong(MANAGER_ID),
                    createFullName(resultSet.getString(MANAGER_FIRSTNAME), resultSet.getString(MANAGER_LASTNAME), resultSet.getString(MANAGER_MIDDLENAME)),
                    Position.valueOf(resultSet.getString(MANAGER_POSITION)),
                    resultSet.getDate(MANAGER_HIREDATE).toLocalDate(),
                    resultSet.getBigDecimal(MANAGER_SALARY),
                    null,
                    resultSet.getLong(MANAGER_DEPARTMENT_ID) != 0L ? new Department(resultSet.getLong(MANAGER_DEPARTMENT_ID), resultSet.getString(MANAGER_DEPARTMENT_NAME), resultSet.getString(MANAGER_DEPARTMENT_LOCATION)) : null
            );
        }
        return manager;
    }
}