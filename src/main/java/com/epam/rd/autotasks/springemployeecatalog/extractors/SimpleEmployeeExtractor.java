package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.constants.DepartmentEnum;
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

import static com.epam.rd.autotasks.springemployeecatalog.constants.DepartmentEnum.employeeDepartmentId;
import static com.epam.rd.autotasks.springemployeecatalog.constants.DepartmentEnum.employeeDepartmentLocation;
import static com.epam.rd.autotasks.springemployeecatalog.constants.DepartmentEnum.employeeDepartmentName;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.firstname;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.hiredate;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.lastname;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.managerHiredate;
import static com.epam.rd.autotasks.springemployeecatalog.constants.EmployeeEnum.middlename;

@Component
public class SimpleEmployeeExtractor implements ResultSetExtractor<List<Employee>> {

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(createEmployeeWithManager(resultSet));
        }

        return employees;
    }

    public static Employee createEmployeeWithManager(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(String.valueOf(EmployeeEnum.id));
        String firstName = resultSet.getString(String.valueOf(firstname));
        String lastName = resultSet.getString(String.valueOf(lastname));
        String middleName = resultSet.getString(String.valueOf(middlename));
        FullName fullName = new FullName(firstName, lastName, middleName);
        Position position = Position.valueOf(resultSet.getString(String.valueOf(EmployeeEnum.position)));
        LocalDate hired = resultSet.getDate(String.valueOf(hiredate)).toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal(String.valueOf(EmployeeEnum.salary));
        long departmentId = resultSet.getLong(String.valueOf(employeeDepartmentId));
        String name = resultSet.getString(String.valueOf(employeeDepartmentName));
        String location = resultSet.getString(String.valueOf(employeeDepartmentLocation));
        Department department = departmentId != 0L ? new Department(departmentId, name, location) : null;
        Long managerId = resultSet.getLong(String.valueOf(EmployeeEnum.manager));

        Employee manager = null;

        if (managerId != 0L) {
            String managerFirstname = resultSet.getString(String.valueOf(EmployeeEnum.managerFirstname));
            String managerLastname = resultSet.getString(String.valueOf(EmployeeEnum.managerLastname));
            String managerMiddlename = resultSet.getString(String.valueOf(EmployeeEnum.managerMiddlename));
            FullName managerFullName = new FullName(managerFirstname, managerLastname, managerMiddlename);
            Position managerPosition = Position.valueOf(resultSet.getString(String.valueOf(EmployeeEnum.managerPosition)));
            LocalDate managerHired = resultSet.getDate(String.valueOf(managerHiredate)).toLocalDate();
            BigDecimal managerSalary = resultSet.getBigDecimal(String.valueOf(EmployeeEnum.managerSalary));
            long managerDepartmentId = resultSet.getLong(String.valueOf(DepartmentEnum.managerDepartmentId));
            String managerDepartmentName = resultSet.getString(String.valueOf(DepartmentEnum.managerDepartmentName));
            String managerDepartmentLocation = resultSet.getString(String.valueOf(DepartmentEnum.managerDepartmentLocation));
            Department managerDepartment = managerDepartmentId != 0L ?
                    new Department(managerDepartmentId, managerDepartmentName, managerDepartmentLocation) : null;

            manager = new Employee(
                    managerId,
                    managerFullName,
                    managerPosition,
                    managerHired,
                    managerSalary,
                    null,
                    managerDepartment);
        }

        return new Employee(
                id,
                fullName,
                position,
                hired,
                salary,
                manager,
                department);
    }
}