package com.epam.rd.autotasks.springemployeecatalog.extractor;

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
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.MANAGER;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.MIDDLENAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.NAME;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.constant.TableColumnNamesEnum.SALARY;

@Component
public class SimpleEmployeeExtractor implements ResultSetExtractor<List<Employee>> {
    private final static String SUFFIX_D_E = "_d_e";
    private final static String SUFFIX_D_M = "_d_m";
    private final static String SUFFIX_E = "_e";
    private final static String SUFFIX_M = "_m";

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(createEmployeeWithManager(resultSet));
        }

        return employees;
    }

    public static Employee createEmployeeWithManager(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(ID.getColumnName() + SUFFIX_E);
        String firstName = resultSet.getString(FIRSTNAME.getColumnName() + SUFFIX_E);
        String lastName = resultSet.getString(LASTNAME.getColumnName() + SUFFIX_E);
        String middleName = resultSet.getString(MIDDLENAME.getColumnName() + SUFFIX_E);
        FullName fullName = new FullName(firstName, lastName, middleName);
        Position position = Position.valueOf(resultSet.getString(POSITION.getColumnName() + SUFFIX_E));
        LocalDate hired = resultSet.getDate(HIREDATE.getColumnName() + SUFFIX_E).toLocalDate();
        BigDecimal salary = resultSet.getBigDecimal(SALARY.getColumnName() + SUFFIX_E);
        long departmentId = resultSet.getLong(ID.getColumnName() + SUFFIX_D_E);
        String name = resultSet.getString(NAME.getColumnName() + SUFFIX_D_E);
        String location = resultSet.getString(LOCATION.getColumnName() + SUFFIX_D_E);
        Department department = departmentId != 0L ? new Department(departmentId, name, location) : null;
        Long managerId = resultSet.getLong(MANAGER.getColumnName() + SUFFIX_E);

        Employee manager = null;

        if (managerId != 0L) {
            String managerFirstname = resultSet.getString(FIRSTNAME.getColumnName() + SUFFIX_M);
            String managerLastname = resultSet.getString(LASTNAME.getColumnName() + SUFFIX_M);
            String managerMiddlename = resultSet.getString(MIDDLENAME.getColumnName() + SUFFIX_M);
            FullName managerFullName = new FullName(managerFirstname, managerLastname, managerMiddlename);
            Position managerPosition = Position.valueOf(resultSet.getString(POSITION.getColumnName() + SUFFIX_M));
            LocalDate managerHired = resultSet.getDate(HIREDATE.getColumnName() + SUFFIX_M).toLocalDate();
            BigDecimal managerSalary = resultSet.getBigDecimal(SALARY.getColumnName() + SUFFIX_M);
            long managerDepartmentId = resultSet.getLong(ID.getColumnName() + SUFFIX_D_M);
            String managerDepartmentName = resultSet.getString(NAME.getColumnName() + SUFFIX_D_M);
            String managerDepartmentLocation = resultSet.getString(LOCATION.getColumnName() + SUFFIX_D_M);
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