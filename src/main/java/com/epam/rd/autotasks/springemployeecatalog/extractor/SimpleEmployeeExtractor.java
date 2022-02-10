package com.epam.rd.autotasks.springemployeecatalog.extractor;

import com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum;
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

import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.FIRSTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.ID;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.LASTNAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.MANAGER_HIREDATE;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.MIDDLENAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.NAME;

@Component
public class SimpleEmployeeExtractor implements ResultSetExtractor<List<Employee>> {
    private final static String D_E = "_d_e";
    private final static String D_M = "_d_m";

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(createEmployeeWithManager(resultSet));
        }

        return employees;
    }

    public static Employee createEmployeeWithManager(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(TableColumnNamesEnum.ID_E.getColumnName());
        String firstName = resultSet.getString(FIRSTNAME.getColumnName());
        String lastName = resultSet.getString(LASTNAME.getColumnName());
        String middleName = resultSet.getString(MIDDLENAME.getColumnName());
        FullName fullName = new FullName(firstName, lastName, middleName);
        Position position = Position.valueOf(resultSet.getString(TableColumnNamesEnum.POSITION.getColumnName()));
        LocalDate hired = resultSet.getDate(HIREDATE.getColumnName()).toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal(TableColumnNamesEnum.SALARY.getColumnName());
        long departmentId = resultSet.getLong(ID.getColumnName() +D_E);
        String name = resultSet.getString(NAME.getColumnName() + D_E);
        String location = resultSet.getString(LOCATION.getColumnName() + D_E);
        Department department = departmentId != 0L ? new Department(departmentId, name, location) : null;
        Long managerId = resultSet.getLong(TableColumnNamesEnum.MANAGER.getColumnName());

        Employee manager = null;

        if (managerId != 0L) {
            String managerFirstname = resultSet.getString(TableColumnNamesEnum.MANAGER_FIRSTNAME.getColumnName());
            String managerLastname = resultSet.getString(TableColumnNamesEnum.MANAGER_LASTNAME.getColumnName());
            String managerMiddlename = resultSet.getString(TableColumnNamesEnum.MANAGER_MIDDLENAME.getColumnName());
            FullName managerFullName = new FullName(managerFirstname, managerLastname, managerMiddlename);
            Position managerPosition = Position.valueOf(resultSet.getString(TableColumnNamesEnum.MANAGER_POSITION.getColumnName()));
            LocalDate managerHired = resultSet.getDate(MANAGER_HIREDATE.getColumnName()).toLocalDate();
            BigDecimal managerSalary = resultSet.getBigDecimal(TableColumnNamesEnum.MANAGER_SALARY.getColumnName());
            long managerDepartmentId = resultSet.getLong(ID.getColumnName() + D_M);
            String managerDepartmentName = resultSet.getString(NAME.getColumnName() + D_M);
            String managerDepartmentLocation = resultSet.getString(LOCATION.getColumnName() + D_M);
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