package com.epam.rd.autotasks.springemployeecatalog.extractors;

import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.FIRST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.LAST_NAME;
import static com.epam.rd.autotasks.springemployeecatalog.AppConstants.MIDDLE_NAME;
@Component
public class FullNameExtractor implements Extractor {
    public FullName getEntity(ResultSet resultSet) throws SQLException {
        return new FullName(
                resultSet.getString(FIRST_NAME),
                resultSet.getString(LAST_NAME),
                resultSet.getString(MIDDLE_NAME)
        );
    }
}
