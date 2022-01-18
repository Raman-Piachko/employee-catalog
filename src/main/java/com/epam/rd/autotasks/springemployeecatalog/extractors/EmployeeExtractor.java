package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.SALARY;

@Component
public class EmployeeExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<Employee> employees = new LinkedList<>();

        while (resultSet.next()) {
            employees.add(createEmployee(resultSet)
            );

        }
        return employees;
    }


    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Long managerId = resultSet.getLong(MANAGER);
        Position position = getPosition(resultSet);
        FullName fullName = getFullName(resultSet);
        Long id = resultSet.getLong(ID);
        LocalDate hired = getHired(resultSet);
        BigDecimal salary = resultSet.getBigDecimal(SALARY);
        long departmentId = resultSet.getLong(DEPARTMENT);
        Department department = null;
        if (departmentId != 0L) {
            department = getDepartment(resultSet, departmentId);
        }

        Employee manager = getManager(resultSet, managerId);

        return new Employee(id, fullName, position, hired, salary, manager, department);

    }

    private Position getPosition(ResultSet resultSet) throws SQLException {
        String rawPosition = resultSet.getString(POSITION);

        return Position.valueOf(rawPosition.toUpperCase());
    }

    private FullName getFullName(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString(FIRST_NAME);
        String lastName = resultSet.getString(LAST_NAME);
        String middleName = resultSet.getString(MIDDLE_NAME);

        return new FullName(firstName, lastName, middleName);
    }

    private LocalDate getHired(ResultSet resultSet) throws SQLException {
        Date rawDate = resultSet.getDate(HIRE_DATE);

        return rawDate.toLocalDate();
    }

    private Employee getManager(ResultSet resultSet, Long managerId) {
        Employee manager = null;
        try {
            int rowForBack = resultSet.getRow();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (resultSet.getLong(ID) == managerId) {
                    manager = createEmployee(resultSet);
                    break;
                }
            }
            resultSet.absolute(rowForBack);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manager;
    }

    private Department createDepartment(ResultSet resultSet) throws SQLException {

        Long depId = resultSet.getLong(1);
        String name = resultSet.getString(2);
        String location = resultSet.getString(3);
        return new Department(depId, name, location);
    }

    private Department getDepartment(ResultSet resultSet, Long departmentId) {
        Department department = null;
        try {
            int rowForBack = resultSet.getRow();
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (resultSet.getLong(ID) == departmentId) {
                    department = createDepartment(resultSet);
                    break;
                }
            }
            resultSet.absolute(rowForBack);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return department;
    }
}
