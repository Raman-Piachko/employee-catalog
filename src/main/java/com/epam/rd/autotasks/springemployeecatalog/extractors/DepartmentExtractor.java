package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.ID;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LOCATION;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.NAME;
@Component
public class DepartmentExtractor implements Extractor {
    public Department getEntity(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getLong(ID),
                resultSet.getString(NAME),
                resultSet.getString(LOCATION)
        );
    }
}