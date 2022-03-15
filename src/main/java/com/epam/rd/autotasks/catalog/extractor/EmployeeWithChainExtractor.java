package com.epam.rd.autotasks.catalog.extractor;

import com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum;
import com.epam.rd.autotasks.catalog.domain.Department;
import com.epam.rd.autotasks.catalog.domain.Employee;
import com.epam.rd.autotasks.catalog.domain.FullName;
import com.epam.rd.autotasks.catalog.domain.Position;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.DEPARTMENT;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.FIRSTNAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.HIREDATE;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.ID;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.LASTNAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.MANAGER;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.MIDDLENAME;
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
        Long id = resultSet.getLong(ID.getColumnName());
        String firstName = resultSet.getString(FIRSTNAME.getColumnName());
        String lastName = resultSet.getString(LASTNAME.getColumnName());
        String middleName = resultSet.getString(MIDDLENAME.getColumnName());
        FullName fullName = new FullName(firstName, lastName, middleName);
        Position position = Position.valueOf(resultSet.getString(TableColumnNamesEnum.POSITION.getColumnName()));
        LocalDate hired = resultSet.getDate(String.valueOf(HIREDATE.getColumnName())).toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal(TableColumnNamesEnum.SALARY.getColumnName());
        long departmentId = resultSet.getLong(DEPARTMENT.getColumnName());
        String name = resultSet.getString(TableColumnNamesEnum.NAME.getColumnName());
        String location = resultSet.getString(TableColumnNamesEnum.LOCATION.getColumnName());
        Employee mgr = getManager(resultSet, manager);
        Department department = getDepartment(departmentId, name, location);

        return new Employee(id, fullName, position, hired, salary, mgr, department);
    }

    private static Employee getManagersManager(ResultSet resultSet) throws SQLException {
        int currentRow = resultSet.getRow();
        long managerId = resultSet.getLong(MANAGER.getColumnName());
        resultSet.beforeFirst();
        Employee manager = null;
        while (resultSet.next()) {
            if (resultSet.getLong(ID.getColumnName()) == managerId) {
                manager = createEmployeeWithManager(resultSet, getManagersManager(resultSet));
                break;
            }
        }
        resultSet.absolute(currentRow);

        return manager;
    }

    private static Department getDepartment(long departmentId, String name, String location) {
        if (departmentId != 0L) {
            return new Department(departmentId, name, location);
        } else {
            return null;
        }
    }

    private static Employee getManager(ResultSet resultSet, Employee manager) throws SQLException {
        if (resultSet.getLong(MANAGER.getColumnName()) == NULL) {
            return null;
        } else {
            return manager;
        }
    }
}