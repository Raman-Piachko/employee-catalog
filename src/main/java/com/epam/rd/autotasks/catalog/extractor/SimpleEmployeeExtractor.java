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
import java.util.Optional;

import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.FIRSTNAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.HIREDATE;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.ID;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.LASTNAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.LOCATION;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.MANAGER;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.MIDDLENAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.NAME;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.POSITION;
import static com.epam.rd.autotasks.catalog.constant.TableColumnNamesEnum.SALARY;

@Component
public class SimpleEmployeeExtractor implements ResultSetExtractor<List<Employee>> {
    private static final String SUFFIX_D_E = "_d_e";
    private static final String SUFFIX_D_M = "_d_m";
    private static final String SUFFIX_E = "_e";
    private static final String SUFFIX_M = "_m";

    @Override
    public List<Employee> extractData(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(createEmployeeWithManager(resultSet));
        }
        return employees;
    }

    public static Employee createEmployeeWithManager(ResultSet resultSet) throws SQLException {

        Long managerId = resultSet.getLong(getColumnName(MANAGER, SUFFIX_E));
        Optional<Employee> manager = getDirectManager(resultSet, managerId);

        Long id = resultSet.getLong(getColumnName(ID, SUFFIX_E));
        return getEmployee(resultSet, id, manager.orElse(null), SUFFIX_E, SUFFIX_D_E);
    }

    private static Optional<Employee> getDirectManager(ResultSet resultSet, Long managerId) throws SQLException {
        if (isManagerExists(managerId)) {
            return Optional.of(getEmployee(resultSet, managerId, null, SUFFIX_M, SUFFIX_D_M));
        }
        return Optional.empty();
    }

    private static Employee getEmployee(ResultSet resultSet, Long id, Employee directManager, String suffix, String departmentSuffix) throws SQLException {
        FullName managerFullName = getFullName(resultSet, suffix);
        Position managerPosition = Position.valueOf(resultSet.getString(getColumnName(POSITION, suffix)));
        LocalDate managerHired = resultSet.getDate(getColumnName(HIREDATE, suffix)).toLocalDate();
        BigDecimal managerSalary = resultSet.getBigDecimal(SALARY.getColumnName() + suffix);
        Optional<Department> department = getDepartment(resultSet, departmentSuffix);

        return new Employee(
                id,
                managerFullName,
                managerPosition,
                managerHired,
                managerSalary,
                directManager,
                department.orElse(null));
    }

    private static String getColumnName(TableColumnNamesEnum column, String suffix) {
        return column.getColumnName() + suffix;
    }

    private static Optional<Department> getDepartment(ResultSet resultSet, String departmentSuffix) throws SQLException {
        long managerDepartmentId = resultSet.getLong(getColumnName(ID, departmentSuffix));
        if (managerDepartmentId != 0L) {
            String managerDepartmentName = resultSet.getString(getColumnName(NAME,departmentSuffix));
            String managerDepartmentLocation = resultSet.getString(getColumnName(LOCATION, departmentSuffix));
            return Optional.of(new Department(managerDepartmentId, managerDepartmentName, managerDepartmentLocation));
        }
        return Optional.empty();
    }

    private static boolean isManagerExists(Long managerId) {
        return managerId != 0L;
    }

    private static FullName getFullName(ResultSet resultSet, String suffix) throws SQLException {
        String managerFirstname = resultSet.getString(FIRSTNAME.getColumnName() + suffix);
        String managerLastname = resultSet.getString(LASTNAME.getColumnName() + suffix);
        String managerMiddlename = resultSet.getString(MIDDLENAME.getColumnName() + suffix);
        return new FullName(managerFirstname, managerLastname, managerMiddlename);
    }
}