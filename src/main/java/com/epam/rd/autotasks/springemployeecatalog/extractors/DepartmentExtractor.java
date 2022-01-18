package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.NAME;

@Component
public class DepartmentExtractor implements ResultSetExtractor<Map<Integer, Department>> {

    @Override
    public Map<Integer, Department> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Department> departmentMap = new HashMap<>();
        while (resultSet.next()) {
            departmentMap.put(resultSet.getInt(ID), new Department(
                    resultSet.getLong(ID), resultSet.getString(NAME), resultSet.getString(LOCATION))
            );
        }
        return departmentMap;
    }
}