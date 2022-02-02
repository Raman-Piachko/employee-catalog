package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum;
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
import java.util.List;

import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.department;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeFirstname;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeId;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeHiredate;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeLastname;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeManager;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.employeeMiddlename;
import static java.sql.Types.NULL;

@Component
public class EmployeeWithChainExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(createEmployeeWithManager(resultSet, getManagersManager(resultSet)));
        }
        return employees;
    }

    public static Employee createEmployeeWithManager(ResultSet resultSet, Employee manager) throws SQLException {
        Long id = resultSet.getLong(String.valueOf(employeeId));
        String firstName = resultSet.getString(String.valueOf(employeeFirstname));
        String lastName = resultSet.getString(String.valueOf(employeeLastname));
        String middleName = resultSet.getString(String.valueOf(employeeMiddlename));
        FullName fullName = new FullName(firstName, lastName, middleName);
        Position position = Position.valueOf(resultSet.getString(String.valueOf(EmployeeEnum.employeePosition)));
        LocalDate hired = resultSet.getDate(String.valueOf(employeeHiredate)).toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal(String.valueOf(EmployeeEnum.employeeSalary));
        long departmentId = resultSet.getLong(String.valueOf(department));
        String name = resultSet.getString(String.valueOf(EmployeeEnum.name));
        String location = resultSet.getString(String.valueOf(EmployeeEnum.location));
        Employee mgr = resultSet.getLong(String.valueOf(EmployeeEnum.employeeManager)) == NULL ? null : manager;
        Department department = departmentId != 0L ? new Department(departmentId, name, location) : null;

        return new Employee(id, fullName, position, hired, salary, mgr, department);
    }

    public static Employee getManagersManager(ResultSet resultSet) throws SQLException {
        int currentRow = resultSet.getRow();
        long managerId = resultSet.getLong(String.valueOf(employeeManager));
        resultSet.beforeFirst();
        Employee manager = null;
        while (resultSet.next()) {
            if (resultSet.getLong(String.valueOf(employeeId)) == managerId) {
                manager = createEmployeeWithManager(resultSet, getManagersManager(resultSet));
                break;
            }
        }
        resultSet.absolute(currentRow);

        return manager;
    }
}