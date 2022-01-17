package com.epam.rd.autotasks.springemployeecatalog.rowpappers;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.NAME;

public class DepartmentRowMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department(
                resultSet.getLong(ID),
                resultSet.getString(NAME),
                resultSet.getString(LOCATION));
        return department;
    }
}