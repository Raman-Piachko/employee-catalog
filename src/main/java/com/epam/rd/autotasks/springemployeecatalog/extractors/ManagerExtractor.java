package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.domain.Position;
import com.epam.rd.autotasks.springemployeecatalog.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.DEPARTMENT;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.HIRE_DATE;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.MIDDLE_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.POSITION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.SALARY;

@Component
public class ManagerExtractor implements ResultSetExtractor<Map<Long, Employee>> {

    private DepartmentRepository departmentRepository;

    @Autowired
    public ManagerExtractor(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public Map<Long, Employee> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Long, Employee> managers = new HashMap<>();

        while (resultSet.next()) {
            managers.put(resultSet.getLong(ID), new Employee(
                            resultSet.getLong(ID),
                            new FullName(
                                    resultSet.getString(FIRST_NAME),
                                    resultSet.getString(LAST_NAME),
                                    resultSet.getString(MIDDLE_NAME)
                            ),
                            Position.valueOf(resultSet.getString(POSITION)),
                            resultSet.getDate(HIRE_DATE).toLocalDate(),
                            resultSet.getBigDecimal(SALARY),
                            null,
                            departmentRepository.getDepartments().get(resultSet.getInt(DEPARTMENT))
                    )
            );
        }
        return managers;
    }
}
