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
import static com.epam.rd.autotasks.springemployeecatalog.constants.AppConstants.MANAGER_MANAGER;
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
        List<Pair<Long, Employee>> pairList = initEmployeeListWithManagerMap(resultSet);
        return addMangerToEmployee(pairList);
    }

    private List<Pair<Long, Employee>> initEmployeeListWithManagerMap(ResultSet resultSet) throws SQLException {
        List<Pair<Long, Employee>> pairList = new ArrayList<>();
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

            Employee employee = new Employee(
                    id,
                    createFullName(firstName, lastName, middleName),
                    position,
                    hired,
                    salary,
                    null,
                    departmentId != 0L ? new Department(departmentId, name, location) : null);
            pairList.add(Pair.of(managerId, employee));
        }
        return pairList;
    }

    private List<Employee> addMangerToEmployee(List<Pair<Long, Employee>> pairList) {
        List<Employee> resultEmployees = new ArrayList<>();
        List<Employee> employees = pairList.stream().map(Pair::getSecond).collect(Collectors.toList());
        for (Pair<Long, Employee> pair : pairList) {
            Long managerId = pair.getFirst();
            Employee manager = getManager(managerId, employees);
            Long id = pair.getSecond().getId();
            FullName fullName = pair.getSecond().getFullName();
            Position position = pair.getSecond().getPosition();
            LocalDate hired = pair.getSecond().getHired();
            BigDecimal salary = pair.getSecond().getSalary();
            Department department = pair.getSecond().getDepartment();
            Employee employee = new Employee(id, fullName, position, hired, salary, manager, department);
            resultEmployees.add(employee);
        }
        return resultEmployees;
    }

    private Employee getManager(Long managerId, List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(managerId))
                .findFirst()
                .orElse(null);
    }

}